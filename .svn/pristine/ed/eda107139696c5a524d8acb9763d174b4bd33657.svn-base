/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.MessageContext;
import com.tchepannou.rails.core.api.MessageController;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.service.JMSService;
import com.tchepannou.rails.core.util.MethodInvoker;
import com.tchepannou.rails.engine.impl.JMSData;
import com.tchepannou.rails.engine.impl.MessageContextImpl;
import java.io.Serializable;
import java.util.List;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author herve
 */
public class MessageControllerWrapper
    extends AbstractControllerWrapper
    implements MessageListener
{
    //-- Attribute
    private static final Logger LOG = LoggerFactory.getLogger (MessageControllerWrapper.class);

    //-- Attribute
    private ContainerContext _containerContext;
    private String _destinationName;
    private Class<? extends MessageController> _controllerClass;
    private Connection _connection;
    private Session _session;
    private Destination _destination;
    private MessageConsumer _consumer;

    //-- Public method
    public void init (ContainerContext containerContext)
    {
        LOG.info ("Initializing");
        try
        {
            _containerContext = containerContext;

            JMSService ms = (JMSService)containerContext.findService (JMSService.class);
            ConnectionFactory cf = ms.getConnectionFactory ();
            _connection = cf.createConnection ();
            _connection.start ();

            _session = _connection.createSession (true, Session.SESSION_TRANSACTED);
            _destination = _session.createTopic (_destinationName);
            _consumer = _session.createConsumer (_destination);
            _consumer.setMessageListener (this);

            LOG.info ("Initialized");
        }
        catch ( JMSException e )
        {
            throw new InitializationException ("JMS Error", e);
        }
    }

    public void destroy ()
    {
        LOG.info ("Destroying");
        _containerContext = null;

        try
        {
            _consumer.close ();
            _session.close ();
            _connection.close ();

            LOG.info ("Destroyed");
        }
        catch ( JMSException e )
        {
            LOG.warn ("Unable to dispose JMS resources", e);
        }
    }


    //-- MessageListener overrides
    public void onMessage (Message msg)
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("onMessage(" + msg + ")");
        }

        /* Pre-interceptor */
        List<Interceptor> interceptors = _containerContext.getMessageInterceptors ();
        MessageController controller = createMessageController ();
        int status = before (controller, interceptors);

        /* Service */
        try
        {
            if ( status != Interceptor.STOP )
            {
                if (msg instanceof ObjectMessage)
                {
                    onMessage ((ObjectMessage)msg);
                }
                _session.commit ();
            }
        }
        catch (JMSException ex)
        {
            LOG.warn ("JMS error", ex);
            throw new RuntimeException ("JMS error", ex);
        }
        catch ( Exception ex )
        {
            LOG.warn ("Unexcepted error while handing the message", ex);
            try
            {
                _session.rollback ();
            }
            catch ( JMSException e )
            {
                LOG.trace ("Unable to rollback the session", ex);
            }
        }
        finally
        {
            after (controller, interceptors);
        }
    }

    
    //-- Private
    private void onMessage (ObjectMessage msg)
        throws JMSException
    {
        Serializable obj = msg.getObject ();
        if (obj instanceof JMSData)
        {
            onMessage ((JMSData)obj);
        }
    }

    private void onMessage(JMSData msg)
        throws JMSException
    {
        String dest = msg.getDestination ();
        Serializable data = msg.getData ();
        MessageController controller = createMessageController ();
        MethodInvoker invoker = getMethodInvoker (dest, data, controller);
        if (invoker != null)
        {
            invoker.invoke (_containerContext.getMessageInterceptors ());
        }
        else
        {
            LOG.debug ("No invoker found at " + dest);
        }
    }

    private MethodInvoker getMethodInvoker (String path, Serializable data, MessageController controller)
    {
        Object[] records = data != null ? new Object[] {data} : null;
        String methodName = getMethodName (path);
        return methodName != null
            ? new MethodInvoker (methodName, controller, records)
            : null;
    }

    private String getMethodName (String path)
    {
        String xpath = path.startsWith ("/") ? path.substring (1) : path;
        int i = xpath.lastIndexOf ("/");
        return i > 0 ? xpath.substring (i + 1) : null;
    }



    private MessageController createMessageController ()
    {
        Class<? extends MessageController> clazz = getControllerClass ();
        try
        {
            MessageController controller = clazz.newInstance ();
            MessageContext context = new MessageContextImpl (_containerContext);
            controller.setMessageContext (context);
            return controller;
        }
        catch ( InstantiationException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + clazz, ex);
        }
        catch ( IllegalAccessException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + clazz, ex);
        }
    }

    //-- Getter/Setter
    public String getDestinationName ()
    {
        return _destinationName;
    }

    public void setDestinationName (String destinationName)
    {
        this._destinationName = destinationName;
    }

    public Class<? extends MessageController> getControllerClass ()
    {
        return _controllerClass;
    }

    public void setControllerClass (Class<? extends MessageController> controllerClass)
    {
        this._controllerClass = controllerClass;
    }


    //-- Object overrides
    @Override
    public String toString ()
    {
        return "MessageControllerWrapper{" + _destinationName + "}";
    }
}
