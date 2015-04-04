/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import javax.jms.Connection;
import javax.jms.ConnectionConsumer;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;
import javax.jms.Session;
import javax.jms.Topic;

/**
 *
 * @author herve
 */
public class MockConnection
    implements Connection
{
    //-- Attribute
    private String _clientID;
    private ExceptionListener _listener;
   
    //--Connection override
    public Session createSession (boolean transacted, int ack)
        throws JMSException
    {
        return new MockSession (transacted, ack);
    }

    public String getClientID ()
        throws JMSException
    {
        return _clientID;
    }

    public void setClientID (String clientID)
        throws JMSException
    {
        _clientID = clientID;
    }

    public ConnectionMetaData getMetaData ()
        throws JMSException
    {
        return new MockConnectionMetaData ();
    }

    public ExceptionListener getExceptionListener ()
        throws JMSException
    {
        return _listener;
    }

    public void setExceptionListener (ExceptionListener el)
        throws JMSException
    {
        _listener = el;
    }

    public void start ()
        throws JMSException
    {
    }

    public void stop ()
        throws JMSException
    {
    }

    public void close ()
        throws JMSException
    {
    }

    public ConnectionConsumer createConnectionConsumer (Destination dst, String selector, ServerSessionPool ssp, int i)
        throws JMSException
    {
        return new MockConnectionConsumer ();
    }

    public ConnectionConsumer createDurableConnectionConsumer (Topic topic, String subscriptionName, String selector, ServerSessionPool ssp, int i)
        throws JMSException
    {
        return new MockConnectionConsumer ();
    }

}
