/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.impl;

import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.MessageContext;

/**
 *
 * @author herve
 */
public class MessageContextImpl
    implements MessageContext
{
    //-- Attribute
    private ContainerContext _cc;
    private boolean _rollback;
    

    //-- Public
    public MessageContextImpl(ContainerContext cc)
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
