/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Output a string
 * 
 * @author herve
 */
public class TextResource
    extends AbstractResource
{
    //-- Attribute
    private String _text;


    //-- Constructor
    public TextResource (String text)
    {
        _text = text;
    }
    public TextResource (String text, String contentType)
    {
        super (contentType);
        _text = text;
    }


    //-- Resource overrides
    public void output (OutputStream out)
        throws IOException
    {
        out.write (_text.getBytes ());
    }
}
