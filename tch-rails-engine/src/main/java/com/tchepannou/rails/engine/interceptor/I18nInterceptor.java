/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.I18nThreadLocal;
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
public class I18nInterceptor 
    implements Interceptor
{
    private static final Logger LOG = LoggerFactory.getLogger (I18nInterceptor.class);
    
    public int before (Controller controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (Controller controller)
    {
        if (LOG.isTraceEnabled ())
        {
            LOG.trace ("after(" + controller + ")");
        }
        
        I18nThreadLocal.remove ();   
    }

    public int before (Method method, Controller controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (Method method, Controller controller, Throwable e)
    {
    }

}
