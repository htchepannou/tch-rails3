/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.service.impl;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.Util;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public abstract class AbstractServiceTest
    extends TestCase
{
    //-- Attribute
    private MockServletContext _servletContext;
    private ContainerContext _containerContext;
    private Map<Class<? extends Service>, Service> _services;


    //-- Constructor
    public AbstractServiceTest ()
    {

    }
    public AbstractServiceTest (String name)
    {
        super (name);
    }

    //-- TestCase override
    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _servletContext = new MockServletContext ();
        _containerContext = createContainerContext ();
        _services = new HashMap<Class<? extends Service>, Service> ();
    }

    //-- Utility methods
    protected void registerService (Class<? extends Service> type, Service service)
    {
        _services.put (type, service);
    }
    
    protected ServiceContext createServiceContext ()
    {
        return new ServiceContext ()
        {
            public ContainerContext getContainerContext ()
            {
                return _containerContext;
            }
        };
    }

    protected ContainerContext createContainerContext ()
    {
        return new ContainerContext ()
        {
            public ServletContext getServletContext ()
            {
                return _servletContext;
            }

            public List<Interceptor> getActionInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getBasePackage ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getLoginURL ()
            {
                return "/login";
            }

            public List<Interceptor> getMailInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public Util createUtil ()
            {
                Util u = new Util ();
                u.setContainerContext (this);
                return u;
            }

            public void deliver (String path, Serializable data)
                throws IOException,
                       MessagingException
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public Service findService (Class<? extends Service> type)
            {
                return _services != null ? _services.get (type) : null;
            }

            public List<Interceptor> getJobInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public List<Interceptor> getMessageInterceptors ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public void sendMessage (String destination, Serializable message)
                throws JMSException
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }
        };
    }
}
