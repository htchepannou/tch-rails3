/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

/**
 *
 * @author herve
 */
public class MockJMSServer
{
    public static void start ()
    {
        // clear all the topics
        MockDestination.clear ();
    }

    public static void stop ()
    {

    }
}
