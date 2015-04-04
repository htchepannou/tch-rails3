package com.tchepannou.rails.action;

import com.tchepannou.rails.core.api.ActionController;

public class Test1ActionController extends ActionController
{
    public void index ()
    {
        renderText ("index");
    }

    public void hello ()
    {
        addViewVariable ("name", "herve");
    }
}
