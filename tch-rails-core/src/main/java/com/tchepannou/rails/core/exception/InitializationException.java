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
public class InitializationException
    extends RuntimeException
{

    public InitializationException (Throwable cause)
    {
        super ( cause);
    }

    public InitializationException (String message, Throwable cause)
    {
        super (message, cause);
    }

    public InitializationException (String message)
    {
        super (message);
    }

    public InitializationException ()
    {
    }

}
