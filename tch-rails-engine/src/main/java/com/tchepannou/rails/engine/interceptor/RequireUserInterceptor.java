/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.annotation.RequireUser;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.core.service.UserService;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author herve
 */
public class RequireUserInterceptor
    implements Interceptor
{
    //-- Private
    private static final Logger LOG = LoggerFactory.getLogger (RequireUserInterceptor.class);
    
    
    //-- Interceptor
    public int before (Controller controller)
    {
        try
        {
            if ( controller instanceof ActionController )
            {
                RequireUser annotation = controller.getClass ().getAnnotation (RequireUser.class);
                if ( annotation != null )
                {
                    return checkSecurity (annotation, ( ActionController ) controller);
                }
            }
            return Interceptor.CONTINUE;
        }
        catch ( IOException e )
        {
            throw new IllegalStateException ("IO Error", e);
        }
    }

    @Override
    public int before (Method method, Controller controller)
    {
        try
        {
            if ( controller instanceof ActionController )
            {
                RequireUser annotation = method.getAnnotation (RequireUser.class);
                if ( annotation != null )
                {
                    return checkSecurity (annotation, ( ActionController ) controller);
                }
            }
            return Interceptor.CONTINUE;
        }
        catch ( IOException e )
        {
            throw new IllegalStateException ("IO Error", e);
        }
    }

    @Override
    public void after (Method method, Controller controller, Throwable e)
    {
    }

    public void after (Controller controller)
    {
    }

    //-- Protected methods
    protected final int checkSecurity (RequireUser annotation, ActionController controller)
        throws IOException
    {
        if ( annotation != null )
        {
            User user = controller.getUser ();
            if (LOG.isDebugEnabled ())
            {
                LOG.debug("user=" + user);
            }
            if ( user == null )
            {
                LOG.warn ("No current user. Login required");
                controller.getResponse ().sendError (HttpServletResponse.SC_UNAUTHORIZED);
                return Interceptor.STOP;
            }
            else
            {
                boolean allow = hasPermission (user, annotation, controller);
                if ( !allow )
                {
                    String msg = "Permission denied. Permission required: " + StringUtil.merge (annotation.permissions (), ",");
                    LOG.warn (msg);
                    
                    controller.getResponse ().sendError (HttpServletResponse.SC_FORBIDDEN, msg);
                    return Interceptor.STOP;
                }
            }
        }
        return Interceptor.CONTINUE;
    }

    protected final boolean hasPermission (User user, RequireUser annotation, ActionController controller)
    {
        String[] permissions = annotation.permissions ();
        if ( permissions == null || (permissions.length == 1 && StringUtil.isEmpty(permissions[0])))
        {
            return true;
        }
        else if (permissions != null && permissions.length > 0)
        {
            UserService us = ( UserService ) controller.getActionContext ().getContainerContext ().findService (UserService.class);
            if ( us == null )
            {
                return false;
            }
            else
            {
                return us.hasPermission (user, permissions);
            }
        }
        else
        {
            return true;
        }
    }
}
