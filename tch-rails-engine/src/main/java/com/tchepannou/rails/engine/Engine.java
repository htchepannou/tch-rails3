package com.tchepannou.rails.engine;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.Util;
import com.tchepannou.rails.engine.container.ActionControllerContainer;
import com.tchepannou.rails.engine.container.ActionControllerWrapperResolver;
import com.tchepannou.rails.engine.container.JobControllerContainer;
import com.tchepannou.rails.engine.container.MailControllerContainer;
import com.tchepannou.rails.engine.container.MessageControllerContainer;
import com.tchepannou.rails.engine.impl.ServiceContextImpl;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Default implementation of {@link ContainerContext}
 * @author herve
 */
public class Engine
    implements ContainerContext
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (Engine.class);
    private static final String ATTR_ENGINE = "com.tchepannou.rails.engine";
    
    
    //-- Attribute
    private String _basePackage;
    private String _loginURL = "/login";
    private ServletContext _servletContext;
    private List<Interceptor> _actionInterceptors = new ArrayList<Interceptor> ();
    private List<Interceptor> _mailInterceptors = new ArrayList<Interceptor> ();
    private List<Interceptor> _jobInterceptors = new ArrayList<Interceptor> ();
    private List<Interceptor> _messageInterceptors = new ArrayList<Interceptor> ();
    private Map<Class<? extends Service>, Service> _services = new HashMap<Class<? extends Service>, Service> ();
    private Class<? extends Util> _utilClass;
    private ActionControllerContainer _actionContainer = new ActionControllerContainer ();
    private MailControllerContainer _mailContainer = new MailControllerContainer ();
    private JobControllerContainer _jobContainer = new JobControllerContainer ();
    private MessageControllerContainer _messageContainer = new MessageControllerContainer ();
    private Class<? extends ActionControllerWrapperResolver> _actionControllerResolver;
    private boolean _initialized ;


    //-- Public methods
    public static Engine getInstance (ServletContext sc)
    {
        return (Engine)sc.getAttribute (ATTR_ENGINE);
    }

    public void init (ServletContext servletContext)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("init(" + servletContext + ")");
        }
        LOG.info ("Initializing");

        _servletContext = servletContext;

        /* Initialize the services */
        LOG.info ("Initializing services");
        ContainerContext cc = this;
        for (Service service : _services.values ())
        {
            ServiceContext sc = new ServiceContextImpl (cc);
            service.init (sc);
        }


        /* Containers */
        _actionContainer.init (cc);
        _jobContainer.init (cc);
        _mailContainer.init (cc);
        _messageContainer.init (cc);

        _servletContext.setAttribute (ATTR_ENGINE, this);

        _initialized = true;
        LOG.info ("Initialized");
    }

    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("destroy ()");
        }
        LOG.info ("Destroying");

        for (Service service : _services.values ())
        {
            service.destroy ();
        }

        _actionContainer.destroy ();
        _jobContainer.destroy ();
        _mailContainer.destroy ();
        _messageContainer.destroy ();

        _services.clear ();
        _actionInterceptors.clear ();
        _jobInterceptors.clear ();
        _mailInterceptors.clear ();
        _messageInterceptors.clear ();

        _servletContext.removeAttribute (ATTR_ENGINE);

        _initialized = false;

        LOG.info ("Destroyed");
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    public Class<? extends Util> getUtilClass ()
    {
        return _utilClass;
    }
    
    public void setLoginURL (String loginURL)
    {
        this._loginURL = loginURL;
    }

    public void registerService (Class<? extends Service> type, Service service)
    {
        _services.put (type, service);
    }

    public void registerActionInterceptor (Interceptor interceptor)
    {
        _actionInterceptors.add (interceptor);
    }

    public void registerMailInterceptor (Interceptor interceptor)
    {
        _mailInterceptors.add (interceptor);
    }

    public void registerJobInterceptor (Interceptor interceptor)
    {
        _jobInterceptors.add (interceptor);
    }

    public void registerMessageInterceptor (Interceptor interceptor)
    {
        _messageInterceptors.add (interceptor);
    }

    public void setBasePackage (String basePackage)
    {
        this._basePackage = basePackage;
    }

    public void setServletContext (ServletContext servletContext)
    {
        this._servletContext = servletContext;
    }

    public MailControllerContainer getMailContainer ()
    {
        return _mailContainer;
    }

    public ActionControllerContainer getActionContainer ()
    {
        return _actionContainer;
    }

    public JobControllerContainer getJobContainer ()
    {
        return _jobContainer;
    }

    public MessageControllerContainer getMessageContainer ()
    {
        return _messageContainer;
    }


    public void setUtilClass (Class<? extends Util> clazz)
    {
        _utilClass = clazz;
    }

    
    //-- ContainerContext methods
    public Service findService (Class<? extends Service> type)
    {
        return _services.get (type);
    }

    public Util createUtil ()
    {
        try
        {
            Util util = _utilClass != null ? _utilClass.newInstance () : new Util ();
            util.setContainerContext (this);

            return util;
        }
        catch (InstantiationException e)
        {
            throw new IllegalStateException ("Unable to instanciate " + _utilClass, e);
        }
        catch (IllegalAccessException e)
        {
            throw new IllegalStateException ("Unable to instanciate " + _utilClass, e);
        }
    }

    public ServletContext getServletContext ()
    {
        return _servletContext;
    }

    public String getBasePackage ()
    {
        return _basePackage;
    }

    public List<Interceptor> getActionInterceptors ()
    {
        return _actionInterceptors;
    }

    public List<Interceptor> getMailInterceptors ()
    {
        return _mailInterceptors;
    }

    public List<Interceptor> getMessageInterceptors ()
    {
        return _messageInterceptors;
    }

    public List<Interceptor> getJobInterceptors ()
    {
        return _jobInterceptors;
    }

    public Map<Class<? extends Service>, Service> getServices ()
    {
        return _services;
    }

    public String getLoginURL ()
    {
        return _loginURL;
    }

    public ServiceContext createServiceContext ()
    {
        final ContainerContext cc = this;
        return new ServiceContext ()
        {
            public ContainerContext getContainerContext ()
            {
                return cc;
            }
        };
    }

    public void deliver (String path, Serializable data)
        throws IOException,
               MessagingException
    {
        _mailContainer.deliver (path, data);
    }

    public void sendMessage (String destination, Serializable message)
        throws JMSException
    {
        _messageContainer.sendMessage (destination, message);
    }

    public Class<? extends ActionControllerWrapperResolver> getActionControllerResolverClass ()
    {
        return _actionControllerResolver;
    }

    public void setActionControllerResolverClass (Class<? extends ActionControllerWrapperResolver> actionControllerResolver)
    {
        this._actionControllerResolver = actionControllerResolver;
    }
}
