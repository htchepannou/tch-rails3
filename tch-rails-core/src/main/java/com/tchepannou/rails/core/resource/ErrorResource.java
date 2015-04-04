/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.api.Resource;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 * This resource returns a HTTP error to a client.
 *
 * @author herve
 * 
 * @see  ActionController#sendError(int)
 * @see  ActionController#sendError(int, java.lang.String)
 */
public class ErrorResource
    implements Resource
{
    //-- Attribute
    private int _error;
    private String _message;


    //-- Constructor
    public ErrorResource (int error, String message)
    {
        _error = error;
        _message = message;
    }


    //-- Resource overrides
    public void output (HttpServletResponse response)
        throws IOException
    {
        response.sendError (_error, _message);
    }

    public String getEncoding ()
    {
        return "utf-8";
    }
}
