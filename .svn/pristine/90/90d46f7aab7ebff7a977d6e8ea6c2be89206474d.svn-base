/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.message;

import com.tchepannou.rails.core.annotation.MessageSource;
import com.tchepannou.rails.core.api.MessageController;

/**
 *
 * @author herve
 */
@MessageSource (topic="test1")
public class Test1MessageController
    extends MessageController
{
    public void handler11(String msg)
    {
        System.out.println ("handler11(" + msg + ")");
    }

    public void handler12(String msg)
    {
        System.out.println ("handler21(" + msg + ")");
    }
}
