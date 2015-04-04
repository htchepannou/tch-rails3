/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.message;

import com.tchepannou.rails.core.annotation.RequireTransaction;
import com.tchepannou.rails.core.api.MessageController;

/**
 *
 * @author herve
 */
public class TransactionMessageController
    extends MessageController
{
    @RequireTransaction
    public void commit()
    {

    }

    @RequireTransaction
    public void rollback()
    {
        throw new IllegalStateException ();
    }
}
