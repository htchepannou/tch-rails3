/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.exception;

/**
 * This exception is thrown when the system initialization fails
 * 
 * @author herve
 */
public class InstantiationException
    extends RuntimeException
{

    public InstantiationException (Throwable cause)
    {
        super ( cause);
    }

    public InstantiationException (String message, Throwable cause)
    {
        super (message, cause);
    }

    public InstantiationException (String message)
    {
        super (message);
    }

    public InstantiationException ()
    {
    }

}
