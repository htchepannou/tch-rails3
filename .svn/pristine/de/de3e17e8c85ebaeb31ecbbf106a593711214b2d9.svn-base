package ${package}.action;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.util.MethodInvoker;
import java.io.File;

/**
 * Static Pages (images/css/javascript)
 */
public class StaticActionController 
    extends ActionController
{
    //-- Public methods
    public void render ()
    {
        String path = getRequest ().getPathInfo ();
        String xpath = getContext ().getContainerContext ().getServletContext ().getRealPath (path);
        if ( xpath != null )
        {
            File f = new File (xpath);
            renderFile (f);
        }
        else
        {
            sendError (404, xpath + " not found!!!");
        }
    }

    //-- ActionController override
    @Override
    public MethodInvoker getMethodInvoker (String path)
    {
        return new MethodInvoker ("render", this);
    }


}
