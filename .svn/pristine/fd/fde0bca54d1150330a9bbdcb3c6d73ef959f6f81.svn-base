/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.siena;

import com.tchepannou.rails.core.cache.Cache;
import com.tchepannou.rails.core.cache.NullCache;
import com.tchepannou.util.StringUtil;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import siena.Table;
import siena.Util;
import siena.jdbc.JdbcPersistenceManager;

/**
 *
 * @author herve
 */
public class CacheJdbcPersistenceManager
    extends JdbcPersistenceManager
{
    //-- Attribute
    private static final Logger LOG = LoggerFactory.getLogger (CacheJdbcPersistenceManager.class);
    private Cache __cache = new NullCache (); 
    private BeanMapper _mapper = new BeanMapper ();
    
    /** Level1 cache */
    private ThreadLocal<L1Cache> _l1Cache =  new ThreadLocal<L1Cache> ();
    
    
    //-- Public
    /**
     * Returns the cache instance
     */
    public L1Cache getCache ()
    {
        L1Cache cache = _l1Cache.get ();
        if (cache == null)
        {
            cache = new L1Cache (__cache);
            _l1Cache.set (cache);
        }
        return cache;
    }
    
    
    //-- JdbcPersistenceManager overrides
    @Override
    public void init (Properties p)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("init(" + p + ")");
        }
        
        LOG.info ("Initializing " + p);
        
        super.init (p);
        
        String c = p.getProperty ("cache");
        if (c != null)
        {
            LOG.info ("Initializing 2nd level cache: " + c);
            try
            {
                __cache = (Cache)Class.forName (c).newInstance ();
                __cache.init (p);
            }
            catch (InstantiationException e)
            {
                LOG.warn ("Unable to initialize Cache. Caching will be disabled", e);
            }
            catch (ClassNotFoundException e)
            {
                LOG.warn ("Unable to initialize Cache. Caching will be disabled", e);
            }
            catch (IllegalAccessException e)
            {
                LOG.warn ("Unable to initialize Cache. Caching will be disabled", e);
            }
        }
    }

    @Override
    public void closeConnection ()
    {
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Closing connection");
        }
        super.closeConnection ();
        
        /* get rid of 1st level cache */
        if (LOG.isDebugEnabled ())
        {
            LOG.debug ("Flushing 1st level cach");
        }
        _l1Cache.remove ();
    }
    
    

    
    
    @Override
    public void delete (Object obj)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("delete(" + obj + ")");
        }
        
        super.delete (obj);        
        removeFromCache (obj);
    }

    @Override
    public int delete (Iterable<?> objects)
    {
        int result = super.delete (objects);
        removeFromCache (objects);
        return result;
    }

    @Override
    public <T> int deleteByKeys (Class<T> clazz, Iterable<?> keys)
    {
        List objects = getByKeys (clazz, keys);
        removeFromCache (objects);
        return super.deleteByKeys (clazz, keys);
    }
    
    

   
    @Override
    public void get (Object obj)
    {        
        Object key = getKey (obj);
        L1Cache cache = getCache ();
        Object xobj = cache.get (key);
        if (xobj == null)
        {
            super.get (obj);
            cache.put (key, obj);
        }
        else
        {
            try
            {
                _mapper.copyProperties (obj, xobj);
            }
            catch ( IllegalAccessException ex )
            {
                throw new IllegalStateException ("Unable to initialize " + obj, ex);
            }
            catch ( InvocationTargetException ex )
            {
                throw new IllegalStateException ("Unable to initialize " + obj, ex);
            }
        }
    }

    @Override
    public void update (Object obj)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("update(" + obj + ")");
        }
        
        super.update (obj);
        removeFromCache (obj);
    }

    @Override
    public <T> int update (Iterable<T> objects)
    {
        int result = super.update (objects);
        removeFromCache (objects);
        return result;
    }
    

    @Override
    public void save (Object obj)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("save(" + obj + ")");
        }
        
        super.save (obj);
        removeFromCache (obj);
    }

    @Override
    public int save (Iterable<?> objects)
    {
        int result = super.save (objects);
        removeFromCache (objects);
        return result;
    }
    
    

    @Override
    public <T> T getByKey (Class<T> clazz, Object key)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace("getByKey(" + clazz + "," + key + ")");
        }
        
        /* get from cache */
        Cache cache = getCache ();
        Object xkey = getKey (clazz, key);
        Object obj = cache.get (xkey);
        if (obj != null)
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("Found in the cache " + xkey + "=" + obj);
            }
            return (T)obj;
        }

        /* get from db */
        if (LOG.isDebugEnabled ())
        {
            LOG.debug (clazz.getName () + "#" + key + " not found in the local cache. loading from database");
        }
        obj = super.getByKey (clazz, key);
       
        /* put in cache */
        if (obj != null)
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("Storing into cache " + xkey + "=" + obj);
            }
            cache.put (xkey, obj);
        }

        return (T)obj;
    }

//    @Override
//    public <T> List<T> getByKeys (Class<T> clazz, Iterable<?> keys)
//    {        
//        List<T> objects = super.getByKeys (clazz, keys);
//        putInCache (objects);
//                
//        return objects;
//    }
//
//    @Override
//    public <T> List<T> fetch (Query<T> query, int limit)
//    {
//        List<T> objects = super.fetch (query, limit);
//        putInCache (objects);
//        return objects;
//    }
//
//    @Override
//    public <T> List<T> fetch (Query<T> query, int limit, Object offset)
//    {
//        List<T> objects = super.fetch (query, limit, offset);
//        putInCache (objects);
//        return objects;
//    }
//    
    
    
    
    
    //-- Private
    private Object getKey (Object o)
    {
        JdbcClassInfo classInfo = JdbcClassInfo.getClassInfo (o.getClass ());
        Field idField = classInfo.info.getIdField();
        Object value =  Util.readField(o, idField);
        return getKey (o.getClass (), value);
    }
    
    private Object getKey (Class clazz, Object value)
    {
        String name = null;
        Object c = clazz.getAnnotation (Table.class);
        if (c instanceof Table)
        {
            name = ((Table)c).value ();
        }
        
        return !StringUtil.isEmpty (name)
            ? name + "#" + value
            : clazz.getName () + "#" + value;
    }
    
//    private void removeFromCache (Iterable objects)
//    {
//        Cache cache = getCache ();
//        for (Object obj : objects)
//        {
//            Object key = getKey (obj);
//            cache.remove (key);
//        }
//    }
//    
    private void removeFromCache (Object obj)
    {
        Object key = getKey (obj);
        getCache ().remove (key);
    }
}
