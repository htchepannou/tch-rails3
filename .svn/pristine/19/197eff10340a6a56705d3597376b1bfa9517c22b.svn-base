/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;

/**
 *
 * @author herve
 */
public class MockMessageConsumer
    implements MessageConsumer
{
    //-- Attribute
    private String _messageSelector;
    private MessageListener _listener;
    private Destination _destination;

    
    //-- Constructor
    public MockMessageConsumer (Destination dest, String selector)
    {
        _destination = dest;
        _messageSelector = selector;
    }

    //-- Public
    public void receive (Message msg)
    {
        if (_listener != null)
        {
            _listener.onMessage (msg);
        }
    }

    //-- Message Consummer override
    public String getMessageSelector ()
        throws JMSException
    {
        return _messageSelector;
    }

    public MessageListener getMessageListener ()
        throws JMSException
    {
        return _listener;
    }

    public void setMessageListener (MessageListener ml)
        throws JMSException
    {
        _listener = ml;
        if (_destination instanceof MockDestination)
        {
            ((MockDestination)_destination).addMessageConsumer (this);
        }
    }

    public Message receive ()
        throws JMSException
    {
        return null;
    }

    public Message receive (long l)
        throws JMSException
    {
        return null;
    }

    public Message receiveNoWait ()
        throws JMSException
    {
        return null;
    }

    public void close ()
        throws JMSException
    {
    }
}
