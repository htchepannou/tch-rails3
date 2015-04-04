/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.util;

import java.io.Serializable;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * JMS API utility function
 * @author herve
 */
public class JMSUtil
{
    /**
     * Send a message to a JMS topic
     *
     * @param topic Name of the topic
     * @param msg Message to send
     * @param cf JMS Connection factory
     *
     * @throws JMSException if any error occurs
     */
    public static void sendMessage (String topic, Serializable msg, ConnectionFactory cf)
        throws JMSException
    {
        Connection connection = cf.createConnection();
        connection.start();
        try
        {
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Message message = session.createObjectMessage (msg);
            Destination dst = session.createTopic (topic);
            MessageProducer producer = session.createProducer(dst);
            producer.send (message);
            session.commit ();
        }
        finally
        {
            connection.close ();
        }
    }

}
