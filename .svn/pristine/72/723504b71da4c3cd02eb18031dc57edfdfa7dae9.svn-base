/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.servlet;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 *
 * @author herve
 */
public class MockHttpSession
    implements HttpSession
{
    //-- Attribute
    private String _id = UUID.randomUUID ().toString ();
    private long _creationTime = System.currentTimeMillis ();
    private int _maxInactiveInterval = Integer.MAX_VALUE;
    private ServletContext _servletContext;
    private Map<String, Object> _attributes = new HashMap<String, Object> ();
    private boolean _valid = true;
    private long _lastAccessedTime = -1;


    //-- Public
    public MockHttpSession(ServletContext sc)
    {
        _servletContext = sc;
    }


    //-- Public
    public boolean isValid ()
    {
        if (_lastAccessedTime > 0)
        {
            long now = System.currentTimeMillis ();
            long diff = (now - _lastAccessedTime)/1000;
            return diff < _maxInactiveInterval && _valid;
        }
        else
        {
            return _valid;
        }
    }

    public void setLastAccessedTime (long lastAccessedTime)
    {
        this._lastAccessedTime = lastAccessedTime;
    }



    //-- HttpSession
    public long getCreationTime ()
    {
        return _creationTime;
    }

    public String getId ()
    {
        return _id;
    }

    public long getLastAccessedTime ()
    {
        return _lastAccessedTime;
    }

    public ServletContext getServletContext ()
    {
        return _servletContext;
    }

    public void setMaxInactiveInterval (int interval)
    {
        _maxInactiveInterval = interval;
    }

    public int getMaxInactiveInterval ()
    {
        return _maxInactiveInterval;
    }

    public HttpSessionContext getSessionContext ()
    {
        return null;
    }

    public Object getAttribute (String name)
    {
        return _attributes.get (name);
    }

    public Object getValue (String name)
    {
        return null;
    }

    public Enumeration getAttributeNames ()
    {
        return new Vector (_attributes.keySet ()).elements ();
    }

    public String[] getValueNames ()
    {
        return null;
    }

    public void setAttribute (String name, Object value)
    {
        _attributes.put (name, value);
    }

    public void putValue (String name, Object value)
    {
    }

    public void removeAttribute (String name)
    {
        _attributes.remove (name);
    }

    public void removeValue (String name)
    {
    }

    public void invalidate ()
    {
        _valid = false;
    }

    public boolean isNew ()
    {
        return false;
    }

}
