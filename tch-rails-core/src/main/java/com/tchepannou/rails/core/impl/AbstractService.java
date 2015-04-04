/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.impl;

import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.api.ServiceContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContext;



/**
 * Abstract implementation of {@link Service}
 * 
 * @author herve
 */
public  abstract class AbstractService
    implements Service
{
    //-- Attribute
    private ServiceContext _serviceContext;
    private boolean _initialized;

    //-- Public method
    public ServiceContext getServiceContext ()
    {
        return _serviceContext;
    }

    public boolean isInitialized ()
    {
        return _initialized;
    }

    protected Service findService (Class<? extends Service> type)
    {
        return _serviceContext.getContainerContext ().findService (type);
    }


    
    //-- Service override
    public void init (ServiceContext context)
    {
        _serviceContext = context;
        _initialized = true;
    }

    public void destroy ()
    {
        _serviceContext = null;
        _initialized = false;
    }


    //-- Protected methods
    protected Properties loadConfigurationAsProperties (String confname)
        throws IOException
    {
        ServletContext context = getServiceContext ().getContainerContext ().getServletContext ();
        String path = context.getRealPath ("/WEB-INF/" + confname);
        Properties p = new Properties ();
        if (path != null)
        {
            File f = new File (path);
            InputStream in = null;
            try
            {
                in = new FileInputStream (f);
                p.load (in);
            }
            finally
            {
                if (in != null)
                {
                    in.close ();
                }
            }
        }
        return p;
    }

}
