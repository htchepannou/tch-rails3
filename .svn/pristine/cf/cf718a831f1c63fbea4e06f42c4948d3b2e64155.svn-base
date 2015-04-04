/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.exception.InitializationException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.mail.MessagingException;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link ActionControllerProvider}.
 *
 * @author herve
 */
public class MailControllerContainer
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (MailControllerContainer.class);
    
    
    //-- Attribute
    private ContainerContext _containerContext;
    private boolean _initialized;
    private Map<String, MailControllerWrapper> _wrappers = new HashMap<String, MailControllerWrapper> ();
    
    
    //-- Constructeur
    public MailControllerContainer ()
    {
    }


    //-- Public method
    public void init (ContainerContext containerContext)
    {
        LOG.info ("Initializing");

        _containerContext = containerContext;
        try
        {
            loadMailControllers (_containerContext.getBasePackage () + ".mail");
            for (MailControllerWrapper wrapper : _wrappers.values ())
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

        LOG.info ("Initialized");
    }

    public void destroy ()
    {
        LOG.info ("Destroying");

        for (MailControllerWrapper wrapper : _wrappers.values ())
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


    public MailController deliver (String path, Serializable data)
        throws MessagingException, IOException
    {
        MailControllerWrapper wrapper = getMailControllerWrapper (path);
        if (wrapper != null)
        {
            return wrapper.deliver (path, data);
        }
        else
        {
            throw new IllegalStateException ("No controller associated with " + path);
        }
    }

    public MailControllerWrapper register (Class<? extends MailController> clazz)
    {
        MailControllerWrapper wrapper = new MailControllerWrapper ();
        wrapper.setControllerClass (clazz);

        LOG.info ("Registering " + wrapper);
        _wrappers.put (wrapper.getName ().toLowerCase (), wrapper);

        return wrapper;
    }


    public MailControllerWrapper getMailControllerWrapper (String path)
    {
        String topic = extractNameFromPath (path);
        return _wrappers.get (topic.toLowerCase ());
    }




    //-- Private
    private String extractNameFromPath (String path)
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

    private void loadMailControllers (String packageName)
        throws ClassNotFoundException,
               IOException
    {
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Loading MailControllers classes from package " + packageName);
        }
        Reflections reflections = new Reflections(packageName);
        Set<Class<? extends MailController>> classes = reflections.getSubTypesOf (MailController.class);
        for (Class<? extends MailController> clazz : classes)
        {
            int modifier = clazz.getModifiers ();
            if ( Modifier.isPublic (modifier) && !Modifier.isAbstract (modifier))
            {
                register (clazz);
            }
        }
    }
}
