/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.siena;

import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.cache.Cache;
import com.tchepannou.rails.core.cache.MapCache;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of 1st level cache.
 * 
 * @author herve
 */
public class L1Cache
    extends MapCache
{
    //-- Attribute
    private static final Logger LOG = LoggerFactory.getLogger (L1Cache.class);
    private Cache _delegate;
    
    
    //-- Public
    public L1Cache (Cache delegate)
    {
        _delegate = delegate;
    }
    
    
    //-- Cache overrides
    @Override
    public void init (Properties props)
    {
    }

    @Override
    public Object get (Object key)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("get(" + key + ")");
        }
        
        /* get from 1st level cache */
        Object obj = super.get (key);
        
        /* get from 2nd level cache */
        if (obj == null)
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug (key + " not found in 1st level cache - searching in 2nd level cache");
            }
            obj = _delegate.get (key);
            
            /* store in 1st level cache */
            if (obj != null)
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug (key + "  found in 2md level cache - " + key + "=" + toString (obj));
                }
                super.put (key, obj);
            }
        }
        else
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug (key + " found in 1st level cache - " + key + "=" + toString (obj));
            }            
        }
        return obj;
    }

    @Override
    public Map mget (List keys)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("mget(" + keys + ")");
        }
        
        /* get from 1st level cache */
        Map map = super.mget (keys);
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Found in 1st level cache: " + map.keySet ());
        }
        
        /* getfrom 2nd level cache */
        List mkeys = new ArrayList ();
        for (Object key : keys)
        {
            if (!map.containsKey (key))
            {
                mkeys.add (key);
            }
        }
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Not found in 1st level cache: " + mkeys);
        }
        Map mmap = _delegate.mget (mkeys);
        
        /* store missing in 1st level cache */
        super.mput (mmap);        
        
        /* merge */
        map.putAll (mmap);
        return map;
    }

    @Override
    public void mput (Map data)
    {
        super.mput (data);
        _delegate.mput (data);
    }

    @Override
    public Map mremove (List keys)
    {
        super.mremove (keys);
        return _delegate.mremove (keys);
    }

    @Override
    public void put (Object key, Object o)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("put(" + key + "," + o + ")");
        }
        
        super.put (key, o);
        _delegate.put (key, o);
    }

    @Override
    public Object remove (Object key)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("remove(" + key + ")");
        }
        
        super.remove (key);
        return _delegate.remove (key);
    }
    
    private String toString (Object obj)
    {
        if (obj instanceof ActiveRecord)
        {
            return obj + " " + ((ActiveRecord)obj).describe ();
        }
        else if (obj != null)
        {
            return obj.toString ();
        }
        else
        {
            return null;
        }
    }
}
