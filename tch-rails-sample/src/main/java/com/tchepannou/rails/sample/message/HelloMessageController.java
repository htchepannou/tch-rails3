/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.sample.message;

import com.tchepannou.rails.core.annotation.MessageSource;
import com.tchepannou.rails.core.api.MessageController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
@MessageSource (topic="hello")
public class HelloMessageController
    extends MessageController
{
    private static final Logger LOG = LoggerFactory.getLogger (HelloMessageController.class);

    public void say (String txt)
    {
        LOG.info ("say: " + txt);
    }
}
