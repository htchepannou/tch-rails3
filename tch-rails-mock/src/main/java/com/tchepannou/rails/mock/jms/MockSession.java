/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

/**
 *
 * @author herve
 */
public class MockSession
    implements Session
{
    //-- Attribute
    private boolean _transacted;
    private int _ack;
    private MessageListener _listener;

    //-- Constructor
    public MockSession(boolean transacted, int ack)
    {
        _transacted = transacted;
        _ack = ack;
    }


    //-- Session override
    public BytesMessage createBytesMessage ()
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public MapMessage createMapMessage ()
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public Message createMessage ()
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public ObjectMessage createObjectMessage ()
        throws JMSException
    {
        return createObjectMessage (null);
    }

    public ObjectMessage createObjectMessage (Serializable obj)
        throws JMSException
    {
        return new MockMessage (obj);
    }

    public StreamMessage createStreamMessage ()
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public TextMessage createTextMessage ()
        throws JMSException
    {
        return createTextMessage (null);
    }

    public TextMessage createTextMessage (String string)
        throws JMSException
    {
        return new MockMessage (string);
    }

    public boolean getTransacted ()
        throws JMSException
    {
        return _transacted;
    }

    public int getAcknowledgeMode ()
        throws JMSException
    {
        return _ack;
    }

    public void commit ()
        throws JMSException
    {
    }

    public void rollback ()
        throws JMSException
    {
    }

    public void close ()
        throws JMSException
    {
    }

    public void recover ()
        throws JMSException
    {
    }

    public MessageListener getMessageListener ()
        throws JMSException
    {
        return _listener;
    }

    public void setMessageListener (MessageListener listener)
        throws JMSException
    {
        _listener = listener;
    }

    public void run ()
    {
    }

    public MessageProducer createProducer (Destination dest)
        throws JMSException
    {
        return new MockMessageProducer (dest);
    }

    public MessageConsumer createConsumer (Destination dest)
        throws JMSException
    {
        return createConsumer(dest, null, false);
    }

    public MessageConsumer createConsumer (Destination dest, String selector)
        throws JMSException
    {
        return createConsumer(dest, selector, false);
    }

    public MessageConsumer createConsumer (Destination dest, String selector, boolean bln)
        throws JMSException
    {
        MockMessageConsumer consumer = new MockMessageConsumer (dest, selector);
        if (dest instanceof MockDestination)
        {
            ((MockDestination)dest).addMessageConsumer (consumer);
        }
        return consumer;
    }

    public Queue createQueue (String name)
        throws JMSException
    {
        return MockDestination.get (name, false);
    }

    public Topic createTopic (String name)
        throws JMSException
    {
        return MockDestination.get (name, true);
    }

    public TopicSubscriber createDurableSubscriber (Topic topic, String name)
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public TopicSubscriber createDurableSubscriber (Topic topic, String name, String selector, boolean bln)
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public QueueBrowser createBrowser (Queue queue)
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public QueueBrowser createBrowser (Queue queue, String string)
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public TemporaryQueue createTemporaryQueue ()
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public TemporaryTopic createTemporaryTopic ()
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public void unsubscribe (String string)
        throws JMSException
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

}
