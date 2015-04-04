/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.engine.util.ServletUtil;
import com.tchepannou.util.StringUtil;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This is an interceptor for dumping controller and request data in the log.
 *
 * @author herve
 */
public class DebugInterceptor
    implements Interceptor
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (DebugInterceptor.class);


    //-- Interceptor overrides
    public int before (Controller controller)
    {
        LOG.debug ("");
        LOG.debug ("======================================================");
        LOG.debug ("Controller: " + controller);
        LOG.debug ("");
        
        if (controller instanceof ActionController)
        {
            doBefore((ActionController)controller);
        }

        return Interceptor.CONTINUE;
    }

    public void after (Controller controller)
    {
        if (controller instanceof ActionController)
        {
            doAfter((ActionController)controller);
        }

        LOG.debug ("======================================================");
        LOG.debug ("");
    }

    public int before (Method method, Controller controller)
    {
        LOG.debug ("Method: " + method);
        LOG.debug ("");
        
        return Interceptor.CONTINUE;
    }

    public void after (Method method, Controller controller, Throwable e)
    {
        if (e != null)
        {
            LOG.debug ("Exception: " + e);
        }
    }



    //-- ActionInterceptor
    private void doBefore (ActionController controller)
    {
        HttpServletRequest req = controller.getActionContext ().getRequest ();

        LOG.debug ("URL: " + ServletUtil.toRequestURL (req));
        Map params = controller.getActionContext ().getRequestParameters ();
        if (params.size () > 0)
        {
            LOG.debug ("");
            for (Object name : params.keySet ())
            {
                Object values = params.get (name);
                if (values instanceof String[])
                {

                    String[] xvalues = (String[])values;
                    if (xvalues.length == 1)
                    {
                        LOG.debug ("request.parameter[" + name + "]: " + xvalues[0] );
                    }
                    else
                    {
                        LOG.debug ("request.parameter[" + name + "]: {" + StringUtil.merge (xvalues, ",") + "}" );
                    }
                }
                else
                {
                    LOG.debug ("request.parameter[" + name + "]: " + values );
                }
            }
        }

        if (req.getHeaderNames ().hasMoreElements ())
        {
            LOG.debug ("");
            for (Enumeration names = req.getHeaderNames () ; names.hasMoreElements () ; )
            {
                String name = (String)names.nextElement ();
                Object value = req.getHeader ((String)name);
                LOG.debug ("request.header[" + name + "]: " + value );
            }
        }
    }

    private void doAfter (ActionController controller)
    {
        HttpServletRequest req = controller.getActionContext ().getRequest ();

        HttpSession session = req.getSession (false);
        if (session != null && session.getAttributeNames ().hasMoreElements ())
        {
            LOG.debug ("");
            for (Enumeration names = session.getAttributeNames () ; names.hasMoreElements () ; )
            {
                String name = (String)names.nextElement ();
                Object value = session.getAttribute ((String)name);
                LOG.debug ("session.attribute[" + name + "]: " + value );
            }
        }

        if (req.getAttributeNames ().hasMoreElements ())
        {
            LOG.debug ("");
            for (Enumeration names = req.getAttributeNames () ; names.hasMoreElements () ; )
            {
                String name = (String)names.nextElement ();
                Object value = req.getAttribute ((String)name);
                LOG.debug ("request.attribute[" + name + "]: " + value );
            }
        }


        if (req.getCookies () != null && req.getCookies ().length > 0)
        {
            LOG.debug ("");
            for (Cookie cookie : req.getCookies ())
            {
                LOG.debug ("request.cookie[" + cookie.getName () + "]: " + cookie.getValue ());
            }
        }

        Map vars = controller.getViewVariables ();
        if (vars.size () > 0)
        {
            LOG.debug ("");
            for (Object var : vars.keySet ())
            {
                Object value = vars.get ((String)var);
                LOG.debug ("view.variable[" + var + "]: " + value );
            }
        }
    }

    //-- MailInterceptor
    private void doBefore (MailController controller)
    {
    }

    private void doAfter (MailController controller)
    {
        LOG.debug ("From:        " + controller.getFrom ());
        LOG.debug ("To:          " + controller.getTo ());
        LOG.debug ("CC:          " + controller.getCc ());
        LOG.debug ("Bcc:         " + controller.getBcc ());
        LOG.debug ("Attachments: " + controller.getAttachments ());

        LOG.debug ("");
        Map vars = controller.getViewVariables ();
        if (vars.size () > 0)
        {
            for (Object var : vars.keySet ())
            {
                Object value = vars.get ((String)var);
                LOG.debug ("view.variable[" + var + "]: " + value );
            }
        }

        LOG.debug ("Subject:     " + controller.getSubject ());
        LOG.debug (controller.getBody ());
    }

}
