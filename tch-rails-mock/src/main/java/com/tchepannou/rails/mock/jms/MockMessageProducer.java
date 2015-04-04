/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;

/**
 *
 * @author herve
 */
public class MockMessageProducer
    implements MessageProducer
{
    //-- Attribute
    private boolean _disableMessageId;
    private boolean _disableMessageTimestamp;
    private int _deliveryMode;
    private int _priority;
    private long _timeToLive;
    private Destination _destination;


    //-- Constructor
    public MockMessageProducer (Destination destination)
    {
        _destination = destination;
    }

    
    //-- MessageProducer
    public void setDisableMessageID (boolean bln)
        throws JMSException
    {
        _disableMessageId = bln;
    }
    public boolean getDisableMessageID ()
        throws JMSException
    {
        return _disableMessageId;
    }

    public void setDisableMessageTimestamp (boolean bln)
        throws JMSException
    {
        _disableMessageTimestamp = bln;
    }
    public boolean getDisableMessageTimestamp ()
        throws JMSException
    {
        return _disableMessageTimestamp;
    }

    public void setDeliveryMode (int i)
        throws JMSException
    {
        _deliveryMode = i;
    }
    public int getDeliveryMode ()
        throws JMSException
    {
        return _deliveryMode;
    }

    public void setPriority (int i)
        throws JMSException
    {
        _priority = i;
    }
    public int getPriority ()
        throws JMSException
    {
        return _priority;
    }

    public void setTimeToLive (long l)
        throws JMSException
    {
        _timeToLive = l;
    }
    public long getTimeToLive ()
        throws JMSException
    {
        return _timeToLive;
    }

    public Destination getDestination ()
        throws JMSException
    {
        return _destination;
    }

    public void close ()
        throws JMSException
    {
    }

    public void send (Message msg)
        throws JMSException
    {
        send(msg, _deliveryMode, _priority, _timeToLive);
    }

    public void send (Message msg, int deliveryMode, int priority, long ttl)
        throws JMSException
    {
        send (_destination, msg, deliveryMode, priority, ttl);
    }

    public void send (Destination destination, Message msg)
        throws JMSException
    {
        send (_destination, msg, _deliveryMode, _priority, _timeToLive);
    }

    public void send (Destination destination, Message msg, int deliveryMode, int priority, long ttl)
        throws JMSException
    {
        if (destination instanceof MockDestination)
        {
            ((MockDestination)destination).send (msg);
        }
    }
}
