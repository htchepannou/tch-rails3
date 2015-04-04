/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.quartz;

import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.exception.JobException;
import com.tchepannou.rails.core.service.JobService;
import java.text.ParseException;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class QuartzService
    implements JobService
{
    //-- Static
    private static final Logger LOG = LoggerFactory.getLogger (QuartzService.class);

    //-- Attribute
    private Scheduler _scheduler;

    //-- JobService overirde
    public void schedule (JobInfo job)
        throws JobException
    {
        try
        {
            String gid = job.getControllerClass ().getName ();
            String jid = job.getMethod ().getName ();
            String cron = job.getCronExpression ();

            JobDetail xjob = JobBuilder.newJob (QuartzJob.class)
                .withIdentity (jid, gid)
                .build ();
            JobDataMap map = xjob.getJobDataMap ();
            map.put (QuartzJob.JOB_KEY, job);

            Trigger trigger = (Trigger)TriggerBuilder.newTrigger ()
                .withIdentity (jid, gid)
                .withSchedule (CronScheduleBuilder.cronSchedule (cron))
                .build();

            if ( LOG.isDebugEnabled () )
            {
                LOG.debug ("Scheduling " + job.getMethod ().getClass ().getName () + "." + job.getMethod ().getName () + " / " + job.getCronExpression ());
            }
            _scheduler.scheduleJob (xjob, trigger);
        }
        catch (ParseException e)
        {
            throw new JobException ("Scheduler error", e);
        }
        catch (SchedulerException e)
        {
            throw new JobException ("Scheduler error", e);
        }
    }

    public void init (ServiceContext context)
    {
        LOG.info ("Initializing");
        try
        {
            _scheduler = StdSchedulerFactory.getDefaultScheduler();
            _scheduler.start ();

            LOG.info ("Initialized");
        }
        catch (SchedulerException e)
        {
            throw new InitializationException ("Unable to initialize Quartz Scheduler", e);
        }
    }

    public void destroy ()
    {
        LOG.info ("Destroying");
        try
        {
            _scheduler.shutdown ();
        }
        catch (SchedulerException e)
        {
            LOG.warn ("Unexpected error while shutting down Quartz scheduler", e);
        }
        finally
        {
            LOG.info ("Destroyed");
        }
    }

}
