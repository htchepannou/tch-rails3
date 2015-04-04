/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.MessageController;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.service.JMSService;
import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.service.PersistenceServiceThreadLocal;
import com.tchepannou.rails.core.util.IsolationLevel;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.EngineConfigurator;
import com.tchepannou.rails.engine.container.MessageControllerWrapper;
import com.tchepannou.rails.engine.impl.ServiceContextImpl;
import com.tchepannou.rails.junit.mock.MockJMSService;
import com.tchepannou.rails.mock.jms.MockJMSServer;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class BaseTestCase
    extends TestCase
{
    //-- Static Attribute
    private static long _uid = System.currentTimeMillis ();

    //-- Attribute
    private MockServletContext _servletContext;
    private Engine _engine;



    //-- Constructor
    public BaseTestCase ()
    {
    }

    public BaseTestCase (String name)
    {
        super (name);
    }

    //-- TestCase override
    @Override
    protected void setUp ()
        throws Exception
    {
        System.out.println ("--- " + getClass().getSimpleName() + "." + getName () + "--------------------------------");

        /* Important: Start JMS Server first */
        MockJMSServer.start ();

        _servletContext = new MockServletContext ();

        _engine = new Engine ();
        new EngineConfigurator ().configure (_engine, _servletContext);
        _engine.registerService (JMSService.class, new MockJMSService ());
        _engine.init (_servletContext);

        /* Set persistence service */
        PersistenceService ps = (PersistenceService)findService (PersistenceService.class);
        if (ps != null)
        {
            PersistenceServiceThreadLocal.init (ps, null);
        }
    }

    @Override
    protected void tearDown ()
        throws Exception
    {
        System.out.println ("");
        
        _engine.destroy ();
        MockJMSServer.stop ();
    }

    //-- Protected
    protected synchronized long nextUID ()
    {
        return ++_uid;
    }

    //-- Protected
    protected void register (Class<? extends MessageController> controller)
    {
        MessageControllerWrapper wrapper = _engine.getMessageContainer ().register (controller);
        wrapper.init (_engine);
    }

    protected void register (Class<? extends Service> spec, Service service)
    {
        _engine.registerService (spec, service);
        service.init (new ServiceContextImpl (_engine));

        if (PersistenceService.class.equals(spec))
        {
            PersistenceServiceThreadLocal.init ((PersistenceService)service, null);
        }
    }

    protected Service findService (Class<? extends Service> spec)
    {
        return _engine.findService (spec);
    }

    protected MockServletContext getServletContext ()
    {
        return _servletContext;
    }

    protected Engine getEngine ()
    {
        return _engine;
    }

    protected void persist(ActiveRecord o)
    {
        PersistenceService ps = (PersistenceService)findService (PersistenceService.class);
        if (ps != null)
        {
            ps.beginTransaction (IsolationLevel.UNDEF);
        }
        if (o.save (true))
        {
            if (ps != null)
            {
                ps.commitTransaction ();
            }
        }
        else
        {
            if (ps != null)
            {
                ps.rollbackTransaction ();
            }
        }
    }
}
