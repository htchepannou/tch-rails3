package ${package}.action;

import com.tchepannou.rails.core.annotation.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Home page
 *
 */
@Template (name="default")
public class HomeActionController
    extends BaseActionController
{
    //-- Static Methods
    private static final Logger LOG = LoggerFactory.getLogger(HomeActionController.class);


    //-- Public
    public void index()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("index()");
        }
    }
}
