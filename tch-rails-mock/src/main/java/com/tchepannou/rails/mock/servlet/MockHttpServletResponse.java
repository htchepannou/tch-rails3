/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author herve
 */
public class MockHttpServletResponse
    implements HttpServletResponse
{
    //-- Atttributes
    private List<Cookie> _cookies = new ArrayList<Cookie> ();
    private Map <String, List> _headers = new HashMap<String, List> ();
    private String _redirectUrl;
    private int _status = 200;
    private String _statusMessage = "OK";
    private String _characterEncoding;
    private String _contentType = "UTF-8";
    private Locale _locale = Locale.getDefault ();
    private int _bufferSize;
    private int _contentLength;
    private MockServletOutputStream _out = new MockServletOutputStream ();
    
    //-- Public method
    public String getRedirectUrl ()
    {
        return _redirectUrl;
    }

    public int getStatus ()
    {
        return _status;
    }

    public String getStatusMessage ()
    {
        return _statusMessage;
    }

    public int getContentLength ()
    {
        return _contentLength;
    }

    public String getContentAsString ()
    {
        return _out.getContentAsString ();
    }

    public Cookie getCookie (String name)
    {
        for (Cookie c : _cookies)
        {
            if (c.getName ().equals(name))
            {
                return c;
            }
        }
        return null;
    }



    //-- HttpServletResponse overrides
    public void addCookie (Cookie cookie)
    {
        _cookies.add (cookie);
    }

    public boolean containsHeader (String name)
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public String encodeURL (String url)
    {
        try
        {
            return URLEncoder.encode (url, _contentType);
        }
        catch (IOException e)
        {
            throw new IllegalStateException ("Unexpected error", e);
        }
    }

    public String encodeRedirectURL (String url)
    {
        return encodeURL (url);
    }

    public String encodeUrl (String url)
    {
        return encodeURL (url);
    }

    public String encodeRedirectUrl (String url)
    {
        return encodeURL (url);
    }

    public void sendError (int sc, String msg)
        throws IOException
    {
        _status = sc;
        _statusMessage = msg;
    }

    public void sendError (int sc)
        throws IOException
    {
        sendError (sc, null);
    }

    public void sendRedirect (String location)
        throws IOException
    {
        _status = 302;
        _redirectUrl = location;
    }

    public void setDateHeader (String name, long date)
    {
        setHeader (name, date);
    }

    public void addDateHeader (String name, long date)
    {
        addHeader (name, date);
    }

    public void setHeader (String name, String value)
    {
        setHeader (name, (Object)value);
    }

    public void addHeader (String name, String value)
    {
        addHeader (name, (Object)value);
    }

    public void setIntHeader (String name, int value)
    {
        addHeader (name, value);
    }

    public void addIntHeader (String name, int value)
    {
        setHeader (name, value);
    }

    private void addHeader(String name, Object value)
    {
        List values = _headers.get (name);
        if (values == null)
        {
            values = new ArrayList ();
            _headers.put (name, values);
        }
        values.add(value);
    }
    private void setHeader (String name, Object value)
    {
        _headers.remove (name);
        addHeader (name, value);
    }

    public void setStatus (int sc)
    {
        setStatus (sc, null);
    }

    public void setStatus (int sc, String sm)
    {
        _status = sc;
        _statusMessage = sm;
    }

    public String getCharacterEncoding ()
    {
        return _characterEncoding;
    }

    public String getContentType ()
    {
        return _contentType;
    }

    public ServletOutputStream getOutputStream ()
        throws IOException
    {
        return _out;
    }

    public PrintWriter getWriter ()
        throws IOException
    {
        return _out.getWriter ();
    }

    public void setCharacterEncoding (String charset)
    {
        _characterEncoding = charset;
    }

    public void setContentLength (int len)
    {
        _contentLength = len;
    }

    public void setContentType (String type)
    {
        _contentType = type;
    }

    public void setBufferSize (int size)
    {
        _bufferSize = size;
    }

    public int getBufferSize ()
    {
        return _bufferSize;
    }

    public void flushBuffer ()
        throws IOException
    {
    }

    public void resetBuffer ()
    {
    }

    public boolean isCommitted ()
    {
        return true;
    }

    public void reset ()
    {
    }

    public void setLocale (Locale loc)
    {
        _locale = loc;
    }

    public Locale getLocale ()
    {
        return _locale;
    }

}
