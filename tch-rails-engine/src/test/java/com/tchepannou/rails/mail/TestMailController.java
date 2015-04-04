/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mail;

import com.tchepannou.rails.core.annotation.Template;
import com.tchepannou.rails.core.api.MailController;

/**
 *
 * @author herve
 */
public class TestMailController
    extends MailController
{
    @Template (name="email")
    public void hello (String name)
    {
        setFrom ("ray.sponsible@google.com");
        addTo ("herve.tchepannou@gmail.com");

        addViewVariable ("name", name);
    }
}
