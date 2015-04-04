/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletInputStream;

/**
 *
 * @author herve
 */
public class MockServletInputStream
    extends ServletInputStream
{
    private InputStream _in;
    private BufferedReader _reader;

    
    //-- Public
    public void setInputStream (InputStream in)
    {
        _in = in;
        _reader = new BufferedReader ( new InputStreamReader (in));
    }

    public BufferedReader getReader ()
    {
        return _reader;
    }

    //-- ServletInputStream
    @Override
    public int read ()
        throws IOException
    {
        return _in.read ();
    }

}
