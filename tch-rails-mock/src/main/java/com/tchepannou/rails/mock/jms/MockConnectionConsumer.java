/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import javax.jms.ConnectionConsumer;
import javax.jms.JMSException;
import javax.jms.ServerSessionPool;

/**
 *
 * @author herve
 */
public class MockConnectionConsumer
    implements ConnectionConsumer
{

    public ServerSessionPool getServerSessionPool ()
        throws JMSException
    {
        return null;
    }

    public void close ()
        throws JMSException
    {
    }

}
