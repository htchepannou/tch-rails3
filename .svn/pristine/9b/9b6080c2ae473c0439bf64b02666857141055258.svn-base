/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.exception.JobException;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.service.JobService;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class JobControllerContainerTest 
    extends TestCase
    implements JobService
{
    private Engine _cc;
    private List<JobService.JobInfo> _jobs;

    public JobControllerContainerTest (String testName)
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
        _jobs = new ArrayList<JobInfo> ();
        
        register (JobService.class, this);
    }


    //-- Test
    public void testInit()
        throws Exception
    {
        JobControllerContainer container = new JobControllerContainer ();
        container.init (_cc);

        assertEquals("jobs", 5, _jobs.size ());
    }


    //-- Private
    private void register(Class<? extends Service> clazz, Service srv)
    {
        _cc.registerService (clazz, srv);

        ServiceContext sc = _cc.createServiceContext ();
        srv.init (sc);
    }


    //-- JobService overrides
    public void init (ServiceContext context)
    {
    }

    public void destroy ()
    {
    }

    public void schedule (JobInfo job)
        throws JobException
    {
        _jobs.add (job);
    }

}
