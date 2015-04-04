/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.impl;

import com.tchepannou.rails.core.api.MailController;
import com.tchepannou.rails.core.service.MailService;
import com.tchepannou.rails.core.service.OptionService;
import com.tchepannou.rails.core.service.OptoutService;
import com.tchepannou.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Default implementation of {@link MailService}.
 * This service depends on the following services:
 * <ul>
 *  <li>{@link OptionService}</li>
 * </ul>
 * 
 * @author herve
 */
public class SMTPService
    extends AbstractService
    implements MailService
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (SMTPService.class);
    private static final String SMTP = "smtp";
    

    //-- MailService overrides
    public void send (MailController controller)
        throws MessagingException
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("send (" + controller + ")");
        }

        if (!controller.hasRecipients ())
        {
            LOG.warn ("Not recipients. No email to send");
            return;
        }

        /* log */
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("");
            LOG.debug (" From:    " + controller.getFromAddress ());
            LOG.debug (" ReplyTo: " + controller.getReplyToAddress ());
            LOG.debug (" To:      " + StringUtil.merge (controller.getTo (), ","));
            LOG.debug (" CC:      " + StringUtil.merge (controller.getCc (), ","));
            LOG.debug (" Bcc:     " + StringUtil.merge (controller.getBcc (), ","));
            LOG.debug (" Subject: " + controller.getSubject ());
            LOG.debug (controller.getBody ());
            LOG.debug ("");
        }

        /* Open the Session */
        Properties properties = getJavaMailProperties ();
        String[] ports = getPorts (properties);
        MessagingException ex = null;
        for (String port : ports)
        {
            try
            {
                send (controller, properties, port);
                return;
            }
            catch (MessagingException e)
            {
                LOG.warn ("Unable to send email via port: " + port, ex);
                ex = e;
            }
        }

        throw ex;
    }

    private void send (MailController controller, Properties properties, String port)
        throws MessagingException
    {
        /* Mail Session */
        Authenticator auth = createAuthenticator (controller);
        int xport = StringUtil.toInt (port, 0);
        properties.setProperty (OptionService.OPTION_MAIL_SMTP_PORT, String.valueOf (xport));
        Session session = Session.getInstance (properties, auth);

        /* Create the message */
        MimeMessage mm = createMimeMessage (controller, session, properties);
        if (mm == null){
            return;
        }
        
        /* Send the message */
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Connecting to server via port #" + port);
        }
        Transport ts = session.getTransport (SMTP);
        ts.connect ();
        try
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("Sending the message via port #" + port);
            }
            Transport.send (mm);
        }
        finally
        {
            ts.close ();
        }
    }
    
    //-- Private methods
    private String[] getPorts (Properties p)
    {
        String ports = p.getProperty(OptionService.OPTION_MAIL_SMTP_PORT);
        return ports != null ? ports.split (",") : new String[] {};
    }

    private MimeMessage createMimeMessage (MailController controller, Session session, Properties props)
        throws MessagingException
    {
        MimeMessage msg = new MimeMessage (session);
        if (addRecipients (controller, msg) == 0){
            return null;    // No receipient
        }
        
        setSender (msg, props);
        setFrom (controller, msg, props);
        setReplyTo (controller, msg);
        setSubject (controller, msg);

        Multipart content = new MimeMultipart();
        msg.setContent (content);
        
        /* Text */
        String body = controller.getBody ();
        if (!StringUtil.isEmpty (body))
        {
            MimeBodyPart part = new MimeBodyPart();
            part.setContent (body, controller.getContentType ());
            content.addBodyPart (part);
        }

        /* Attachments */
        List<File> attachments = controller.getAttachments ();
        if (!attachments.isEmpty ())
        {
            for (File attachment : attachments)
            {
                MimeBodyPart part = new MimeBodyPart();
                DataSource source = new FileDataSource(attachment);
                part.setDataHandler(new DataHandler (source));
                part.setFileName(attachment.getName ());
                content.addBodyPart(part);
            }
        }

        return msg;
    }

    private void setFrom (MailController controller, MimeMessage msg, Properties props)
        throws MessagingException
    {
        InternetAddress from = controller.getFromAddress ();
        if ( from == null )
        {
            String address = props.getProperty (OptionService.OPTION_MAIL_FROM);
            InternetAddress replyTo = controller.getReplyToAddress ();
            String displayName = replyTo != null ? replyTo.getPersonal () : null;
            try{
                from = StringUtil.isEmpty (displayName)
                    ? new InternetAddress (address)
                    : new InternetAddress (address, displayName);
            }catch (IOException e){
                from = new InternetAddress (address);
            }
        }

        if ( from != null)
        {
            msg.setFrom (from);
        }
    }
    private void setReplyTo (MailController controller, MimeMessage msg) throws MessagingException{
        InternetAddress replyTo = controller.getReplyToAddress ();
        if (replyTo != null){
            msg.setReplyTo (new Address[] {replyTo});
        }
    }
    private void setSender (MimeMessage msg, Properties props)
        throws MessagingException
    {
        String from = props.getProperty (OptionService.OPTION_MAIL_FROM);
        if ( StringUtil.isEmail (from) )
        {
            msg.setSender (new InternetAddress (from));
        }
    }

    private int addRecipients (MailController controller, MimeMessage msg)
        throws MessagingException
    {
        return addRecipients (controller.getTo (), Message.RecipientType.TO, msg)
            + addRecipients (controller.getCc (), Message.RecipientType.CC, msg)
            + addRecipients (controller.getBcc (), Message.RecipientType.BCC, msg)
        ;
    }

    private int addRecipients (List<String> recipients, Message.RecipientType type, MimeMessage msg)
        throws MessagingException
    {
        int result = 0;
        for ( String recipient: recipients )
        {
            if (!hasOptout(recipient)){
                msg.addRecipient (type, new InternetAddress (recipient));
                result++;
            } else {
                LOG.info (recipients + " has optout");
            }
        }
        return result;
    }
    
    private boolean hasOptout (String email){
        OptoutService os = (OptoutService)findService(OptoutService.class);
        return os == null ? false : os.hasOptout(email);
    }

    private void setSubject (MailController controller, MimeMessage msg)
        throws MessagingException
    {
        String subject = controller.getSubject ();
        msg.setSubject (!StringUtil.isEmpty (subject)
            ? subject
            : StringUtil.EMPTY);
    }

    private Authenticator createAuthenticator (MailController controller)
    {
        OptionService srv = ( OptionService ) findService (OptionService.class);
        if ( srv != null )
        {
            String auth = srv.get (OptionService.OPTION_MAIL_SMTP_AUTH, "false");
            if ( "true".equals (auth) )
            {
                String user = srv.get (OptionService.OPTION_MAIL_SMTP_USER, "");
                String password = srv.get (OptionService.OPTION_MAIL_SMTP_PASSWORD, "");

                return new AuthenticatorImpl (user, password);
            }
        }
        return null;
    }

    private Properties getJavaMailProperties ()
    {
        OptionService srv = (OptionService)findService (OptionService.class);
        Properties p = new Properties ();
        setProperty (p, OptionService.OPTION_MAIL_DEBUG, OptionService.DEFAULT_MAIL_DEBUG, srv);
        setProperty (p, OptionService.OPTION_MAIL_FROM, null, srv);
        setProperty (p, OptionService.OPTION_MAIL_SMTP_HOST, OptionService.DEFAULT_MAIL_SMTP_HOST, srv);
        setProperty (p, OptionService.OPTION_MAIL_SMTP_PORT, OptionService.DEFAULT_MAIL_SMTP_PORT, srv);
        setProperty (p, OptionService.OPTION_MAIL_SMTP_AUTH, null, srv);

        if ("true".equalsIgnoreCase (srv.get (OptionService.OPTION_MAIL_SSL, null)))
        {
            p.setProperty("mail.smtp.starttls.enable", "true");
//            p.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//            p.setProperty("mail.smtp.socketFactory.fallback", "false");
        }
        return p;
    }

    private void setProperty (Properties p, String name, String defaultValue, OptionService srv)
    {
        String value = srv.get (name, defaultValue);
        if ( LOG.isDebugEnabled () )
        {
            LOG.debug (name + "=" + value);
        }
        if ( value != null )
        {
            p.put (name, value);
        }
    }

    private void setProperty (Properties p, String name, int defaultValue, OptionService srv)
    {
        String value = srv.get (name, String.valueOf (defaultValue));
        setProperty (p, name, value, srv);
    }

    private void setProperty (Properties p, String name, boolean defaultValue, OptionService srv)
    {
        String value = srv.get (name, String.valueOf (defaultValue));
        setProperty (p, name, value, srv);
    }


    //-- Inner classes
    /**
     *
     * SMTP Authenticator
     */
    private static class AuthenticatorImpl
        extends Authenticator
    {
        //-- Attributes
        private String _user;
        private String _password;

        //-- Constructors
        public AuthenticatorImpl (String user, String password)
        {
            _user = user;
            _password = password;
        }

        //-- Authenticator overrides
        @Override
        public PasswordAuthentication getPasswordAuthentication ()
        {
            return new PasswordAuthentication (_user, _password);
        }

    }
}
