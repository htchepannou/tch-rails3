/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.junit;

import java.io.Serializable;
import javax.jms.JMSException;

/**
 *
 * @author herve
 */
public class MessageControllerTestCase
    extends ControllerTestCase
{
    //-- Constructor
    public MessageControllerTestCase ()
    {

    }
    public MessageControllerTestCase (String name)
    {
        super (name);
    }


    //-- Protected
    protected void sendMessage (String destination)
        throws JMSException
    {
        sendMessage (destination, null);
    }

    protected void sendMessage (String destination, Serializable msg)
        throws JMSException
    {
        getEngine ().getMessageContainer ().sendMessage (destination, msg);
    }
}
