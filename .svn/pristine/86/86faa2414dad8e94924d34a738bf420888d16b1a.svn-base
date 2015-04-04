/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ActionContext;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.Resource;
import com.tchepannou.rails.core.exception.ResourceNotFoundException;
import com.tchepannou.rails.core.resource.RestoreViewResource;
import com.tchepannou.rails.core.util.MethodInvoker;
import com.tchepannou.rails.engine.impl.ActionContextImpl;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Decorator for {@link ActionController}
 * 
 * @author herve
 */
public class ActionControllerWrapper
    extends AbstractControllerWrapper
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (ActionControllerWrapper.class);
    
    //-- Attributes
    private String __name;
    private boolean _initialized;
    private ContainerContext _containerContext;
    private Class<? extends ActiveRecord> _modelClass;
    private Class<? extends ActionController> _controllerClass;
    

    //-- Public method
    public void init (ContainerContext containerContext)
    {
        _containerContext = containerContext;
        _initialized = true;
    }

    public void destroy ()
    {
        _containerContext = null;
        _initialized = false;
    }

    public ActionController service (String path, HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        if ( !_initialized )
        {
            throw new IllegalStateException ("Not initialized. call init() first");
        }

        ActionController controller = createActionController (request, response);
        service (path, controller);

        return controller;
    }

    //-- Protected methods
    private void service (String path, ActionController controller)
        throws IOException
    {
        /* Pre-interceptor */
        List<Interceptor> interceptors = _containerContext.getActionInterceptors ();
        int status = before (controller, interceptors);

        /* Service */
        try
        {
            if ( status != Interceptor.STOP )
            {
                MethodInvoker invoker = controller.getMethodInvoker (path);
                if (invoker != null)
                {
                    if (invoker.invoke (interceptors))
                    {
                        doRender (path, controller);
                    }
                }
                else
                {
                    controller.getResponse ().sendError (404);
                }
            }
        }
        finally
        {
            /* Post-interceptor */
            after (controller, interceptors);
        }
    }

    private void doRender (String path, ActionController controller)
        throws IOException
    {
        ActionContext ac = controller.getActionContext ();
        HttpServletResponse response = ac.getResponse ();

        /* Render */
        Resource resource = controller.getResource ();
        if ( resource == null )
        {
            String view = controller.getView (path);
            resource = controller.createDefaultResource (controller.getName (), view);
        }
        try
        {
            if (isModified (controller))
            {
                /* Restore the state of the view */
                restoreState (resource, controller);

                /* Output */
                if ( LOG.isDebugEnabled () )
                {
                    LOG.debug ("Outputting " + resource);
                }
                resource.output (response);
            }

            /* headers */
            addHeaders (response, controller);
        }
        catch ( ResourceNotFoundException e )
        {
            LOG.error ("Unable to render the view", e);
            controller.getContext ().setRollback (true);
            response.sendError (404, e.getMessage ());
        }
    }

    private boolean isModified (ActionController controller)
        throws IOException
    {
        long lastModifiedTime = controller.getLastModificationDate ();
        if ( lastModifiedTime >= 0 )
        {
            HttpServletRequest request = controller.getRequest ();
            long requestModifiedTime = request.getDateHeader ("If-Modified-Since");
            if ( (requestModifiedTime >= 0) && (requestModifiedTime <= lastModifiedTime) )
            {
                HttpServletResponse response = controller.getResponse ();
                response.sendError (HttpServletResponse.SC_NOT_MODIFIED);
                return false;
            }
        }
        return true;
    }

    private void addHeaders (HttpServletResponse response, ActionController controller)
    {
        Map<String, Object> headers = controller.getResponseHeaders ();
        for ( String name: headers.keySet () )
        {
            Object value = headers.get (name);
            if ( value instanceof Date )
            {
                response.setDateHeader (name, (( Date ) value).getTime ());
            }
            else if ( value != null )
            {
                response.setHeader (name, value.toString ());
            }
        }

        long lastModified = controller.getLastModificationDate ();
//        if ( LOG.isDebugEnabled () )
//        {
//            LOG.debug (" Last-Modified: " + lastModified);
//        }
        if ( lastModified > 0 )
        {
            response.addDateHeader ("Last-Modified", lastModified);
        }

        long expirySeconds = controller.getExpirySeconds ();
//        if ( LOG.isDebugEnabled () )
//        {
//            LOG.debug (" Expiry-Seconds: " + expirySeconds);
//        }
        if ( expirySeconds > 0 )
        {
            long expires = System.currentTimeMillis () + expirySeconds * 1000;
            response.addDateHeader ("Expires", expires);
            response.addHeader ("Cache-Control", "max-age=" + expirySeconds + ", must-revalidate, public");
        }
        response.addHeader ("Vary", "Accept-Encoding");
    }

    private void restoreState (Resource resource, ActionController controller)
        throws IOException
    {
        if ( resource instanceof RestoreViewResource )
        {
            String path = (( RestoreViewResource ) resource).getPath ();
            MethodInvoker invoker = controller.getMethodInvoker (path);
            if (invoker != null)
            {
                List<Interceptor> interceptors = _containerContext.getActionInterceptors ();
                invoker.invoke (interceptors);
            }
        }
    }

    private ActionController createActionController (HttpServletRequest request, HttpServletResponse response)
    {
        try
        {
            ActionController controller = _controllerClass.newInstance ();
            ActionContext context = createActionContext (request, response);
            controller.setActionContext (context);

            Map flash = ( Map ) controller.getSession (ActionController.SESSION_FLASH);
            if ( flash != null )
            {
                controller.setFlash (flash);
                controller.removeSession (ActionController.SESSION_FLASH);
            }
            return controller;
        }
        catch ( InstantiationException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + _controllerClass, ex);
        }
        catch ( IllegalAccessException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + _controllerClass, ex);
        }
    }

    private ActionContext createActionContext (final HttpServletRequest request, final HttpServletResponse response)
    {
        return new ActionContextImpl (_containerContext, request, response);
    }

    //-- Getter/Setter
    public String getName ()
    {
        if ( __name == null )
        {
            __name = ActionController.getName (_controllerClass);
        }
        return __name;
    }

    public Class<? extends ActiveRecord> getModelClass ()
    {
        return _modelClass;
    }

    public void setModelClass (Class<? extends ActiveRecord> modelClass)
    {
        this._modelClass = modelClass;
    }

    public Class<? extends ActionController> getControllerClass ()
    {
        return _controllerClass;
    }

    public void setControllerClass (Class<? extends ActionController> controllerClass)
    {
        _controllerClass = controllerClass;
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    //-- Object overrides
    @Override
    public String toString ()
    {
        return "ActionControllerWrapper{" + getName () + "}";
    }
}
