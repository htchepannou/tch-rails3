/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.exception;

/**
 * This exception is thrown when the configuration files
 *
 * @author herve
 */
public class ConfigurationException 
    extends Exception
{
    public ConfigurationException (String message)
    {
        super (message);
    }
    public ConfigurationException (String message, Throwable cause)
    {
        super (message, cause);
    }
}
