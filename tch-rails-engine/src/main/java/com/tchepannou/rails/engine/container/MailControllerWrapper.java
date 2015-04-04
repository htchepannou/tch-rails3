/*
 * To change this path, choose Tools | Templates
 * and open the path in the editor.
 */

package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.MailContext;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.service.MailService;
import com.tchepannou.rails.core.service.RenderService;
import com.tchepannou.rails.core.util.MethodInvoker;
import com.tchepannou.rails.engine.impl.MailContextImpl;
import com.tchepannou.util.MimeUtil;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 *
 * @author herve
 */
public class MailControllerWrapper
    extends AbstractControllerWrapper
{
    //-- Attribute
    private static final Logger LOG = LoggerFactory.getLogger (MailControllerWrapper.class);

    public static final String VAR_I18N = "i18n";
    public static final String VAR_UTIL = "util";


    //-- Attribute
    private String __name;
    private ContainerContext _containerContext;
    private boolean _initialized;
    private Class<? extends MailController> _controllerClass;

    //-- Public method
    public void init (ContainerContext containerContext)
    {
        LOG.info ("Initializing");

        _containerContext = containerContext;
        _initialized = true;

        LOG.info ("Initialized");
    }

    public void destroy ()
    {
        LOG.info ("Destroying");

        _containerContext = null;
        _initialized = false;

        LOG.info ("Destroyed");
    }

    /**
     * Deliver an email
     *
     * @param view  Name of the email message to deliver. The content of this
     *              email is associated with the file <code>/mail/<i>&lt;CONTROLLER_NAME></i>/<i>view</i>.vm.
     *              It represents the View in MVC design patther.
     *
     * @param data  Data to apply to the email message to deliver.
     *              It represents the Model in MVC design patther.
     *
     * @throws MessagingException if any error when sending the email
     * @throws IOException if any error when generating the email content
     */
    public MailController deliver (String path, Serializable data)
        throws MessagingException,  IOException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("deliver(" + path + "," + data + ")");
        }

        if (!_initialized)
        {
            throw new IllegalStateException ("Not initialized. call init() first");
        }

        MailController controller = createMailController ();

        /* Pre-interceptor */
        List<Interceptor> interceptors = _containerContext.getMailInterceptors ();
        int status = before (controller, interceptors);

        /* Service */
        try
        {
            if (status != Interceptor.STOP)
            {
                MethodInvoker invoker = controller.getMethodInvoker (path, data);
                if (invoker.invoke (interceptors))
                {
                    doRender (path, controller);
                    doDeliver (controller);
                }
            }
        }
        finally
        {
            /* Post-interceptor */
            after (controller, interceptors);
        }

        return controller;
    }

    public Class<? extends MailController> getControllerClass ()
    {
        return _controllerClass;
    }

    public void setControllerClass (Class<? extends MailController> controllerClass)
    {
        this._controllerClass = controllerClass;
    }

    public String getName ()
    {
        if ( __name == null )
        {
            __name = MailController.getName (_controllerClass);
        }
        return __name;
    }

    //-- Object overrides
    @Override
    public String toString ()
    {
        return "MailControllerWrapper{" + getName() + "}";
    }

    //-- Private methods
    private void doRender (String path, MailController controller)
        throws IOException
    {
        RenderService rs = (RenderService)_containerContext.findService (RenderService.class);
        if (rs == null)
        {
            throw new IllegalStateException ("Service not found: " + RenderService.class.getName ());
        }

        /* Generate content */
        Map data = new HashMap (controller.getViewVariables ());
        data.put (VAR_UTIL, _containerContext.createUtil ());
        data.put (VAR_I18N, controller.getI18n ());

        String template = "/mail" + path;
        StringWriter writer = new StringWriter ();
        rs.render (template, data, writer);

        String content = writer.toString ();
        String contentType = MimeUtil.getInstance ().getMimeTypeByFile (template);
        if (StringUtil.isEmpty (contentType))
        {
            contentType = MimeUtil.TEXT;
        }

        int i = content.indexOf ("\n");
        String subject = i > 0 ? content.substring (0, i) : "";
        String body = i > 0 ? content.substring (i+1) : "";
        controller.setBody (body);
        controller.setSubject (subject);
        controller.setContentType (contentType);

        /* apply the mail template */
        applyTemplate (controller);
    }

    private void applyTemplate(MailController controller)
        throws IOException
    {
        String template = controller.getTemplate ();
        if (StringUtil.isEmpty (template))
        {
            return;
        }

        String contentType = controller.getContentType ();
        String path = MimeUtil.HTML.equals (contentType)
            ? template + ".html"
            : template + ".txt";

        ContainerContext cc = controller.getMailContext ().getContainerContext ();
        StringWriter writer = new StringWriter ();
        RenderService rs = (RenderService)cc.findService (RenderService.class);

        Map data = new HashMap ();
        data.put ("__controller", controller);
        data.put ("util", cc.createUtil ());
        rs.render ("/template/" + path, data, writer);

        controller.setBody (writer.toString ());
    }

    private void doDeliver (MailController controller)
        throws MessagingException
    {
        MailService ms = (MailService)_containerContext.findService (MailService.class);
        if (ms == null)
        {
            throw new IllegalStateException ("Service not found: " + MailService.class);
        }
        ms.send (controller);
    }

    private MailController createMailController ()
    {
        Class<? extends MailController> clazz = getControllerClass ();
        try
        {
            MailController controller = clazz.newInstance ();
            MailContext context = new MailContextImpl (_containerContext);
            controller.setMailContext (context);
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
}
