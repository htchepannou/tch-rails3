package com.tchepannou.rails.action;

import com.tchepannou.rails.core.api.ActionController;

public class HomeActionController extends ActionController
{
    public void index ()
    {
        renderText ("home");
    }
}
