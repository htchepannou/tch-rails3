/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.cache;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Implementation of {@link Cache} that doesn't cache any object.
 * @author herve
 */
public class NullCache
    implements Cache
{
    //-- Cache overrides
    public void init (Properties props)
    {
    }

    public Object get (Object key)
    {
        return null;
    }

    public void put (Object key, Object o)
    {
    }

    public Object remove (Object key)
    {
        return null;
    }

    public Map mget (List keys)
    {
        return Collections.EMPTY_MAP;
    }

    public void mput (Map data)
    {
    }

    public Map mremove (List keys)
    {
        return Collections.EMPTY_MAP;
    }

}
