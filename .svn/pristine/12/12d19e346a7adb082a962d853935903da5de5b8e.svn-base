/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.util.IOUtil;
import com.tchepannou.util.MimeUtil;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This resource downloads a file to the client.
 * 
 * @author herve
 *
 * @see  ActionController#renderFile(java.io.File)
 */
public class FileResource
    extends AbstractResource
{
    //-- Attribute
    private File _file;


    //-- Constructor
    public FileResource (File file)
    {
        _file = file;
    }


    //-- Resource overrides
    @Override
    public String getContentType ()
    {
        return MimeUtil.getInstance ().getMimeTypeByFile (_file.getName ());
    }




    public void output (OutputStream out)
        throws IOException
    {
        IOUtil.copy (_file, out);
    }
}
