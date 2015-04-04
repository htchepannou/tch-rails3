package com.tchepannou.rails.action;

import com.tchepannou.rails.core.api.ActionController;

public class FlashActionController
    extends ActionController
{
    public void index ()
    {
        addNotice ("notice");
    }

    public void redirect ()
    {
        addNotice ("notice");
        redirectTo ("/flash/redirected");
    }
}
