/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.service.JobService.JobInfo;
import com.tchepannou.rails.engine.container.JobControllerWrapper;

/**
 *
 * @author herve
 */
public class JobControllerTestCase 
    extends ControllerTestCase
{
    public JobControllerTestCase()
    {

    }
    public JobControllerTestCase(String name)
    {
        super (name);
    }


    public void execute (String path)
    {
        JobInfo job = getEngine ().getJobContainer ().getJob (path);
        if (job != null)
        {
            ((JobControllerWrapper)job.getExecutor ()).execute (job);
        }
    }
}
