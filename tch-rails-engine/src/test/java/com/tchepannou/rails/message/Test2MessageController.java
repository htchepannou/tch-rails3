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
@MessageSource (topic="test2")
public class Test2MessageController
    extends MessageController
{
    public void handler21(String msg)
    {
        System.out.println ("handler21(" + msg + ")");
    }
}
