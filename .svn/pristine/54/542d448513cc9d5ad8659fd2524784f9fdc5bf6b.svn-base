/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.job;

import com.tchepannou.rails.core.annotation.Job;
import com.tchepannou.rails.core.annotation.RequireTransaction;
import com.tchepannou.rails.core.api.JobController;

/**
 *
 * @author herve
 */
public class TransactionJobController
    extends JobController
{
    @Job (cron="0 0 1 * * ?")
    @RequireTransaction
    public void jobCommit()
    {

    }

    @Job (cron="0 0 1 * * ?")
    @RequireTransaction
    public void jobRollback()
    {
        throw new IllegalStateException ();
    }
}
