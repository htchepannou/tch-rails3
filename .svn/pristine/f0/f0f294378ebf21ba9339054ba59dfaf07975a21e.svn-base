package com.tchepannou.rails.engine.servlet;

import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.service.PersistenceServiceThreadLocal;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.util.ServletUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page decorator
 * 
 * @author herve
 */
public class PersistenceFilter
    implements Filter
{
    //-- Static Attributes
    private static final Logger LOG = LoggerFactory.getLogger (PersistenceFilter.class);


    //-- Attribute
    private FilterConfig _config;


    //-- Filter overrides
    public void doFilter (ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException,
               ServletException
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("doFilter(" + ServletUtil.toRequestURL (req) + ")");
        }

        if ( req instanceof HttpServletRequest )
        {
            HttpServletRequest request = ( HttpServletRequest ) req;
            if ( res instanceof HttpServletResponse )
            {
                HttpServletResponse response = ( HttpServletResponse ) res;
                doFilter (request, response, chain);
            }
        }
    }

    public void init (FilterConfig config)
    {
        LOG.info ("Initializing");

        _config = config;
    }

    public void destroy ()
    {
        LOG.info ("Destroying");
    }


    //-- Private methods
    private void doFilter (HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws IOException,
               ServletException
    {
        try
        {
            chain.doFilter (request, response);
        }
        finally
        {
            /* close connection */
            try
            {
                PersistenceService ps = getPersistenceService ();
                if (ps != null)
                {
                    if (LOG.isDebugEnabled ())
                    {
                        LOG.debug ("Closing PersitenceService Connection");
                    }
                    ps.closeConnection ();
                }
            }
            finally
            {
                /* release thread locals */
                PersistenceServiceThreadLocal.remove ();
            }
        }
    }

    private PersistenceService getPersistenceService ()
    {
        Engine engine = Engine.getInstance (_config.getServletContext ());
        PersistenceService s = (PersistenceService)engine.findService (PersistenceService.class);
        return s;
    }
}
