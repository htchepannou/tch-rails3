/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.impl;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.JobContext;

/**
 *
 * @author herve
 */
public class ContextImpl
    implements JobContext
{
    //-- Attribute
    private ContainerContext _cc;
    private boolean _rollback;
    

    //-- Public
    public ContextImpl(ContainerContext cc)
    {
        _cc = cc;
    }

    
    //-- JobContext overrides
    public ContainerContext getContainerContext ()
    {
        return _cc;
    }

    public boolean isRollback ()
    {
        return _rollback;
    }

    public void setRollback (boolean rollback)
    {
        _rollback = rollback;
    }

}
