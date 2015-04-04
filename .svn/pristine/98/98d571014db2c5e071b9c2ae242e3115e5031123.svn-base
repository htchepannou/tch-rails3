package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.service.PersistenceServiceThreadLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Implementation of the <a href="http://en.wikipedia.org/wiki/Active_record_pattern">ActiveRecord</a> design pattern.
 *
 * @author  Herve Tchepannou
 */
public abstract class ActiveRecord
    implements Serializable
{
    //-- Attribute
    private static final List<String> NO_ERROR = new ArrayList<String> ();
    private transient List<String> __errors;
    
    //-- Abstract methods
    public abstract Serializable getId ();


    //-- Public methods
    public static Set<Serializable> toIds(Iterable<? extends ActiveRecord> objs)
    {
        Set<Serializable> ids = new HashSet ();
        for (ActiveRecord obj : objs)
        {
            if (obj != null)
            {
                ids.add(obj.getId ());
            }
        }
        return ids;
    }
    

    //-- Persistence methods
    /**
     * Returns all the properties of the ActiveRecord in a <code>HashMap</code>
     */
    public Map describe ()
    {
        return getPersistenceService ().describe (this);
    }

    /**
     * Populate the model properties from a set of name/value pairs
     *
     * @param properties    name/value pairs containing the model properties
     */
    public void populate (Map properties)
    {
        getPersistenceService ().populate (this, properties);
    }

    /**
     * Save the model into the database.
     * if the model is new, it getPersistenceService created into the database
     *
     * @param validate if <code>true</code>, the model will be validated
     *
     * @return if <code>validate</code> is <code>true</code>, this method returns <code>false</code> if the validation fails,
     *         else it will always returns <code>true</code>.
     */
    public boolean save (boolean validate)
    {
        if ( validate )
        {
            checkSave ();
            if (hasErrors ())
            {
                return false;
            }
        }

        PersistenceService ps = getPersistenceService ();
        ps.save (this);
        return true;
    }

    /**
     * Delete the model from the database
     */
    public boolean delete (boolean validate)
    {
        if ( validate )
        {
            checkDelete ();
            if (hasErrors ())
            {
                return false;
            }
        }

        getPersistenceService ().delete (this);
        return true;
    }

    /**
     * Load from the datastore
     */
    public ActiveRecord get ()
    {
        PersistenceService ps = getPersistenceService ();
        if (ps != null)
        {
            ps.get (this);
        }
        return this;
    }

    //-- Validation methods
    /**
     * Checks all validation rule before deleting. This method is called by {@link #delete(boolean)}
     */
    public void checkDelete ()
    {
    }

    /**
     * Checks all validation rule before deleting. This method is called by {@link #save(boolean)}
     */
    public void checkSave ()
    {
    }

    /**
     * Returns the list of validation errors
     */
    public List<String> getErrors ()
    {
        return __errors != null
            ? Collections.unmodifiableList (__errors)
            : NO_ERROR;
    }

    /**
     * Returns <code>true</code> if the record has errors
     */
    public boolean hasErrors ()
    {
        return __errors != null && !__errors.isEmpty ();
    }


    //-- Protected
    protected PersistenceService getPersistenceService ()
    {
        return PersistenceServiceThreadLocal.getPersistenceService ();
    }

    /**
     * Record an error
     *
     * @param error Error message
     */
    protected void addError (String error)
    {
        if (__errors == null)
        {
            __errors = new ArrayList<String> ();
        }

        __errors.add (error);
    }

    /**
     * Remove all errors
     */
    protected void clearErrors ()
    {
        __errors = null;
    }

    public I18n getI18n ()
    {
        return I18nThreadLocal.get ();
    }

    //-- Object overrides
    @Override
    public boolean equals (Object o)
    {
        if (o != null && getClass () == o.getClass ())
        {
            Serializable id = getId ();
            if (id != null)
            {
                Serializable xid = ((ActiveRecord)o).getId ();
                return id.equals (xid);
            }
            return super.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode ()
    {
        Serializable id = getId ();
        return id != null ? id.hashCode () : super.hashCode();
    }

    @Override
    public String toString ()
    {
        return getClass ().getSimpleName () + "{" + getId () + "}";
    }



}
