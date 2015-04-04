/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.cache.Cache;
import com.tchepannou.rails.core.util.IsolationLevel;
import java.io.Serializable;
import java.util.Map;

/**
 * This service is used for managing data persistence
 * 
 * @author herve
 */
public interface PersistenceService
    extends Service
{
    public ActiveRecord get (Serializable id, Class<? extends ActiveRecord> type);

    public void beginTransaction (IsolationLevel level);

    public void commitTransaction ();

    public void rollbackTransaction ();
    
    public void closeConnection ();

    public void get (Object o);
    
    public void save (Object o);

    public void delete (Object o);

    public Map describe (Object o);

    public void populate (Object o, Map properties);
    
    public Cache getCache ();
}
