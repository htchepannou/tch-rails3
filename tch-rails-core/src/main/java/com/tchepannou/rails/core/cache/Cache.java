/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.cache;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Interface for caching
 * 
 * @author herve
 */
public interface Cache
{
    public void init (Properties props);
    
    public Object get (Object key);
    
    public void put (Object key, Object o);
    
    public Object remove (Object key);

    
    public Map mget (List keys);
    
    public void mput (Map data);
    
    public Map mremove (List keys);
}
