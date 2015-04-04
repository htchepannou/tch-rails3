/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.impl;

import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.service.UserService;
import com.tchepannou.util.StringUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Implementation of {@link UserService} that loads the options from the
 * file <code>/WEB-INF/rails-user.properties</code>
 *
 * @author herve
 */
public class PropertiesUserService
    extends AbstractService
    implements UserService
{
    //-- Static Attribute
    private static final Logger LOG = LoggerFactory.getLogger (PropertiesUserService.class);

    //-- Attributes
    private Properties _properties = new Properties ();

    //-- UserService override
    public User findUser (Serializable id)
    {
        String name = _properties.getProperty ("user." + id + ".name");
        if (name != null)
        {
            return new UserImpl (id);
        }
        else
        {
            return null;
        }
    }

    //-- Service overrides
    @Override
    public void init (ServiceContext context)
    {
        LOG.info ("Initializint");
        super.init (context);

        try
        {
            _properties = loadConfigurationAsProperties ("rails-user.properties");
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("User Properties");
                for (Object name : _properties.keySet ())
                {
                    LOG.debug (" " + name + "=" + _properties.get (name));
                }
            }
            LOG.info ("Initialized");
        }
        catch (FileNotFoundException e)
        {
            LOG.warn ("User configuration not available");
        }
        catch (IOException e)
        {
            throw new InitializationException ("Initialization error", e);
        }
    }

    @Override
    public void destroy ()
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("destroy()");
        }
        super.destroy ();

        _properties.clear ();
    }

    public String encryptPassword (String password)
    {
        return password;
    }

    public boolean passwordMatches (String clearPassword, String encrpytedPassword)
    {
        return clearPassword != null
            ? clearPassword.equals (encrpytedPassword)
            : encrpytedPassword == null;
    }

    public boolean hasPermission (User user, String[] permissions)
    {
        String props = getProperty (user, "permissions");
        if (StringUtil.isEmpty (props))
        {
            return false;
        }
        else
        {
            String[] array = props.split (",");
            int count = 0;
            for (String prop : array)
            {
                for (String perm : permissions)
                {
                    if (perm.equalsIgnoreCase (prop))
                    {
                        count++;
                    }
                }
            }
            return count == permissions.length;
        }
    }


    //-- Private
    private String getProperty (User user, String name)
    {
        return _properties.getProperty ("user." + user.getId () + "." + name);
    }

    //-- Implementation Method
    /**
     * Implementation of {@link User}
     */
    private class UserImpl
        implements User
    {
        //-- Attribute
        private Serializable _id;

        //-- Constructor
        public UserImpl (Serializable id)
        {
            _id = id;
        }

        //-- User overrides
        public Serializable getId ()
        {
            return _id;
        }

        public String getName ()
        {
            return getAttribute ("name");
        }

        public String getDisplayName ()
        {
            return getAttribute ("displayName");
        }

        public String getEmail ()
        {
            return getAttribute ("email");
        }

        public String getAttribute (String name)
        {
            return getProperty (this, name);
        }

        public Locale getLocale ()
        {
            String locale = getAttribute ("locale");
            for (Locale loc : Locale.getAvailableLocales ())
            {
                if (loc.toString ().equals(locale))
                {
                    return loc;
                }
            }
            return null;
        }
    }
}
