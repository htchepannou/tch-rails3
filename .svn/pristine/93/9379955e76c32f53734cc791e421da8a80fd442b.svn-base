/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Topic;

/**
 *
 * @author herve
 */
public class MockDestination 
    implements Destination, Topic, Queue
{
    //-- Static
    private static Map<String, MockDestination> _pool = new HashMap<String, MockDestination> ();

    //-- Attribute
    private boolean _topic;
    private String _name;
    private Set<MessageConsumer> _consumers = new HashSet<MessageConsumer> ();

    //-- Constructor
    private MockDestination (String name, boolean topic)
    {
        _name = name;
        _topic = topic;
    }


    //-- Public
    /**
     * Returns a destination from the destination pool
     */
    public static MockDestination get (String name, boolean topic)
    {
        MockDestination dest = _pool.get (name);
        if (dest == null)
        {
            dest = new MockDestination (name, topic);
            _pool.put (name, dest);
        }
        return dest;
    }

    /**
     * Clear all destination already created
     */
    public static void clear ()
    {
        _pool.clear ();
    }

    public void addMessageConsumer (MessageConsumer consumer)
    {
        _consumers.add (consumer);
    }

    public void send(Message msg)
    {
        for (MessageConsumer consumer : _consumers)
        {
            if (consumer instanceof MockMessageConsumer)
            {
                ((MockMessageConsumer)consumer).receive(msg);
            }
        }
    }

    public boolean isTopic ()
    {
        return _topic;
    }
    
    //--  Topic
    public String getTopicName ()
        throws JMSException
    {
        return _name;
    }


    //-- Queue
    public String getQueueName ()
        throws JMSException
    {
        return _name;
    }
}
