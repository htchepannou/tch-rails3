/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 *
 * @author herve
 */
public class MockConnectionFactory 
    implements ConnectionFactory
{
    public Connection createConnection ()
        throws JMSException
    {
        return new MockConnection ();
    }

    public Connection createConnection (String string, String string1)
        throws JMSException
    {
        return new MockConnection ();
    }
}
