/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.action;

import com.tchepannou.rails.core.annotation.RequireTransaction;
import com.tchepannou.rails.core.api.ActionController;

/**
 *
 * @author herve
 */
public class TransactionActionController
    extends ActionController
{
    @RequireTransaction
    public void commit()
    {
        renderText ("");
    }

    @RequireTransaction
    public void rollbackException ()
    {
        renderText ("");
        throw new IllegalStateException ();
    }

    @RequireTransaction
    public void rollbackError ()
    {
        renderText ("");
        addError ("err");
    }
}
