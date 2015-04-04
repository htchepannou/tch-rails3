/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.quartz;

import com.tchepannou.rails.core.service.JobService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author herve
 */
public class QuartzJob
    implements Job
{
    //-- Static attributes
    public static final String JOB_KEY = "com.tchepannou.rails.quartz.info_key";
    
    //-- Job overrides
    public void execute (JobExecutionContext jec)
        throws JobExecutionException
    {
        JobService.JobInfo job =  (JobService.JobInfo)jec.getJobDetail ().getJobDataMap ().get (JOB_KEY);
        if (job != null)
        {
            job.getExecutor ().execute (job);
        }
    }

}
