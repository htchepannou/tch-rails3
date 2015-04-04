/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.annotation.RequireTransaction;
import com.tchepannou.rails.core.api.ActionContext;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.Context;
import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.service.PersistenceService;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the interceptor that start/commit/rollback transaction.
 * The interceptor will trigger the transaction if the ActionController or the
 * action method has the annotation {@link RequireTransaction}
 * 
 * @author herve
 */
public class TransactionInterceptor
    implements Interceptor
{
    //-- Interceptor
    private static final Logger LOG = LoggerFactory.getLogger(TransactionInterceptor.class);
    
    //-- Interceptor
    @Override
    public int before (Method method, Controller controller)
    {
        begin (controller, method);
        return Interceptor.CONTINUE;
    }

    @Override
    public void after (Method method, Controller controller, Throwable e)
    {
        Context context = controller.getContext ();
        if (e == null && !context.isRollback ())
        {
            commit (controller, method);
        }
        else
        {
            if (LOG.isDebugEnabled ())
            {
                LOG.warn ("Rolling back the transaction");
                if (controller instanceof ActionController)
                {
                    LOG.warn (" Flash: " + ((ActionController)controller).getFlash ());
                }
                if (e != null)
                {
                    LOG.warn (" Cause: ", e);
                }
            }
            rollback (controller, method);
        }
    }

    public int before (Controller controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (Controller controller)
    {
    }


    //-- Private methods
    private void begin (Controller controller, Method method)
    {
        RequireTransaction ann = getAnnotation (controller, method);
        if (ann != null)
        {
            PersistenceService ps = getPersistenceService (controller);
            if (ps != null)
            {
                ps.beginTransaction (ann.isolation ());
            }
        }
    }

    private void commit (Controller controller, Method method)
    {
        RequireTransaction ann = getAnnotation (controller, method);
        if (ann != null)
        {
            PersistenceService ps = getPersistenceService (controller);
            if (ps != null)
            {
                ps.commitTransaction ();
            }
        }
    }

    private void rollback (Controller controller, Method method)
    {
        RequireTransaction ann = getAnnotation (controller, method);
        if (ann != null)
        {
            PersistenceService ps = getPersistenceService (controller);
            if (ps != null)
            {
                ps.rollbackTransaction ();
            }
        }
    }

    private RequireTransaction getAnnotation (Object controller, Method method)
    {
        RequireTransaction ann = method.getAnnotation(RequireTransaction.class);
        if (ann == null)
        {
            ann = controller.getClass ().getAnnotation (RequireTransaction.class);
        }
        return ann;
    }
    
    private PersistenceService getPersistenceService (Controller controller)
    {
        return (PersistenceService)controller.getContext ().getContainerContext ().findService (PersistenceService.class);
    }
}
