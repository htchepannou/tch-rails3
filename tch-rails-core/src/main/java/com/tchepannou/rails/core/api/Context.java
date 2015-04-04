/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

/**
 * Base interface of controller contexts
 *
 * @author herve
 */
public interface Context
{
    public ContainerContext getContainerContext ();

    /**
     * Call this method to rollback the current transaction
     */
    public void setRollback (boolean rollback);

    /**
     * Returns <code>true</code> if the current transaction should be rolled-back
     * @return
     */
    public boolean isRollback ();

}
