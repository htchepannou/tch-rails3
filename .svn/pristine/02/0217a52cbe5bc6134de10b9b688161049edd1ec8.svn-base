/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.action;

import com.tchepannou.rails.core.annotation.Template;
import com.tchepannou.rails.core.service.OptionService;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Action controller for error pages
 */
@Template (name="error")
public class ErrorActionController
    extends BaseActionController
{
    //-- Static Method
    private static final Logger LOG = LoggerFactory.getLogger (ErrorActionController.class);
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";


    public void e404 ()
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("e404()");
        }
        
        addViewVariable ("remoteIP", getRemoteIP ());
    }

    public void e403 ()
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("e403()");
        }
        
        addViewVariable ("remoteIP", getRemoteIP ());
    }

    public void e500 ()
    {
        if ( LOG.isTraceEnabled () )
        {
            LOG.trace ("e500()");
        }

        addViewVariable ("remoteIP", getRemoteIP ());
        
        HttpServletRequest req = getRequest ();
        Throwable exception = (Throwable)req.getAttribute ("javax.servlet.error.exception");
        notifyError (req, exception);
    }

    public void fail ()
    {
        throw new RuntimeException ("failure");
    }
    
    
    //-- Protected methods
    protected void notifyError (HttpServletRequest req, Throwable exception)
    {
        OptionService os = (OptionService)findService (OptionService.class);        
        String to = os.get ("mail.error.to", null);
        
        if (to != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat (DATE_FORMAT_NOW);
            HashMap params = new HashMap ();
            params.put ("to", to);
            params.put ("exception", exception);
            params.put ("requestURL", req.getAttribute ("javax.servlet.forward.request_uri"));
            params.put ("remoteIP", getRemoteIP ());
            params.put ("timestamp", sdf.format (Calendar.getInstance ().getTime ()));
            params.put ("userAgent", req.getHeader ("User-Agent"));
            params.put ("user", getUser ());

            // Deliver
            if ( LOG.isDebugEnabled () )
            {
                LOG.debug ("Sending error details to " + to);
            }
            try
            {
                deliver ("/error/e500", params);
            }
            catch ( Exception ex )
            {
                LOG.warn ("Error delivering the error 500 email", ex);
            }
        }
    }
}
