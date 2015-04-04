/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.Service;
import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 * MessageQueue service
 * 
 * @author herve
 */
public interface JMSService
    extends Service
{
    /**
     * Returns the JMS connection factory
     */
    public ConnectionFactory getConnectionFactory ();

    /**
     * Send a message to a JMS topic
     *
     * @param JMSService Name of the JMS topic
     * @param message Message send
     *
     * @throws JMSException if any error occurs
     */
    public void sendMessage (String topic, Serializable msg)
        throws JMSException;
}
