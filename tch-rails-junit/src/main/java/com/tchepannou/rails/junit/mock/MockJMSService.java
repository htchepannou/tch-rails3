/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.junit.mock;

import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.service.JMSService;
import com.tchepannou.rails.core.util.JMSUtil;
import com.tchepannou.rails.mock.jms.MockConnectionFactory;
import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 *
 * @author herve
 */
public class MockJMSService
    implements JMSService
{
    //-- Service overrides
    public ConnectionFactory getConnectionFactory ()
    {
        return new MockConnectionFactory ();
    }

    public void sendMessage (String topic, Serializable msg)
        throws JMSException
    {
        ConnectionFactory cf = getConnectionFactory ();
        JMSUtil.sendMessage (topic, msg, cf);
    }

    public void init (ServiceContext context)
    {
    }

    public void destroy ()
    {
    }
}
