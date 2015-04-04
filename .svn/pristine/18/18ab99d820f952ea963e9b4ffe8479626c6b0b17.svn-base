package com.tchepannou.rails.engine.servlet;

import com.tchepannou.rails.core.annotation.Template;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.service.RenderService;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.interceptor.TemplateInterceptor;
import com.tchepannou.rails.engine.util.ServletUtil;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
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
public class TemplateFilter
    implements Filter
{
    //-- Static Attributes
    private static final Logger LOG = LoggerFactory.getLogger (TemplateFilter.class);
    private static final String TEMPLATE_PREFIX = "/template/";


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
        /* Process the request */
        BufferedResponseWrapper wrappedResponse = new BufferedResponseWrapper (response);
        try
        {
            chain.doFilter (request, wrappedResponse);
        }
        finally
        {
            wrappedResponse.finishResponse ();
        }
        byte[] content = wrappedResponse.toByteArray ();

        /* Apply the template */
        if (wrappedResponse.getStatus () != 302)    // dont apply the template when redirecting
        {
            Template template = (Template)request.getAttribute (TemplateInterceptor.ATTR_TEMPLATE);
            if ( template != null && canDecorate (response))
            {
                Writer writer = response.getWriter ();
                ActionController controller = (ActionController)request.getAttribute (TemplateInterceptor.ATTR_CONTROLLER);
                applyTemplate (request, template, content, controller, writer);
            }
            else
            {
                response.getOutputStream ().write (content);
            }
        }
    }

    private void applyTemplate (HttpServletRequest request, Template template, byte[] content, ActionController controller, Writer writer)
        throws IOException,
               ServletException
    {
        String pathInfo = request.getPathInfo ();
        int i = pathInfo != null ? pathInfo.lastIndexOf (".") : -1;
        String ext = i > 0 ? pathInfo.substring (i) : "";
        String path = TEMPLATE_PREFIX + template.name () + ext;
        
        Map data = new HashMap ();
        data.putAll (controller.getViewVariables ());
        data.put ("__template", template);
        data.put ("__content", new String(content));

        getRenderService ().render (path, data, writer);
    }

    private RenderService getRenderService ()
    {
        Engine engine = Engine.getInstance (_config.getServletContext ());
        RenderService s = (RenderService)engine.findService (RenderService.class);
        if (s == null)
        {
            throw new IllegalStateException ("Service not found: " + RenderService.class.getName ());
        }

        return s;
    }

    private boolean canDecorate (HttpServletResponse response)
    {
        String contentType = response.getContentType ();
        if (contentType == null )
        {
            return false;
        }
        else
        {
            return contentType.startsWith ("text/")
                || contentType.startsWith ("application/json");
        }
    }
}
