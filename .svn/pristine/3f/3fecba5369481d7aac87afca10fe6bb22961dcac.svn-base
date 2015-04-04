/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.interceptor;

import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.exception.MethodInvocationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author herve
 */
public class ExceptionInterceptor
    implements Interceptor
{
    //-- Interceptor overrides
    public int before (Controller controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (Controller controller)
    {
    }

    public int before (Method method, Controller controller)
    {
        return Interceptor.CONTINUE;
    }

    public void after (Method method, Controller controller, Throwable e)
    {
        handleException (e);
    }

    //-- Private method
    private void handleException (Throwable e)
    {
        if ( e == null )
        {
            return;
        }

        if ( e instanceof InvocationTargetException )
        {
            Throwable cause = e.getCause ();
            if ( cause instanceof RuntimeException )
            {
                throw ( RuntimeException ) cause;
            }
            else
            {
                throw new MethodInvocationException (e);
            }
        }
        else if ( e instanceof RuntimeException )
        {
            throw ( RuntimeException ) e;
        }
        else
        {
            throw new MethodInvocationException (e);
        }
    }
}
