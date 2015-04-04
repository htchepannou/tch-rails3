/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.engine.container.*;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.service.MailService;
import com.tchepannou.rails.core.service.OptionService;
import com.tchepannou.rails.core.service.RenderService;
import com.tchepannou.rails.core.impl.PropertiesOptionService;
import com.tchepannou.rails.core.impl.VelocityRenderService;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.mail.TestMailController;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class TemplateInterceptorTest
    extends TestCase
{
    private Engine _cc;

    public TemplateInterceptorTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _cc = new Engine ();
        _cc.setBasePackage ("com.tchepannou.rails");
        _cc.setServletContext (new MockServletContext ());

        _cc.registerMailInterceptor (new TemplateInterceptor ());
        
        register (RenderService.class, new VelocityRenderService ());
        register (OptionService.class, new PropertiesOptionService ());
        register (MailService.class, createMailService ());
    }


    //-- Test
    public void testTXT ()
        throws Exception
    {
        MailControllerContainer container = new MailControllerContainer ();
        container.init (_cc);

        MailController mc = container.deliver ("/test/hello.txt", "herve");

        assertNotNull ("no message sent", mc);
        assertEquals ("from", mc.getFrom (), mc.getFrom ());
        assertTrue ("to", mc.getTo ().contains ("herve.tchepannou@gmail.com"));
        assertEquals ("subject", "Subject", mc.getSubject ());
        assertEquals ("body", "---\nHello herve\n---", mc.getBody ());
        assertEquals ("contentType", "text/plain", mc.getContentType ());
        assertEquals ("encoding", "utf-8", mc.getEncoding ());
    }


    //-- Private
    private MailService createMailService ()
    {
        return new MailService () {

            public void send (MailController controller)
                throws MessagingException
            {
            }

            public void init (ServiceContext context)
            {
            }

            public void destroy ()
            {
            }
        };
    }

    private void register(Class<? extends Service> clazz, Service srv)
    {
        _cc.registerService (clazz, srv);

        ServiceContext sc = _cc.createServiceContext ();
        srv.init (sc);
    }
}
