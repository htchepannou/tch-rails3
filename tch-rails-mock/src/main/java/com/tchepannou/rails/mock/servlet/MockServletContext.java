/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.servlet;

import com.tchepannou.util.MimeUtil;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 *
 * @author herve
 */
public class MockServletContext
    implements ServletContext
{
    public static final int VERSION_MAJOR = 2;
    public static final int VERSION_MINOR = 4;

    //-- Attribute
    private String _path;
    private Map<String, String> _parameters = new HashMap<String, String> ();
    private Map<String, Object> _attributes = new HashMap<String, Object> ();


    //-- Public
    public void setInitParameter(String name, String value)
    {
        _parameters.put (name, value);
    }

    //-- ServletContext overrides
    public ServletContext getContext (String uripath)
    {
        return new MockServletContext ();
    }

    public int getMajorVersion ()
    {
        return VERSION_MAJOR;
    }

    public int getMinorVersion ()
    {
        return VERSION_MINOR;
    }

    public String getMimeType (String file)
    {
        return MimeUtil.getInstance ().getMimeTypeByFile (file);
    }

    public Set getResourcePaths (String path)
    {
        return new HashSet ();
    }

    public URL getResource (String path)
        throws MalformedURLException
    {
        return getClass ().getClassLoader ().getResource (path);
    }

    public InputStream getResourceAsStream (String path)
    {
        return getClass ().getClassLoader ().getResourceAsStream (path);
    }

    public RequestDispatcher getRequestDispatcher (String path)
    {
        return null;
    }

    public RequestDispatcher getNamedDispatcher (String name)
    {
        return null;
    }

    public Servlet getServlet (String name)
        throws ServletException
    {
        return null;
    }

    public Enumeration getServlets ()
    {
        return null;
    }

    public Enumeration getServletNames ()
    {
        return null;
    }

    public void log (String msg)
    {
        System.out.println (msg);
    }

    public void log (Exception exception, String msg)
    {
        System.out.println (msg);
        exception.printStackTrace ();
    }

    public void log (String msg, Throwable throwable)
    {
        System.out.println (msg);
        throwable.printStackTrace ();
    }

    public String getRealPath (String path)
    {
        URL url = getClass ().getResource (path);
        if (url != null)
        {
            String xpath = url.toString ();
            return xpath.startsWith ("file:") ? xpath.substring (5) : path;
        }
        else
        {
            return null;
        }
    }

    public String getServerInfo ()
    {
        return getClass ().getName ();
    }

    public String getInitParameter (String name)
    {
        return _parameters.get (name);
    }

    public Enumeration getInitParameterNames ()
    {
        return new Vector (_parameters.keySet ()).elements ();
    }

    public Object getAttribute (String name)
    {
        return _attributes.get (name);
    }

    public Enumeration getAttributeNames ()
    {
        return  new Vector (_attributes.keySet ()).elements ();
    }

    public void setAttribute (String name, Object object)
    {
        _attributes.put (name, object);
    }

    public void removeAttribute (String name)
    {
        _attributes.remove (name);
    }

    public String getServletContextName ()
    {
        return "";
    }

}
