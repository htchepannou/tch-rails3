package com.tchepannou.rails.engine.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 
 * 
 * @author herve
 */
class BufferedResponseWrapper
    extends HttpServletResponseWrapper
{
    //-- Attributes
    protected ServletOutputStream _stream = null;
    protected PrintWriter _writer = null;
    protected int _status = 200;

    //-- Constructors
    public BufferedResponseWrapper (HttpServletResponse response)
    {
        super (response);
    }

    //-- Public Methods
    public byte[] toByteArray ()
    {
        return _stream != null
            ? ((BufferedResponseStream)_stream).toByteArray ()
            : new byte[] {};
    }

    //-- Public methods
    public ServletOutputStream createOutputStream ()
        throws IOException
    {
        return new BufferedResponseStream ();
    }

    public void finishResponse ()
        throws IOException
    {
        if ( _writer != null )
        {
            _writer.close ();
        }
        else
        {
            if ( _stream != null )
            {
                _stream.close ();
            }
        }
    }
    

    //-- HttpServletResponse override
    @Override
    public void flushBuffer ()
        throws IOException
    {
        _stream.flush ();
    }

    @Override
    public ServletOutputStream getOutputStream ()
        throws IOException
    {
        if ( _writer != null )
        {
            throw new IllegalStateException ("getWriter() has already been called!");
        }

        if ( _stream == null )
        {
            _stream = createOutputStream ();
        }
        return _stream;
    }

    @Override
    public PrintWriter getWriter ()
        throws IOException
    {
        if ( _stream != null )
        {
            throw new IllegalStateException ("getOutputStream() has already been called!");
        }

        if (_writer == null)
        {
            _stream = createOutputStream ();
            _writer = new PrintWriter (new OutputStreamWriter (_stream, "UTF-8"));
        }
        return _writer;
    }

    @Override
    public void setContentLength (int length)
    {
    }

    @Override
    public void sendRedirect (String location)
        throws IOException
    {
        super.sendRedirect (location);
        _status = 302;
    }

    @Override
    public void sendError (int sc)
        throws IOException
    {
        super.sendError (sc);
        _status = sc;
    }

    @Override
    public void sendError (int sc, String msg)
        throws IOException
    {
        super.sendError (sc, msg);
        _status = sc;
    }


    //-- Getter/Setter
    public int getStatus ()
    {
        return _status;
    }
}
