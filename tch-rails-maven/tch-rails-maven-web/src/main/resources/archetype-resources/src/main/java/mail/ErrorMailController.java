/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ${package}.mail;

import com.tchepannou.rails.core.api.MailController;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class ErrorMailController
    extends MailController
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (ErrorMailController.class);
    
    //-- Public methods
    public void e500 (HashMap data)
    {        
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("e500(" + data + ")");
        }
        
        String to = (String)data.get ("to");
        if (to != null)
        {
            addTo (to);
            
            addViewVariables (data);
        }
    }
}
