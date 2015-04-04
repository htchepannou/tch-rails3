/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.exception;

import java.io.IOException;

/**
 * This exception is thrown when the rendering fails
 *
 * @author herve
 */
public class RenderException 
    extends IOException
{
    private  String _path;
    private int _errorNumber;

    public RenderException (String message, String path, int errorNumber, Throwable cause)
    {
        super (message, cause);

        _path = path;
        _errorNumber = errorNumber;
    }

    public String getPath ()
    {
        return _path;
    }

    public int getErrorNumber ()
    {
        return _errorNumber;
    }

}
