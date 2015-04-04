/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.action;

import com.tchepannou.rails.core.annotation.RequireUser;
import com.tchepannou.rails.core.api.ActionController;

/**
 *
 * @author herve
 */
public class SecureActionController
    extends ActionController
{
    public void nonSecure ()
    {
        renderText("nonSecure");
    }

    @RequireUser
    public void requireUser ()
    {
        renderText("requireUser");
    }

    @RequireUser (permissions={"p1", "P2"})
    public void requirePermission ()
    {
        renderText("requirePermission");
    }
}
