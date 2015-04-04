package ${package}.action;

import com.tchepannou.rails.core.annotation.Template;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.core.service.UserService;
import com.tchepannou.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Login page
 *
 */
@Template (name="default")
public class LoginActionController
    extends BaseActionController
{
    //-- Static Methods
    private static final Logger LOG = LoggerFactory.getLogger(LoginActionController.class);


    //-- Public
    public void index()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("index()");
        }
        
        addViewVariable (PARAM_REDIRECT, getRequestParameter (PARAM_REDIRECT));
    }
    
    public void signin ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("auth()");
        }
        
        /* authenticate */
        String uname = getRequestParameter ("uname");
        String password = getRequestParameter ("password");
        User user = auth(uname, password);
        if (user == null)
        {
            addError ("<h3>Authentication Failed</h3><p>Your username or password is not valid</p>");
        }

        if (hasErrors ())
        {
            restoreView ("/login/index");
        }
        else
        {            
            /* login */
            setUser (user);

            /* Redirect */
            String redirect = getRequestParameter (PARAM_REDIRECT);
            if (!StringUtil.isEmpty (redirect) )
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Redirecting to " + redirect);
                }
                redirectTo (redirect);
            }
            else
            {
                redirectTo ("/");
            }        
        }        
    }
    
    
    //-- Protected
    /**
     * Authenticate the user.
     * This is a lousy implementation based on {@link com.tchepannou.rails.core.impl.PropertiesOptionService} -
     * user information are in <code>WEB-INF/rails-user.properties</code>
     * 
     * @param uname
     * @param password
     * @return User 
     */
    protected User auth (String uname, String password)
    {
        if ("admin".equals(uname))
        {
            UserService us = (UserService)findService (UserService.class);
            return us.findUser (1);
        }
        return null;
    }
}
