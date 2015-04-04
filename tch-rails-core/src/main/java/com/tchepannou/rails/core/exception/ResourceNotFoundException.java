/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.exception;

/**
 * This exception is thrown when a render
 *
 * @author herve
 */
public class ResourceNotFoundException 
    extends RenderException
{

    public ResourceNotFoundException (String message, String path, int errorNumber, Throwable cause)
    {
        super (message, path, errorNumber, cause);
    }

}
