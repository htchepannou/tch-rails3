/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;

/**
 *
 * @author herve
 */
public class MockServletOutputStream 
    extends ServletOutputStream
{
    private ByteArrayOutputStream _out = new ByteArrayOutputStream ();
    private PrintWriter _writer = new PrintWriter (_out);

    //-- Public method
    public PrintWriter getWriter ()
    {
        return _writer;
    }

    public String getContentAsString ()
    {
        return _out.toString ();
    }

    //-- ServletOutputStream overrides
    @Override
    public void write (int i)
        throws IOException
    {
        _out.write (i);
    }
}
