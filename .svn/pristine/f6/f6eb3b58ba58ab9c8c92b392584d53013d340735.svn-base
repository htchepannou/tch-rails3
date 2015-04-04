/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.siena;

import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.bean.BeanResolver;
import com.tchepannou.rails.core.cache.Cache;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.impl.AbstractService;
import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.util.IsolationLevel;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import siena.PersistenceManager;
import siena.PersistenceManagerFactory;

/**
 * Implementation of {@link PersistenceService} based on <a href="http://www.sienaproject.com/index.html">Siena</a>
 *
 * @author herve
 */
public class SienaPersistenceService
    extends AbstractService
    implements PersistenceService,
               BeanResolver
{
    //-- Static
    private static final Logger LOG = LoggerFactory.getLogger (SienaPersistenceService.class);
    
    //-- Private methods
    private BeanMapper _mapper = new BeanMapper ();
    private ThreadLocal<Object> _tx = new ThreadLocal<Object> ();
    private Class<? extends ActiveRecord> _modelClazz;

    //-- Public Method
    public PersistenceManager getPersistenceManager ()
    {
        return getPersistenceManager (_modelClazz);
    }

    public PersistenceManager getPersistenceManager (Class<? extends ActiveRecord> type)
    {
        return PersistenceManagerFactory.getPersistenceManager (type);
    }

    //-- PersistenceService overrides
    public ActiveRecord get (Serializable id, Class<? extends ActiveRecord> type)
    {
        return ( ActiveRecord ) resolve (id, type);
    }

    public void beginTransaction (IsolationLevel level)
    {
        PersistenceManager pm = getPersistenceManager ();
        pm.beginTransaction ();
        _tx.set (new Object ());
    }

    public void commitTransaction ()
    {
        if ( _tx.get () != null )
        {
            try
            {
                PersistenceManager pm = getPersistenceManager ();
                pm.commitTransaction ();
            }
            finally
            {
                _tx.remove ();
            }
        }
    }

    public void rollbackTransaction ()
    {
        if ( _tx.get () != null )
        {
            try
            {
                PersistenceManager pm = getPersistenceManager ();
                pm.rollbackTransaction ();
            }
            finally
            {
                _tx.remove ();
            }
        }
    }

    public void closeConnection ()
    {
        PersistenceManager pm = getPersistenceManager ();
        pm.closeConnection ();
        _tx.remove ();
    }

    public void save (Object o)
    {
        PersistenceManager pm = PersistenceManagerFactory.getPersistenceManager (o.getClass ());
        pm.save (o);
    }

    public void delete (Object o)
    {
        PersistenceManager pm = PersistenceManagerFactory.getPersistenceManager (o.getClass ());
        pm.delete (o);
    }

    public Map describe (Object o)
    {
        try
        {
            return _mapper.describe (o);
        }
        catch ( IllegalAccessException ex )
        {
            throw new IllegalStateException ("Mapping error", ex);
        }
        catch ( InvocationTargetException ex )
        {
            throw new IllegalStateException ("Mapping error", ex);
        }
        catch ( NoSuchMethodException ex )
        {
            throw new IllegalStateException ("Mapping error", ex);
        }
    }

    public void get (Object o)
    {
        PersistenceManager pm = PersistenceManagerFactory.getPersistenceManager (o.getClass ());
        pm.get (o);
    }

    public void populate (Object o, Map properties)
    {
        try
        {
            _mapper.populate (o, properties);
        }
        catch ( IllegalAccessException ex )
        {
            throw new IllegalStateException ("Mapping error", ex);
        }
        catch ( InvocationTargetException ex )
        {
            throw new IllegalStateException ("Mapping error", ex);
        }
    }

    public Cache getCache ()
    {
        PersistenceManager pm = getPersistenceManager ();
        if (pm instanceof CacheJdbcPersistenceManager)
        {
            return ((CacheJdbcPersistenceManager)pm).getCache ();
        }
        return null;
    }
    
    //-- Service overrides
    @Override
    public void init (ServiceContext context)
    {
        super.init (context);

        LOG.info ("Initializing");

        _modelClazz = resolveModel (context);
        if ( LOG.isDebugEnabled () )
        {
            LOG.debug (" Model class=" + _modelClazz);
        }
        if ( _modelClazz == null )
        {
            throw new InitializationException ("Unable to find model class");
        }
        _mapper.setBeanResolver (this);


        LOG.info ("Initialized");
    }

    @Override
    public void destroy ()
    {
        LOG.info ("Destroying");

        try
        {
            _mapper.setBeanResolver (null);
            closeConnection ();
        }
        finally
        {
            super.destroy ();
        }

        LOG.info ("Destroyed");
    }

    //-- BeanResolver
    public Object resolve (Object key, Class type)
    {
        PersistenceManager pm = getPersistenceManager (type);
        return pm.getByKey (type, key);
    }

    //-- Private
    private Class<? extends ActiveRecord> resolveModel (ServiceContext context)
    {
        String pkg = context.getContainerContext ().getBasePackage ();
        int i = pkg.indexOf (".");
        String root = i > 0
            ? pkg.substring (0, i)
            : pkg;
        Reflections reflections = new Reflections (root);
        Set<Class<? extends ActiveRecord>> classes = reflections.getSubTypesOf (ActiveRecord.class);
        return classes.size () > 0
            ? classes.iterator ().next ()
            : null;
    }
}
