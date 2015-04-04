/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.job;

import com.tchepannou.rails.core.annotation.Job;
import com.tchepannou.rails.core.api.JobController;

/**
 *
 * @author herve
 */
public class Test1JobController
    extends JobController
{
    @Job (cron="0 0 1 * * ?")
    public void job11()
    {

    }

    @Job (cron="0 0 1 * * ?")
    public void job12()
    {

    }
}
