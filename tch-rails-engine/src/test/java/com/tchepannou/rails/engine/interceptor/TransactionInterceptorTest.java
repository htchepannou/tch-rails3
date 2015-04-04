/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.TestActiveRecord;
import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.cache.Cache;
import com.tchepannou.rails.core.service.JMSService;
import com.tchepannou.rails.core.service.JobService;
import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.util.IsolationLevel;
import com.tchepannou.rails.core.util.JMSUtil;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.container.ActionControllerContainer;
import com.tchepannou.rails.engine.container.JobControllerContainer;
import com.tchepannou.rails.engine.container.MessageControllerContainer;
import com.tchepannou.rails.mock.jms.MockConnectionFactory;
import com.tchepannou.rails.mock.servlet.MockHttpServletRequest;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class TransactionInterceptorTest
    extends TestCase
    implements PersistenceService, JMSService
{
    private Engine _cc;
    private boolean _txBegin;
    private boolean _txCommit;
    private boolean _txRollback;

    public TransactionInterceptorTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _cc = new Engine ();
        _cc.setBasePackage ("com.tchepannou.rails");
        _cc.setServletContext (new MockServletContext ());

        Interceptor itc= new TransactionInterceptor ();
        _cc.registerActionInterceptor (itc);
        _cc.registerJobInterceptor (itc);
        _cc.registerMessageInterceptor (itc);
        _cc.registerService (PersistenceService.class, this);
        _cc.registerService (JMSService.class, this);

        _txBegin = _txCommit = _txRollback = false;

    }


    //-- ActionTestCases
    public void testActionTransactionCommit()
        throws Exception
    {
        ActionControllerContainer container  = new ActionControllerContainer ();
        container.init (_cc);
        
        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/transaction/commit");

        MockHttpServletResponse resp = new MockHttpServletResponse ();
        container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());

        assertTrue("beginTransaction", _txBegin);
        assertTrue("commitTransaction", _txCommit);
        assertFalse("rollbackTransaction", _txRollback);
    }
    public void testActionTransactionRollbackException()
        throws Exception
    {
        ActionControllerContainer container  = new ActionControllerContainer ();
        container.init (_cc);


        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/transaction/rollbackException");

        MockHttpServletResponse resp = new MockHttpServletResponse ();
        container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());

        assertTrue("beginTransaction", _txBegin);
        assertFalse("commitTransaction", _txCommit);
        assertTrue("rollbackTransaction", _txRollback);
    }
    public void testActionTransactionRollbackError()
        throws Exception
    {
        ActionControllerContainer container  = new ActionControllerContainer ();
        container.init (_cc);


        MockHttpServletRequest req = new MockHttpServletRequest (_cc.getServletContext ());
        req.setPathInfo ("/transaction/rollbackError");

        MockHttpServletResponse resp = new MockHttpServletResponse ();
        container.service (req, resp);

        assertEquals ("HTTP status", 200, resp.getStatus ());

        assertTrue("beginTransaction", _txBegin);
        assertFalse("commitTransaction", _txCommit);
        assertTrue("rollbackTransaction", _txRollback);
    }


    //-- JobTestCases
    public void testJobTransactionCommit()
    {
        JobControllerContainer container = new JobControllerContainer ();
        container.init (_cc);
        
        JobService.JobInfo info = getJob("jobCommit", container);
        info.getExecutor ().execute (info);
        assertTrue("beginTransaction", _txBegin);
        assertTrue("commitTransaction", _txCommit);
        assertFalse("rollbackTransaction", _txRollback);
    }
    public void testJobTransactionRollback()
    {
        JobControllerContainer container = new JobControllerContainer ();
        container.init (_cc);

        JobService.JobInfo info = getJob("jobRollback", container);
        info.getExecutor ().execute (info);
        assertTrue("beginTransaction", _txBegin);
        assertFalse("commitTransaction", _txCommit);
        assertTrue("rollbackTransaction", _txRollback);
    }

    //-- MessageTestCases
    public void testMessageTransactionCommit()
        throws Exception
    {
        MessageControllerContainer container = new MessageControllerContainer ();
        container.init (_cc);

        container.sendMessage ("/transaction/commit", null);
        assertTrue("beginTransaction", _txBegin);
        assertTrue("commitTransaction", _txCommit);
        assertFalse("rollbackTransaction", _txRollback);
    }
    public void testMessageTransactionRollback()
        throws Exception
    {
        MessageControllerContainer container = new MessageControllerContainer ();
        container.init (_cc);

        container.sendMessage ("/transaction/rollback", null);
        assertTrue("beginTransaction", _txBegin);
        assertFalse("commitTransaction", _txCommit);
        assertTrue("rollbackTransaction", _txRollback);
    }

    private JobService.JobInfo getJob (String name, JobControllerContainer container)
    {
        for (JobService.JobInfo info : container.getJobs ())
        {
            if (info.getMethod ().getName ().endsWith (name))
            {
                return info;
            }
        }
        return null;
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
        _txBegin = true;
    }

    public void commitTransaction ()
    {
        _txCommit = true;
    }

    public void rollbackTransaction ()
    {
        _txRollback = true;
    }

    public void closeConnection ()
    {
        throw new UnsupportedOperationException ("Not supported yet.");
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


    //-- JMSService override
    public ConnectionFactory getConnectionFactory ()
    {
        return new MockConnectionFactory ();
    }

    public void sendMessage (String topic, Serializable data)
        throws JMSException
    {
        ConnectionFactory cf = getConnectionFactory ();
        JMSUtil.sendMessage (topic, data, cf);
    }

    public Cache getCache ()
    {
        throw new UnsupportedOperationException ("Not supported yet.");
    }
}
