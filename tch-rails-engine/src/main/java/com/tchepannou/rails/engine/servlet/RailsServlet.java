/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.servlet;

import com.tchepannou.rails.core.exception.ConfigurationException;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.EngineConfigurator;
import com.tchepannou.rails.engine.util.ServletUtil;
import com.tchepannou.util.MimeUtil;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the rails servlet
 *
 * @author herve
 */
public class RailsServlet
    extends HttpServlet
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (RailsServlet.class);

    //-- Attribute
    private Engine _engine;

    //-- HttpServlet overrides
    @Override
    protected void service (HttpServletRequest req, HttpServletResponse resp)
        throws ServletException,
               IOException
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("service(" + ServletUtil.toRequestURL (req) + ")");
        }

        /* IMPORTANT !!!! set the charset ! */
        if ( req.getCharacterEncoding () == null )
        {
            req.setCharacterEncoding ("UTF-8");
        }

        String path = req.getPathInfo ();
        String mime = MimeUtil.getInstance ().getMimeTypeByFile (path);
        if (StringUtil.isEmpty (mime))
        {
            mime = MimeUtil.HTML;
        }
        resp.setContentType (mime + ";charset=UTF-8");
        resp.setCharacterEncoding ("UTF-8");


        /* Handle the request */
        _engine.getActionContainer ().service (req, resp);
    }

    @Override
    public void destroy ()
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("destroy ()");
        }
        LOG.info ("Destroying");

        _engine.destroy ();
        super.destroy ();

        LOG.info ("Destroyed");
    }

    @Override
    public void init (ServletConfig config)
        throws ServletException
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("init (" + config + ")");
        }
        LOG.info ("Initializing");

        super.init (config);

        ServletContext sc = config.getServletContext ();
        EngineConfigurator cfg = new EngineConfigurator ();

        try
        {
            /* configure */
            LOG.info ("Configuring RAILS Engine");
            _engine = new Engine ();
            cfg.configure (_engine, sc);

            /* init */
            LOG.info ("Initializing RAILS Engine");
            _engine.init (sc);

            LOG.info ("Initialized");
        }
        catch ( ConfigurationException e )
        {
            LOG.error ("Configuration error. Initialization failed", e);
            throw new ServletException ("Configuration failed", e);
        }
    }
}
