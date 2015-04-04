/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class PersistenceServiceThreadLocal
{
    //-- Static Attributes
    private static final Logger LOG = LoggerFactory.getLogger (PersistenceServiceThreadLocal.class);
    private static ThreadLocal<Data> _ps = new ThreadLocal<Data> ();


    //-- Public method
    public static PersistenceServiceThreadLocal.Data get ()
    {
        return _ps.get ();
    }
    
    public static PersistenceService getPersistenceService ()
    {
        Data data = get ();
        return data != null ? data.service : null;
    }

    public static void init (PersistenceService ps, Controller controller)
    {
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Creating");
        }
        Data data = new Data ();
        data.service = ps;
        data.controller = controller;
        _ps.set (data);
    }
    
    public static void remove ()
    {
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Removing");
        }
        _ps.remove ();
    }
    
    //-- Static
    public static class Data
    {
        public PersistenceService service;
        public Controller controller;
    }
}
