/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.core.service.UserService;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.container.ActionControllerContainer;
import com.tchepannou.rails.mock.servlet.MockHttpServletRequest;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.Locale;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class RequireUserInterceptorTest
    extends TestCase
    implements UserService
{
    private Engine _cc;
    private boolean _hasPermission;
    private ActionControllerContainer _container;

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _cc = new Engine ();
        _cc.setBasePackage ("com.tchepannou.rails");
        _cc.setLoginURL ("/login");
        _cc.setServletContext (new MockServletContext ());

        _cc.registerActionInterceptor (new RequireUserInterceptor ());
        _cc.registerService (UserService.class, this);

        _container = new ActionControllerContainer ();
        _container.init (_cc);

        _hasPermission = false;
    }


    //-- Test
    public void testNonSecure ()
        throws Exception
    {
        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/secure/nonSecure");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "nonSecure", resp.getContentAsString ());
    }

    public void testRequireUser ()
        throws Exception
    {
        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/secure/requireUser");
        req.getSession ().setAttribute (ActionController.SESSION_USER_ID, new Long(1));

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "requireUser", resp.getContentAsString ());
    }
    public void testRequireUserNoLogin ()
        throws Exception
    {
        String loginUrl = "/login?"+ ActionController.PARAM_REDIRECT + "=" + URLEncoder.encode("/secure/requireUser", "utf8");
        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/secure/requireUser");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 401, resp.getStatus ());
    }

    public void testRequirePermissionNoLogin ()
        throws Exception
    {
        String loginUrl = "/login?"+ ActionController.PARAM_REDIRECT + "=" + URLEncoder.encode("/secure/requirePermission", "utf8");
        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/secure/requirePermission");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 401, resp.getStatus ());
    }
    public void testRequirePermissionNo ()
        throws Exception
    {
        String loginUrl = "/login?"+ ActionController.PARAM_REDIRECT + "=" + URLEncoder.encode("/secure/requireUser", "utf8");
        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/secure/requirePermission");
        req.getSession ().setAttribute (ActionController.SESSION_USER_ID, new Long(1));

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _hasPermission = false;
        
        _container.service (req, resp);

        assertEquals ("HTTP status", 403, resp.getStatus ());
    }
    public void testRequirePermission ()
        throws Exception
    {
        String loginUrl = "/login?"+ ActionController.PARAM_REDIRECT + "=" + URLEncoder.encode("/secure/requireUser", "utf8");
        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/secure/requirePermission");
        req.getSession ().setAttribute (ActionController.SESSION_USER_ID, new Long(1));

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _hasPermission = true;

        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "requirePermission", resp.getContentAsString ());
    }
    
    //-- UserService overides
    public User findUser (final Serializable id)
    {
        return createUser (id);
    }

    public String encryptPassword (String password)
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public boolean passwordMatches (String clearPassword, String encrpytedPassword)
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public boolean hasPermission (User user, String[] permissions)
    {
        return _hasPermission;
    }

    public void init (ServiceContext context)
    {
    }

    public void destroy ()
    {
    }

    //-- Private
    private User createUser (final Serializable id)
    {
        return new User () {

            public Serializable getId ()
            {
                return id;
            }

            public String getName ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getDisplayName ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getEmail ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public String getAttribute (String name)
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }

            public Locale getLocale ()
            {
                throw new UnsupportedOperationException ("Not supported yet.");
            }
        };
    }
}
