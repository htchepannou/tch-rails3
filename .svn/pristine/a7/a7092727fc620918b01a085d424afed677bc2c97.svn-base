/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.service.PersistenceServiceThreadLocal;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class PersistenceInterceptor 
    implements Interceptor
{
    private static final Logger LOG = LoggerFactory.getLogger (PersistenceInterceptor.class);
    
    public int before (Controller controller)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("before(" + controller + ")");
        }
        PersistenceServiceThreadLocal.Data data = PersistenceServiceThreadLocal.get ();
        if (data == null)
        {
            PersistenceService ps = (PersistenceService)controller.getContext ().getContainerContext ().findService (PersistenceService.class);
            if (ps != null)
            {
                if (LOG.isDebugEnabled ())
                {
                    LOG.debug (controller + " is initializing PersitenceService");
                }
                PersistenceServiceThreadLocal.init (ps, controller);
            }
        }
        return Interceptor.CONTINUE;
    }

    public void after (Controller controller)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("after(" + controller + ")");
        }
        
        PersistenceServiceThreadLocal.Data data = PersistenceServiceThreadLocal.get ();
        if (data != null && controller.equals (data.controller) )
        {
            /* close connexion */
            if (LOG.isDebugEnabled ())
            {
                LOG.debug (controller + " is closing PersitenceService");
            }
            data.service.closeConnection ();
            
            /* 
             * clear the ThreadLocal 
             * For ActionController, it get remove in PersistenceFilter!
             */
            if (!(data.controller instanceof ActionController))
            {
                PersistenceServiceThreadLocal.remove ();
            }
        }
        
    }

    public int before (Method method, Controller controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (Method method, Controller controller, Throwable e)
    {
    }

}
