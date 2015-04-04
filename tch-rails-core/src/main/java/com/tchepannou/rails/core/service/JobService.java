/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobController;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.exception.JobException;
import java.lang.reflect.Method;

/**
 * This is a service for executing jobs in background
 * 
 * @author herve
 */
public interface JobService
    extends Service
{
    //-- Public method
    public void schedule (JobInfo job)
        throws JobException;

    //-- Inner classes
    /**
     * Job informations
     */
    public static interface JobInfo
    {
        public String getCronExpression ();

        public Class<? extends JobController> getControllerClass ();

        public Method getMethod ();

        public ContainerContext getContainerContext ();

        public JobExecutor getExecutor ();
    }

    //-- Inner class
    /**
     * Execute jobs
     */
    public static interface JobExecutor
    {
        public void execute (JobInfo job);
    }
}
