package com.tchepannou.rails.engine.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletOutputStream;

/**
 * 
 *
 * @author herve
 */
class BufferedResponseStream
    extends ServletOutputStream
{
    //-- Attributes
    protected ByteArrayOutputStream _os = null;
    protected boolean _closed = false;

    //-- Constructor
    public BufferedResponseStream ()
        throws IOException
    {
        super ();
        
        _closed = false;
        _os = new ByteArrayOutputStream ();
    }


    //-- Public method
    public byte[] toByteArray ()
    {
        return _os.toByteArray ();
    }

    //-- ServletOutputStream overrides
    @Override
    public void close ()
        throws IOException
    {
//        if ( _closed )
//        {
//            throw new IOException ("This output stream has already been closed");
//        }

        _closed = true;
    }

    @Override
    public void flush ()
        throws IOException
    {
        _os.flush ();
    }

    @Override
    public void write (int b)
        throws IOException
    {
        if ( _closed )
        {
            throw new IOException ("Cannot write to a closed output stream");
        }
        _os.write (( byte ) b);
    }

    @Override
    public void write (byte[] b)
        throws IOException
    {
        write (b, 0, b.length);
    }

    @Override
    public void write (byte[] b, int off, int len)
        throws IOException
    {
        //System.out.println("writing...");
        if ( _closed )
        {
            throw new IOException ("Cannot write to a closed output stream");
        }
        _os.write (b, off, len);
    }

    public boolean closed ()
    {
        return this._closed;
    }

    public void reset ()
    {
        //noop
    }
}
