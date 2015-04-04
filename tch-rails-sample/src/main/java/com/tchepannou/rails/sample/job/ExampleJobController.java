package com.tchepannou.rails.sample.job;

import com.tchepannou.rails.core.annotation.Job;
import com.tchepannou.rails.core.api.JobController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author herve
 */
public class ExampleJobController
    extends JobController
{
    private static final Logger LOG = LoggerFactory.getLogger(ExampleJobController.class);

    /**
     * This method is executed in background every 5 mins
     */
    @Job (cron="0 0/5 * * * ?")
    public void job1 ()
    {
        LOG.info ("Executing job1");
    }

    /**
     * This method is executed in background every 2 mins
     */
    @Job (cron="0 0/2 * * * ?")
    public void job2 ()
    {
        LOG.info ("Executing job2");
    }
}
