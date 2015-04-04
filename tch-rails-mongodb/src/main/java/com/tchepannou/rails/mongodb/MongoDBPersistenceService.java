/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mongodb;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.mongodb.Mongo;
import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.bean.BeanResolver;
import com.tchepannou.rails.core.exception.InitializationException;
import com.tchepannou.rails.core.impl.AbstractService;
import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.util.IsolationLevel;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.beanutils.ConversionException;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class MongoDBPersistenceService 
    extends AbstractService
    implements PersistenceService, BeanResolver
{
    //-- Static
    private static final Logger LOG = LoggerFactory.getLogger(MongoDBPersistenceService.class);
    private static final int DEFAULT_PORT = 27017;

    //-- Private methods
    private BeanMapper _mapper = new BeanMapper ();
    private Datastore _datastore;


    //-- PersistenceService overrides    
    public ActiveRecord get (Serializable id, Class<? extends ActiveRecord> type)
    {
        return (ActiveRecord)resolve (id, type);
    }

    public void get (Object o)
    {
        _datastore.get (o);
    }

    public void save (Object o)
    {
        _datastore.save (o);
    }

    public void delete (Object o)
    {
        _datastore.delete (o);
    }

    public Map describe (Object o)
    {
        try
        {
            return _mapper.describe (o);
        }
        catch (Exception e)
        {
            throw new IllegalStateException ("Mapping error", e);
        }
    }

    public void populate (Object o, Map properties)
    {
        try
        {
            _mapper.populate (o, properties);
        }
        catch (Exception e)
        {
            throw new IllegalStateException ("Mapping error", e);
        }
    }

    public void beginTransaction (IsolationLevel level)
    {
    }

    public void commitTransaction ()
    {
    }

    public void rollbackTransaction ()
    {
    }
    
    public void closeConnection ()
    {
    }
    

    //-- Converter overrides
    public Object convert (Class type, Object value)
    {
        if (value == null)
        {
            return null;
        }
        if (value instanceof ObjectId)
        {
            return get ((ObjectId)value, type);
        }
        else if (value instanceof String)
        {
            ObjectId id = new ObjectId ((String)value);
            return get (id, type);
        }
        else
        {
            throw new ConversionException ("Unable to convert " + value + " to " + type);
        }
    }

    
    //-- Override
    @Override
    public void init (ServiceContext context)
    {
        LOG.info ("Initializing");

        super.init (context);
        try
        {
            /* Load configuration */
            Properties properties = loadConfigurationAsProperties ("rails-mongodb.properties");
            if (LOG.isDebugEnabled ())
            {
                LOG.debug ("MongoDB Properties");
                for (Object name : properties.keySet ())
                {
                    LOG.debug (" " + name + "=" + properties.get (name));
                }
            }

            /* Integration with SLF4J */
            MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);

            /* Create datastore */
            String host = properties.getProperty ("db.hostname");
            String port = properties.getProperty ("db.port");
            String name = properties.getProperty ("db.name");
            String pkg = properties.getProperty("db.base-package");
            Mongo mongo = new Mongo (host, StringUtil.toInt(port, DEFAULT_PORT));
            Morphia morphia = new Morphia ();
            morphia.mapPackage (pkg);
            _datastore = morphia.createDatastore (mongo, name);

            /* Register converter */
            _mapper.setBeanResolver (this);

            LOG.info ("Initialized");
        }
        catch (IOException e)
        {
            throw new InitializationException ("Initialization error", e);
        }
    }

    @Override
    public void destroy ()
    {
        LOG.info ("Destroying");

        super.destroy ();
        
        _datastore = null;
        _mapper.setBeanResolver (null);
        
        LOG.info ("Destroyed");
    }

    //-- BeanResolver
    public Object resolve (Object key, Class type)
    {
        return _datastore.get (type, key);
    }
}
