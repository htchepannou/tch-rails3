/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.action;

import com.tchepannou.rails.core.TestActiveRecord;
import com.tchepannou.rails.core.annotation.Action;
import com.tchepannou.rails.core.api.ActionController;

/**
 *
 * @author herve
 */
@Action (modelClass=TestActiveRecord.class)
public class TestActionController
    extends ActionController
{

    public String index ()
    {
        renderText ("index");
        return "index";
    }

    public String create ()
    {
        renderText ("create");
        return "create";
    }

    public String doUpdate (TestActiveRecord obj)
    {
        renderText ("doUpdate(" + obj.getId () + ")");
        return "doUpdate(" + obj.getId () + ")";
    }

    public String doDelete (TestActiveRecord obj)
    {
        renderText ("doDelete(" + obj.getId () + ")");
        return "doDelete(" + obj.getId () + ")";
    }

    public String doDelete (TestActiveRecord[] objs)
    {
        String ids = "";
        for (int i=0 ; i<objs.length ; i++)
        {
            if (i>0)
            {
                ids += ",";
            }
            ids += objs[i].getId ();
        }
        
        renderText ("doDelete({" + ids + "})");
        return "doDelete({" + ids + "})";
    }
}
