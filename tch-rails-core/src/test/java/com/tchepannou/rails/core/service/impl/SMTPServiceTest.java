/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service.impl;

import com.tchepannou.rails.core.ObjectFactory;
import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.impl.SMTPService;
import com.tchepannou.rails.core.service.MailService;
import com.tchepannou.rails.core.service.OptionService;
import com.tchepannou.rails.core.service.OptoutService;
import org.subethamail.wiser.Wiser;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author herve
 */
public class SMTPServiceTest 
    extends AbstractServiceTest
{
    private static final int SMTP_PORT = 9998;
    private MailService _service;

    public SMTPServiceTest (String name)
    {
        super (name);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _service = new SMTPService ();
        registerService (OptionService.class, createOptionService ());
    }



    public void testSend ()
        throws Exception
    {
        ServiceContext sc = createServiceContext ();

        _service.init (sc);

        MailController mc = new MailController ();
        mc.addTo ("herve.tchepannou@gmail.com");
        mc.setSubject ("subject of " + getName ());
        mc.setBody ("body of " + getName ());

        Wiser wiser = new Wiser ();
        wiser.setPort (SMTP_PORT);
        wiser.start ();
        try
        {
            _service.send (mc);
        }
        finally
        {
            wiser.stop ();
        }

        assertEquals ("Message", 1, wiser.getMessages ().size ());

        MimeMessage msg = wiser.getMessages ().get (0).getMimeMessage ();
        assertEquals ("subject", mc.getSubject (), msg.getSubject ());
    }

    public void testSendWithAttachment ()
        throws Exception
    {

        ServiceContext sc = createServiceContext ();

        _service.init (sc);

        File f = ObjectFactory.createTXTFile ("This is an attachment");
        MailController mc = new MailController ();
        mc.addTo ("herve.tchepannou@gmail.com");
        mc.setSubject ("subject of " + getName ());
        mc.setBody ("body of " + getName ());
        mc.addAttachment (f);

        Wiser wiser = new Wiser ();
        wiser.setPort (SMTP_PORT);
        wiser.start ();
        try
        {
            _service.send (mc);
        }
        finally
        {
            wiser.stop ();
        }

        assertEquals ("Message", 1, wiser.getMessages ().size ());

        MimeMessage msg = wiser.getMessages ().get (0).getMimeMessage ();
        assertEquals ("subject", mc.getSubject (), msg.getSubject ());
    }


    public void testSendToOptout() throws Exception{
        ServiceContext sc = createServiceContext ();
        
        OptoutService os = createOptoutService ("herve.tchepannou@gmail.com");
        registerService(OptoutService.class, os);
            
        _service.init (sc);
        os.init (sc);

        MailController mc = new MailController ();
        mc.addTo ("herve.tchepannou@gmail.com");
        mc.setSubject ("subject of " + getName ());
        mc.setBody ("body of " + getName ());

        Wiser wiser = new Wiser ();
        wiser.setPort (SMTP_PORT);
        wiser.start ();
        try
        {
            _service.send (mc);
        }
        finally
        {
            wiser.stop ();
        }

        assertEquals ("Message", 0, wiser.getMessages ().size ());        
    }
    
    public void testSendCcOptout() throws Exception{
        ServiceContext sc = createServiceContext ();
        
        OptoutService os = createOptoutService ("herve.tchepannou@gmail.com");
        registerService(OptoutService.class, os);
            
        _service.init (sc);
        os.init (sc);

        MailController mc = new MailController ();
        mc.addCc ("herve.tchepannou@gmail.com");
        mc.setSubject ("subject of " + getName ());
        mc.setBody ("body of " + getName ());

        Wiser wiser = new Wiser ();
        wiser.setPort (SMTP_PORT);
        wiser.start ();
        try
        {
            _service.send (mc);
        }
        finally
        {
            wiser.stop ();
        }

        assertEquals ("Message", 0, wiser.getMessages ().size ());        
    }    
    
    private OptoutService createOptoutService (final String optoutEmail){
        return new OptoutService ()
        {

            public boolean hasOptout (String email)
            {
                return email.equals(optoutEmail);
            }

            public void init (ServiceContext context)
            {
            }

            public void destroy ()
            {
            }
        };
    }
    private OptionService createOptionService ()
    {
        return new OptionService ()
        {
            public String get (String name, String defaultValue)
            {
                return getJavaMailProperties ().getProperty (name);
            }

            public void set (String name, String value)
            {
            }

            public void setServiceContext (ServiceContext context)
            {
            }

            public List<String> getNames ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public void init (ServiceContext context)
            {
            }

            public void destroy ()
            {
            }
        };
    }

    protected Properties getJavaMailProperties ()
    {
        Properties prop = new Properties ();
        prop.put (OptionService.OPTION_MAIL_DEBUG, "true");
        prop.put (OptionService.OPTION_MAIL_FROM, getName () + "@foo.com");
        prop.put (OptionService.OPTION_MAIL_SMTP_HOST, "localhost");
        prop.put (OptionService.OPTION_MAIL_SMTP_PORT, String.valueOf (SMTP_PORT));
        return prop;
    }
}
