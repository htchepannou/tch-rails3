/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.api.Resource;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This resource redirect the user to another location
 * 
 * @author herve
 *
 * @see com.tchepannou.rails.core.api.ActionController#redirectTo(java.lang.String)
 * @see com.tchepannou.rails.core.api.ActionController#redirectTo(java.lang.String, java.util.Map)
 */
public class RedirectResource
    implements Resource
{
    //-- Attribute
    private String _url;
    private Map _parameters = new HashMap ();
    private HttpServletRequest _request;

    //-- Public methods
    public RedirectResource (String url, Map parameters, HttpServletRequest request)
    {
        _url = url;
        _parameters = parameters;
        _request = request;
    }


    //-- Resource overrides
    public void output (HttpServletResponse response)
        throws IOException
    {
        /* URL */
        StringBuilder sb = new StringBuilder ();

        if ( _url.startsWith ("http://") || _url.startsWith ("https://") )
        {
            sb.append (_url);
        }
        else if ( _url.startsWith ("/") )
        {
            sb.append (_request.getContextPath ()).append (_url);
        }
        else
        {
            String path = _request.getPathInfo ();
            int i = path.lastIndexOf ("/");
            if ( i == 0 )
            {
                path = path + "/" + _url;
            }
            else
            {
                path = path.substring (0, i) + "/" + _url;
            }
            sb.append (_request.getContextPath ()).append (path);
        }

        /* Parameters */
        if ( _parameters != null && _parameters.size () > 0)
        {
            if (sb.indexOf ("?") < 0)
            {
                sb.append ('?');
            }
            appendQueryString (sb, _parameters);
        }

        /* Redirect */
        response.sendRedirect (sb.toString ());
    }

    private StringBuilder appendQueryString (StringBuilder url, Map params)
        throws IOException
    {
        int count = 0;

        for ( Object name: params.keySet () )
        {
            if ( count > 0 )
            {
                url.append ('&');
            }

            Object value = params.get (name);
            if ( value instanceof String[] )
            {
                String[] values = ( String[] ) value;
                for ( int i = 0; i < values.length; i++ )
                {
                    if ( i > 0 )
                    {
                        url.append ('&');
                    }
                    String val = values[i];
                    if (val != null)
                    {
                        String xvalue = URLEncoder.encode (val, DEFAULT_ENCODING);
                        url.append (name).append ('=').append (xvalue);
                    }
                }
            }
            else if (value != null)
            {
                String xvalue = URLEncoder.encode (value.toString (), DEFAULT_ENCODING);
                url.append (name).append ('=').append (xvalue);
            }

            count++;
        }

        return url;
    }

    public String getEncoding ()
    {
        return "utf-8";
    }
}
