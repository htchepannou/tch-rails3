/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.impl;

import com.tchepannou.rails.core.api.FilePart;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.fileupload.FileItem;

/**
 *
 * @author herve
 */
public class FilePartImpl 
    implements FilePart
{
    //-- Attribute
    private FileItem _file;

    //-- Public
    public FilePartImpl (FileItem file)
    {
        _file = file;
    }


    //-- FilePart override
    public String getName ()
    {
        return _file.getName ();
    }

    public long getSize ()
    {
        return _file.getSize ();
    }

    public InputStream getInputStream ()
        throws IOException
    {
        return _file.getInputStream ();
    }

}
