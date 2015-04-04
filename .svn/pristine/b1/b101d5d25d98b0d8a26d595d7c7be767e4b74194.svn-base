/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.servlet;

import com.tchepannou.util.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author herve
 */
public class MockHttpServletRequest 
    implements HttpServletRequest
{
    //-- Attribute
    private String _method = "GET";
    private String _pathInfo;
    private String _contextPath = "";
    private String _queryString;
    private String _remoteUser;
    private String _authType;
    private String _contentType;
    private String _characterEncoding;
    private int _contentLength;
    private String _remoteAddr;
    private MockHttpSession __session;
    private ServletContext _servletContext;
    private List<String> _roles = new ArrayList<String> ();
    private Map<String, Object> _attributes = new HashMap<String, Object> ();
    private MockServletInputStream _in = new MockServletInputStream ();
    private Map<String, String[]> _parameters = new HashMap<String, String[]> ();
    private List<Locale> _locales = new ArrayList<Locale> ();
    private List<Cookie> _cookies = new ArrayList<Cookie> ();
    private Map <String, List> _headers = new HashMap<String, List> ();


    //-- Constructor
    public MockHttpServletRequest ()
    {

    }
    public MockHttpServletRequest (ServletContext sc)
    {
        _servletContext = sc;
    }


    //-- Public
    public void addCookie (Cookie cookie)
    {
        _cookies.add (cookie);
    }

    public void setInputStream (InputStream in)
    {
        _in.setInputStream (in);
    }
    
    public void addLocale (Locale loc)
    {
        _locales.add (loc);
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
        setHeader (name, value);
    }

    public void addHeader (String name, String value)
    {
        addHeader (name, value);
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

    public void setMethod (String method)
    {
        this._method = method;
    }

    public void setPathInfo (String pathInfo)
    {
        this._pathInfo = pathInfo;
    }

    public void setContextPath (String contextPath)
    {
        this._contextPath = contextPath;
    }

    public void setQueryString (String queryString)
    {
        this._queryString = queryString;
    }

    public void setAuthType (String authType)
    {
        this._authType = authType;
    }

    public void setRemoteUser (String remoteUser)
    {
        this._remoteUser = remoteUser;
    }

    public void addRole (String role)
    {
        _roles.add (role);
    }

    public void setContentLength (int contentLength)
    {
        this._contentLength = contentLength;
    }

    public void setContentType (String contentType)
    {
        this._contentType = contentType;
    }

    public void setRemoteAddr (String remoteAddr)
    {
        this._remoteAddr = remoteAddr;
    }

    public void setParameter (String name, String value)
    {
        if (value != null)
        {
            setParameterValues (name, new String [] {value});
        }
        else
        {
            setParameterValues (name, null);
        }
    }
    public void setParameterValues (String name, String[] values)
    {
        if (values != null)
        {
            _parameters.put (name, values);
        }
        else
        {
            _parameters.remove (name);
        }
    }

    public void setParameter (String name, byte value)
    {
        setParameter (name, String.valueOf (value));
    }
    public void setParameter (String name, int value)
    {
        setParameter (name, String.valueOf (value));
    }
    public void setParameter (String name, long value)
    {
        setParameter (name, String.valueOf (value));
    }
    public void setParameter (String name, float value)
    {
        setParameter (name, String.valueOf (value));
    }
    public void setParameter (String name, double value)
    {
        setParameter (name, String.valueOf (value));
    }
    public void setParameter (String name, boolean value)
    {
        setParameter (name, String.valueOf (value));
    }
    public void setParameter (String name, Object value)
    {
        if (value instanceof String[])
        {
            setParameterValues (name, (String[])value);
        }
        else if (value != null)
        {
            setParameter (name, value.toString ());
        }
        else
        {
            setParameter (name, null);
        }
    }
    public void setParameters (Map map)
    {
        for (Object name : map.keySet ())
        {
            Object value = map.get(name);
            setParameter(name.toString (), value);
        }
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

    
    //-- HttpServletReques
    public String getAuthType ()
    {
        return _authType;
    }

    public Cookie[] getCookies ()
    {
        return _cookies.toArray (new Cookie [] {});
    }

    public long getDateHeader (String name)
    {
        return StringUtil.toLong (getHeader (name), 0);
    }

    public String getHeader (String name)
    {
        Enumeration enu = getHeaders (name);
        return enu.hasMoreElements () ? enu.nextElement ().toString () : null;
    }

    public Enumeration getHeaders (String name)
    {
        List values = _headers.get (name);
        Vector vector = values != null ? new Vector (values) : new Vector ();
        return vector.elements ();
    }

    public Enumeration getHeaderNames ()
    {
        return new Vector (_headers.keySet ()).elements ();
    }

    public int getIntHeader (String name)
    {
        return StringUtil.toInt (getHeader (name), 0);
    }

    public String getMethod ()
    {
        return _method;
    }

    public String getPathInfo ()
    {
        return _pathInfo;
    }

    public String getPathTranslated ()
    {
        return null;
    }

    public String getContextPath ()
    {
        return _contextPath;
    }

    public String getQueryString ()
    {
        return _queryString;
    }

    public String getRemoteUser ()
    {
        return _remoteUser;
    }

    public boolean isUserInRole (String role)
    {
        return _roles.contains (role);
    }

    public Principal getUserPrincipal ()
    {
        return _remoteUser != null ?  new Principal () {

            public String getName ()
            {
                return _remoteUser;
            }
        } : null;
    }

    public String getRequestedSessionId ()
    {
        return null;
    }

    public String getRequestURI ()
    {
        return null;
    }

    public StringBuffer getRequestURL ()
    {
        return null;
    }

    public String getServletPath ()
    {
        return null;
    }

    public HttpSession getSession (boolean create)
    {
        if ((__session == null || !__session.isValid ()) && create)
        {
            __session = new MockHttpSession (_servletContext);
        }
        return __session;
    }

    public HttpSession getSession ()
    {
        return getSession (true);
    }

    public boolean isRequestedSessionIdValid ()
    {
        return true;
    }

    public boolean isRequestedSessionIdFromCookie ()
    {
        return false;
    }

    public boolean isRequestedSessionIdFromURL ()
    {
        return false;
    }

    public boolean isRequestedSessionIdFromUrl ()
    {
        return false;
    }

    public Object getAttribute (String name)
    {
        return _attributes.get (name);
    }

    public Enumeration getAttributeNames ()
    {
        return new Vector (_attributes.keySet ()).elements ();
    }

    public String getCharacterEncoding ()
    {
        return _characterEncoding;
    }

    public void setCharacterEncoding (String env)
        throws UnsupportedEncodingException
    {
        _characterEncoding = env;
    }

    public int getContentLength ()
    {
        return _contentLength;
    }

    public String getContentType ()
    {
        return _contentType;
    }

    public ServletInputStream getInputStream ()
        throws IOException
    {
        return _in;
    }

    public String getParameter (String name)
    {
        String[] values = getParameterValues (name);
        return values != null && values.length > 0 ? values[0] : null;
    }

    public Enumeration getParameterNames ()
    {
        return new Vector (_parameters.keySet ()).elements ();
    }

    public String[] getParameterValues (String name)
    {
        return _parameters.get (name);
    }

    public Map getParameterMap ()
    {
        return _parameters;
    }

    public String getProtocol ()
    {
        return null;
    }

    public String getScheme ()
    {
        return null;
    }

    public String getServerName ()
    {
        return null;
    }

    public int getServerPort ()
    {
        return 80;
    }

    public BufferedReader getReader ()
        throws IOException
    {
        return _in.getReader ();
    }

    public String getRemoteAddr ()
    {
        return _remoteAddr;
    }

    public String getRemoteHost ()
    {
        return _remoteAddr;
    }

    public void setAttribute (String name, Object o)
    {
        _attributes.put (name, o);
    }

    public void removeAttribute (String name)
    {
        _attributes.remove (name);
    }

    public Locale getLocale ()
    {
        return _locales.size () > 0 ? _locales.get(0) : null;
    }

    public Enumeration getLocales ()
    {
        return new Vector(_locales).elements ();
    }

    public boolean isSecure ()
    {
        return "https".equals(getProtocol ());
    }

    public RequestDispatcher getRequestDispatcher (String path)
    {
        return null;
    }

    public String getRealPath (String path)
    {
        return _servletContext != null ? _servletContext.getRealPath (path) : null;
    }

    public int getRemotePort ()
    {
        return 80;
    }

    public String getLocalName ()
    {
        return "localhost";
    }

    public String getLocalAddr ()
    {
        return "127.0.0.1";
    }

    public int getLocalPort ()
    {
        return 80;
    }

}
