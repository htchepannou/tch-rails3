/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.JobContext;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.service.JobService;
import com.tchepannou.rails.core.util.MethodInvoker;
import com.tchepannou.rails.engine.impl.JobContextImpl;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class JobControllerWrapper
    extends AbstractControllerWrapper
    implements JobService.JobExecutor
{
    //-- Static Method
    private static final Logger LOG = LoggerFactory.getLogger (JobControllerWrapper.class);
    
    //-- JobService.JobExecutor overrides
    public void execute (JobService.JobInfo job)
    {
        JobController controller = createJobController (job);

        /* Pre-interceptor */
        List<Interceptor> interceptors = job.getContainerContext ().getJobInterceptors ();
        int status = before (controller, interceptors);

        /* Service */
        try
        {
            if ( status != Interceptor.STOP )
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug ("Executing " + job.getMethod ());
                }
                MethodInvoker invoker = new MethodInvoker (job.getMethod ().getName (), controller);
                invoker.invoke (interceptors);
            }
        }
        finally
        {
            /* Post-interceptor */
            after (controller, interceptors);
        }
    }

    private JobController createJobController (JobService.JobInfo job)
    {
        Class<? extends JobController> clazz = job.getControllerClass ();
        try
        {
            JobController controller = clazz.newInstance ();
            JobContext context = new JobContextImpl (job.getContainerContext ());
            controller.setJobContext (context);
            return controller;
        }
        catch ( InstantiationException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + clazz, ex);
        }
        catch ( IllegalAccessException ex )
        {
            throw new com.tchepannou.rails.core.exception.InstantiationException ("Unable to instantiate " + clazz, ex);
        }
    }
}
