/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.annotation.Action;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.impl.DefaultActionControllerWrapperResolver;
import com.tchepannou.rails.engine.util.ServletUtil;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default implementation of {@link ActionControllerProvider}.
 *
 * @author herve
 */
public class ActionControllerContainer
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (ActionControllerContainer.class);
    
    
    //-- Attribute
    private ContainerContext _containerContext;
    private Map<String, ActionControllerWrapper> _wrappers = new HashMap<String, ActionControllerWrapper> ();
    private ActionControllerWrapperResolver _resolver;
    private boolean _initialized;
    
    //-- Constructeur
    public ActionControllerContainer ()
    {
    }


    //-- Public method
    public void init (ContainerContext containerContext)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("init(" + containerContext + ")");
        }
        LOG.info ("Initializing");
        _containerContext = containerContext;
        try
        {
            Class<? extends ActionControllerWrapperResolver> resolverClazz = ((Engine)containerContext).getActionControllerResolverClass ();
            _resolver = resolverClazz != null 
                ? resolverClazz.newInstance ()
                : new DefaultActionControllerWrapperResolver ();
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("Action Controller Resolver: " + _resolver);
            }
                
            loadActionControllers (_containerContext.getBasePackage () + ".action");
            for (ActionControllerWrapper wrapper : _wrappers.values ())
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
        catch (InstantiationException e)
        {
            throw new InitializationException ("Unable to initialized the ActionControllerContainer", e);
        }
        catch (IllegalAccessException e)
        {
            throw new InitializationException ("Unable to initialized the ActionControllerContainer", e);
        }
        catch (ClassNotFoundException e)
        {
            throw new InitializationException ("Unable to initialized the ActionControllerContainer", e);
        }
        catch (IOException e)
        {
            throw new InitializationException ("Unable to initialized the ActionControllerContainer", e);
        }
    }

    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("destroy()");
        }

        LOG.info ("Destroying");
        for (ActionControllerWrapper wrapper : _wrappers.values ())
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

    public ActionController service (HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("service(" + ServletUtil.toRequestURL (request) + ")");
        }

        if (!_initialized)
        {
            throw new IllegalStateException ("Not initialized. call init() first");
        }

        /* path */
        String path = request.getPathInfo ();
        if ( path.endsWith ("/") )
        {
            path = path.substring (0, path.length () - 1);
        }

        /* get the wrapper */
        ActionControllerWrapper wrapper = getActionControllerWrapper (path);
        if (wrapper != null)
        {
            /* perform the action */
            return wrapper.service (path, request, response);
        }
        else
        {
            LOG.error ("No ActionController mapped to " + path);
            response.sendError (404, "No ActionController mapped to " + path);
            return null;
        }
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    public ActionControllerWrapper getActionControllerWrapper (String path)
    {
        return _resolver.resolve (path, _wrappers);
    }
    
    public void register (Class<? extends ActionController> clazz)
    {
        ActionControllerWrapper wrapper = new ActionControllerWrapper ();
        wrapper.setControllerClass (clazz);

        Action action = (Action)clazz.getAnnotation (Action.class);
        if (action != null)
        {
            Class<? extends ActiveRecord> model = action.modelClass ();
            if (model != ActiveRecord.class)
            {
                wrapper.setModelClass (model);
            }
        }

        LOG.info ("Registering " + wrapper);
        String name = ActionController.getName (clazz);
        _wrappers.put (name.toLowerCase (), wrapper);
    }


    //-- Private
    private void loadActionControllers (String packageName)
        throws ClassNotFoundException,
               IOException
    {
        String xpackageName = basePackage (packageName);
        String[] pkgs = new String[] {xpackageName, "com.tchepannou.rails"};
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Loading ActionControllers classes from package " + StringUtil.merge (pkgs, ","));
        }
        
        Reflections reflections = new Reflections(pkgs);
        Set<Class<? extends ActionController>> classes = reflections.getSubTypesOf (ActionController.class);
        for (Class<? extends ActionController> clazz : classes)
        {
            int modifier = clazz.getModifiers ();
            if (Modifier.isPublic (modifier) 
                && !Modifier.isAbstract (modifier)
                && clazz.getPackage ().getName ().startsWith (packageName) )
            {
                register (clazz);
            }
        }
    }

    public ActionControllerWrapperResolver getResolver ()
    {
        return _resolver;
    }
    
    protected String basePackage (String packageName)
    {
        String pkg = packageName;
        while (true)
        {
            int i = pkg.lastIndexOf (".");
            if (i < 0)
            {
                break;
            }
            else
            {
                pkg = pkg.substring (0, i);
            }
        }
        if (LOG.isDebugEnabled ())
        {
            LOG.debug("Root package of " + packageName  + " is " + pkg);
        }
        return pkg;
    }
}
