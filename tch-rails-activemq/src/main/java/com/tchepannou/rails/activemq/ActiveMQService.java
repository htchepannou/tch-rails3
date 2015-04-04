/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.activemq;

import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.impl.AbstractService;
import com.tchepannou.rails.core.service.JMSService;
import com.tchepannou.rails.core.util.JMSUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class ActiveMQService
    extends AbstractService
    implements JMSService
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (ActiveMQService.class);

    //-- Attribute
    private String _url;
    private String _user;
    private String _password;
    private ActiveMQConnectionFactory __cf;

    //-- MessagingService override
    public ConnectionFactory getConnectionFactory ()
    {
        if (__cf == null)
        {
            __cf = new ActiveMQConnectionFactory (_user, _password, _url);
        }
        return __cf;
    }

    public void sendMessage (String topic, Serializable msg)
        throws JMSException
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("sendMessage(" + topic + "," + msg + ")");
        }

        ConnectionFactory cf = getConnectionFactory ();
        JMSUtil.sendMessage (topic, msg, cf);
    }



    //-- Service override
    @Override
    public void init (ServiceContext context)
    {
        try
        {
            LOG.info ("Initializing");

            super.init (context);
            configure ();
            
            LOG.info ("Initialized");
        }
        catch (IOException e)
        {
            throw new InitializationException ("Unable to start", e);
        }
    }

    @Override
    public void destroy ()
    {
        LOG.info ("Destroying");
        
        super.destroy ();
        __cf = null;
        
        LOG.info ("Destroyed");
    }


    //-- Private methods
    private void configure ()
        throws IOException
    {
        Properties props = loadConfigurationAsProperties ("rails-activemq.properties");
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("ActiveMQ Properties");
            for (Object name : props.keySet ())
            {
                LOG.debug (" " + name + "=" + props.get (name));
            }
        }
        
        _url = props.getProperty ("url");
        _user = props.getProperty ("user");
        _password = props.getProperty ("password");        
    }


    //-- Getter/Setter
    public String getUrl ()
    {
        return _url;
    }

    public String getUser ()
    {
        return _user;
    }

    public String getPassword ()
    {
        return _password;
    }
}
