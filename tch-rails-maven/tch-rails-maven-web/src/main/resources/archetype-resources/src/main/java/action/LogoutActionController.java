package ${package}.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logout
 * 
 */
public class LogoutActionController
    extends BaseActionController
{
    //-- Static Methods
    private static final Logger LOG = LoggerFactory.getLogger(LogoutActionController.class);


    //-- Public
    public void index()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("index()");
        }

        setUser (null);
        redirectTo ("/login");
    }
}
