/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.util.IOUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This resource downloads a data stream to the client.
 *
 * @author herve
 *
 * @see com.tchepannou.rails.core.api.ActionController#renderStream(java.io.InputStream, java.lang.String)
 */
public class InputStreamResource
    extends AbstractResource
{
    //-- Attribute
    private InputStream _in;


    //-- Constructor
    public InputStreamResource (InputStream in, String contentType)
    {
        super (contentType);

        _in = in;
    }


    //-- Resource overrides
    public void output (OutputStream out)
        throws IOException
    {
        IOUtil.copy (_in, out);
    }
}
