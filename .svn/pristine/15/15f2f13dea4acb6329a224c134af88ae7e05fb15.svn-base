/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.api.Resource;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * Abstract implementation of @{Resource}
 * 
 * @author herve
 */
public abstract class AbstractResource
    implements Resource
{    
    //-- Attribute
    private String _contentType;


    //-- Constructor
    public AbstractResource ()
    {
    }
    public AbstractResource (String contentType)
    {
        _contentType = contentType;
    }


    //-- Abstract methods
    protected abstract void output (OutputStream out)
        throws IOException;


    //-- Resource overrides
    public void output (HttpServletResponse response)
        throws IOException
    {
        String contentType = getContentType ();
        if (contentType != null)
        {
            response.setContentType (contentType);
        }
        output (response.getOutputStream ());
    }

    //-- Getter/Setter
    public String getContentType ()
    {
        return _contentType;
    }
}
