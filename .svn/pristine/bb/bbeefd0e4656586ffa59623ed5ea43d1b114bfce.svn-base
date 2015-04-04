/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

/**
 * <code>JobController</code> contains jobs (methods) that are executed periodically
 * in background. Job methods are defined by the annotation {@link com.tchepannou.rails.core.annotation.Job}, with a cron-expression that
 * defines their schedule of execution.
 *
 * @author herve
 */
public class JobController
    extends AbstractController
{
    //-- Static Attributes
    public static final String CLASSNAME_SUFFIX = "JobController";

    //-- Attributes
    private JobContext _jobContext;

    //-- Controller override
    public Context getContext ()
    {
        return getJobContext ();
    }


    //-- Public methods
    public void setJobContext (JobContext context)
    {
        _jobContext =  context;
    }

    public JobContext getJobContext ()
    {
        return _jobContext;
    }

    public I18n getI18n ()
    {
        return I18nThreadLocal.get ();
    }
}
