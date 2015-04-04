/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.sample.action;

import com.tchepannou.rails.core.annotation.Template;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.core.service.UserService;
import com.tchepannou.util.StringUtil;

/**
 *
 * @author herve
 */
@Template (name="default")
public class LoginActionController
    extends ActionController
{
    public void index ()
    {
        addViewVariable ("redirect", getRequestParameter (PARAM_REDIRECT));
    }
    
    public void doLogin()
    {
        String name = getRequestParameter ("name");
        UserService us = (UserService)findService (UserService.class);
        User user = findUserByName (name, us);
        if (user == null)
        {
            addError ("No user found with name <b>" + name + "</b>");
        }
        else
        {
            String password = getRequestParameter ("password");
            String xpassword = us.encryptPassword (password);
            if (!xpassword.equals (user.getAttribute ("password")))
            {
                addError ("Invalid password");;
            }
            else
            {
                setUser (user);
            }
        }

        if (hasErrors ())
        {
            restoreView ("/login");
        }
        else
        {
            String redirect = getRequestParameter ("redirect");
            if (!StringUtil.isEmpty (redirect))
            {
                redirectTo (redirect);
            }
            else
            {
                redirectTo ("/home");
            }
        }
    }


    private User findUserByName (String name, UserService us)
    {
        /* BAD algorithm */
        User user = null;
        for (int i=1 ; i<10 ; i++)
        {
            User u = us.findUser (i);
            if (u == null)
            {
                return null;
            }
            else if (name.equalsIgnoreCase (u.getName ()))
            {
                user = u;
                break;
            }
        }
        return user;
    }
}
