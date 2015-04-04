/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.annotation.Template;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.MailController;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;

/**
 * This interceptor set the email template from the annotation <code>@Template</code>
 * 
 * @author herve
 */
public class TemplateInterceptor
    implements Interceptor
{
    //-- Static Attributes
    public static final String ATTR_TEMPLATE = "com.tchepannou.rails.template_key";
    public static final String ATTR_CONTROLLER = "com.tchepannou.rails.controller_key";


    //-- Interceptor override
    @Override
    public int before (Controller controller)
    {
        return Interceptor.CONTINUE;
    }

    @Override
    public void after (Controller controller)
    {
    }

    @Override
    public int before (Method method, Controller controller)
    {
        try
        {
            Template template = method.getAnnotation (Template.class);
            if (template == null)
            {
                template = controller.getClass ().getAnnotation (Template.class);
            }
            if (template != null)
            {
                applyTemplate (controller, template);
            }

            return Interceptor.CONTINUE;
        }
        catch (IOException e)
        {
            throw new IllegalStateException ("IO error", e);
        }
    }

    @Override
    public void after (Method method, Controller controller, Throwable e)
    {
    }


    private void applyTemplate(Controller controller, Template template)
        throws IOException
    {
        if (controller instanceof MailController)
        {
            ((MailController)controller).setTemplate (template.name ());
        }
        else if (controller instanceof ActionController)
        {
            HttpServletRequest request = ((ActionController)controller).getRequest ();
            request.setAttribute (ATTR_TEMPLATE, template);
            request.setAttribute (ATTR_CONTROLLER, controller);
        }
    }
}
