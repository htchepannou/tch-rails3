/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.api.User;
import java.io.Serializable;


/**
 * This is the service that returns user's information
 * 
 * @author herve
 */
public interface UserService 
    extends Service
{
    /**
     * Returns a user by its ID or <code>null</code> if not found
     *
     * @param id ID of the user
     */
    public User findUser (Serializable id);

    /**
     * Encrypt a password and returnes it
     */
    public String encryptPassword (String password);

    /**
     * Test if a clear password matches an encripted password
     */
    public boolean passwordMatches (String clearPassword, String encrpytedPassword);

    /**
     * Return <code>true</code> if a user has any of the permission
     */
    public boolean hasPermission (User user, String[] permissions);

}
