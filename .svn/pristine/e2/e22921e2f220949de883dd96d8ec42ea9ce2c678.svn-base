/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.annotation.MessageSource;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.MessageController;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.service.JMSService;
import com.tchepannou.rails.engine.impl.JMSData;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Default implementation of {@link ActionControllerProvider}.
 *
 * @author herve
 */
public class MessageControllerContainer
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (MessageControllerContainer.class);
    
    
    //-- Attribute
    private ContainerContext _containerContext;
    private Map<String, MessageControllerWrapper> _wrappers = new HashMap<String, MessageControllerWrapper> ();
    private boolean _initialized;
    
    //-- Constructeur
    public MessageControllerContainer ()
    {
    }


    //-- Public method
    public void init (ContainerContext containerContext)
    {
        LOG.info ("Initializing");

        _containerContext = containerContext;
        try
        {
            loadMessageControllers (_containerContext.getBasePackage () + ".message");
            for (MessageControllerWrapper wrapper : _wrappers.values ())
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Initializing " + wrapper);
                }
                wrapper.init (containerContext);
            }

            _initialized = true;
            LOG.info ("Initialized");
        }
        catch (ClassNotFoundException e)
        {
            throw new InitializationException ("Unable to initialized the MessagingControllerContainer", e);
        }
        catch (IOException e)
        {
            throw new InitializationException ("Unable to initialized the MessagingControllerContainer", e);
        }
    }

    public void destroy ()
    {
        LOG.info ("Destroying");

        for (MessageControllerWrapper wrapper : _wrappers.values ())
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("Destroying " + wrapper);
            }
            wrapper.destroy ();
        }

        _containerContext = null;
        _wrappers.clear ();
        _initialized = false;

        LOG.info ("Destroyed");
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    public MessageControllerWrapper register (Class<? extends MessageController> clazz)
    {
        String topic = null;
        MessageSource msg = (MessageSource)clazz.getAnnotation (MessageSource.class);
        if (msg != null)
        {
            topic = msg.topic ().toLowerCase ();
        }
        else
        {
            topic = MessageController.getName (clazz);
        }

        MessageControllerWrapper wrapper = new MessageControllerWrapper ();
        wrapper.setControllerClass (clazz);
        wrapper.setDestinationName (topic);

        LOG.info ("Registering " + wrapper);
        _wrappers.put (topic, wrapper);

        return wrapper;
    }

    /**
     * This is the method that send the message to JMS path
     * 
     * @see ContainerContext#sendMessage(java.lang.String, java.io.Serializable)
     */
    public void sendMessage (String path, Serializable msg)
        throws JMSException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("sendMessage(" + path + "," + msg + ")");
        }

        JMSService js = (JMSService)_containerContext.findService (JMSService.class);
        if (js == null)
        {
            throw new IllegalStateException ("Service " + JMSService.class.getName () + " not available");
        }
        else
        {
            JMSData data = new JMSData (path, msg);
            String topic = extractTopicFromPath (path);
            js.sendMessage (topic, data);
        }
    }

    public MessageControllerWrapper getMessageControllerWrapper (String path)
    {
        String topic = extractTopicFromPath (path);
        return _wrappers.get (topic.toLowerCase ());
    }



    //-- Private
    private String extractTopicFromPath (String path)
    {
        String topic;
        String xpath = path != null ? path.toLowerCase () : "";
        int i = xpath.lastIndexOf ("/");
        if ( i == 0 )
        {
            topic = xpath.substring (1);
        }
        else
        {
            String xuri = xpath.substring (0, i);
            int j = xuri.lastIndexOf ("/");
            topic = j == 0
                ? xuri.substring (1)
                : xuri.substring (j + 1);
        }
        return topic;
    }

    private void loadMessageControllers (String packageName)
        throws ClassNotFoundException,
               IOException
    {
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Loading MessageControllers classes from package " + packageName);
        }
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends MessageController>> classes = reflections.getSubTypesOf (MessageController.class);
        for (Class<? extends MessageController> clazz : classes)
        {
            int modifier = clazz.getModifiers ();
            if ( Modifier.isPublic (modifier) && !Modifier.isAbstract (modifier))
            {
                register (clazz);
            }
        }
    }
}
