/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.TestActiveRecord;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.cache.Cache;
import com.tchepannou.rails.core.service.OptionService;
import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.service.RenderService;
import com.tchepannou.rails.core.impl.PropertiesOptionService;
import com.tchepannou.rails.core.impl.VelocityRenderService;
import com.tchepannou.rails.core.util.IsolationLevel;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.mock.servlet.MockHttpServletRequest;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class ActionControllerContainerTest 
    extends TestCase
    implements PersistenceService
{

    private Engine _cc;
    private ActionControllerContainer _container;

    public ActionControllerContainerTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _cc = new Engine ();
        _cc.setBasePackage ("com.tchepannou.rails");
        _cc.setServletContext (new MockServletContext ());
        
        register (RenderService.class, new VelocityRenderService ());
        register (OptionService.class, new PropertiesOptionService ());
        register (PersistenceService.class, this);

        _container = new ActionControllerContainer ();
    }

    private void register(Class<? extends Service> clazz, Service srv)
    {
        _cc.registerService (clazz, srv);

        ServiceContext sc = _cc.createServiceContext ();
        srv.init (sc);
    }


    //-- Test cases
    public void testInit ()
    {
        _container.init (_cc);
        assertTrue ("initialized", _container.isInitialized ());
        assertNotNull ("/test1/hello", _container.getActionControllerWrapper ("test1/wrapper"));
        assertNotNull ("/test2/hello", _container.getActionControllerWrapper ("test2/wrapper"));
        assertNull ("/xxx", _container.getActionControllerWrapper ("/xx"));
    }

    public void testDestroy ()
    {
        _container.init (_cc);
        _container.destroy ();
        assertFalse ("initialized", _container.isInitialized ());
        assertNull ("/test1/hello", _container.getActionControllerWrapper ("test1/wrapper"));
        assertNull ("/test2/hello", _container.getActionControllerWrapper ("test2/wrapper"));
    }


    //-- Test rendring
    public void testHello ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/test1/hello");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "<b>hello herve</b>", resp.getContentAsString ());
    }

    public void testHelloTXT ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/test1/hello.txt");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "hello herve", resp.getContentAsString ());
    }

    public void testIndex ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/test1");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "index", resp.getContentAsString ());
    }

    public void testHome ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "home", resp.getContentAsString ());
    }

    public void testWrongAction ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/test1/xxx");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 404, resp.getStatus ());
    }

    public void testDoUpdate ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setParameter ("id", "1");
        req.setPathInfo ("/test/doUpdate");

        MockHttpServletResponse resp = new MockHttpServletResponse ();
        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "doUpdate(1)", resp.getContentAsString ());
    }

    public void testDoDelete ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setParameterValues ("id", new String[] {"1", "2"});
        req.setPathInfo ("/test/doDelete");

        MockHttpServletResponse resp = new MockHttpServletResponse ();
        _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertEquals ("response content", "doDelete({1,2})", resp.getContentAsString ());
    }


    public void testWrongController ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/xxx");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);

        assertEquals ("HTTP status", 404, resp.getStatus ());
    }

    public void testFlash ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/flash/index");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        ActionController ctl = _container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());
        assertTrue ("response content", resp.getContentAsString ().contains ("notice"));
        assertNull ("flash is not reset", ctl.getSession (ActionController.SESSION_FLASH));
    }

    public void testFlashRedirect ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/flash/redirect");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        ActionController ctl = _container.service (req, resp);

        //assertEquals ("HTTP status", 302, resp.getStatus ());
        assertNotNull ("flash is reset", ctl.getSession (ActionController.SESSION_FLASH));
    }


    //-- Expiration test case
    public void testExpiry ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/expire/index");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        ActionController ctl = _container.service (req, resp);
        assertTrue (ctl.getExpirySeconds () > 0);
    }

    public void testIfModifiedSince ()
        throws Exception
    {
        _container.init (_cc);

        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.addDateHeader ("if-modified-since", System.currentTimeMillis ()-100);
        req.setPathInfo ("/ifMofified/index");

        MockHttpServletResponse resp = new MockHttpServletResponse ();

        _container.service (req, resp);
        assertEquals(304, resp.getStatus ());
    }




    //-- PersistenceService override
    public ActiveRecord get (Serializable id, Class<? extends ActiveRecord> type)
    {
        if (id instanceof Long && ((Long)id).longValue () > 0)
        {
            TestActiveRecord r = new TestActiveRecord ();
            r.setId ((Long)id);
            return r;
        }
        else
        {
            return null;
        }
    }

    public void get (Object o)
    {
    }

    public void init (ServiceContext context)
    {
    }

    public void destroy ()
    {
    }

    public void beginTransaction (IsolationLevel level)
    {
    }

    public void commitTransaction ()
    {
    }

    public void rollbackTransaction ()
    {
    }

    public void save (Object o)
    {
    }

    public void delete (Object o)
    {
    }

    public Map describe (Object o)
    {
        return new HashMap ();
    }

    public void populate (Object o, Map properties)
    {
    }

    public void closeConnection ()
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    public Cache getCache ()
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }
}
