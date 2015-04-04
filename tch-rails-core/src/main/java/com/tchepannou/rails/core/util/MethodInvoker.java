/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.util;

import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 *
 * @author herve
 */
public class MethodInvoker
{
    //-- Attribute
    private String _methodName;
    private Controller _target;
    private Object[] _params;
    private Method __method;

    //-- Constructor
    public MethodInvoker (String methodName, Controller target)
    {
        _methodName = methodName;
        _target = target;
    }

    public MethodInvoker (String methodName, Controller target, Object[] params)
    {
        this (methodName, target);
        _params = params;
    }

    //-- Public
    public String getMethodName ()
    {
        return _methodName;
    }

    public Method getMethod ()
    {
        if ( __method == null )
        {
            try
            {
                __method = resolveMethod (_methodName);
            }
            catch ( NoSuchMethodException e )
            {
                return null;
            }
        }
        return __method;
    }

    public Object invoke ()
        throws IllegalAccessException,
               InvocationTargetException
    {
        Method method = getMethod ();
        if ( method == null )
        {
            return null;
        }

        Class[] types = method.getParameterTypes ();
        if ( types == null || types.length == 0 )
        {
            return method.invoke (_target);
        }
        else
        {
            Class type = types[0];
            if ( type.isArray () )
            {
                return method.invoke (_target, new Object[]
                    {
                        _params
                    });
            }
            else
            {
                return method.invoke (_target, _params[0]);
            }
        }
    }

    public boolean invoke (List<Interceptor> interceptors)
    {
        Method method = getMethod ();
        if ( method == null )
        {
            return true;
        }

        int status = Interceptor.CONTINUE;
        int size = interceptors.size ();
        for ( int i = 0; i < size; i++ )
        {
            Interceptor itc = interceptors.get (i);
            status = itc.before (method, _target);
            if ( status != Interceptor.CONTINUE )
            {
                break;
            }
        }

        Throwable ex = null;
        try
        {
            if ( status != Interceptor.STOP )
            {
                invoke ();
            }
        }
        catch ( InvocationTargetException e )
        {
            ex = e;
        }
        catch ( IllegalAccessException e )
        {
            ex = e;
        }
        finally
        {
            /* Post-interceptor */
            for ( int i = size - 1; i >= 0; i-- )
            {
                Interceptor itc = interceptors.get (i);
                itc.after (method, _target, ex);
            }
        }
        return status == Interceptor.CONTINUE;
    }

    //-- Private methods
    private Method resolveMethod (String name)
        throws NoSuchMethodException
    {
        Class clazz = _target.getClass ();

        /* no records */
        if ( _params == null || _params.length == 0 )
        {
            return clazz.getMethod (name);
        }

        /* 1 record */
        if ( _params.length == 1 )
        {
            Object param = _params[0];
            if (param != null)
            {
                Method method = resolveMethod (name, param.getClass ());
                if ( method != null )
                {
                    return method;
                }
            }
        }

        /* n records */
        try
        {
            return clazz.getMethod (name, new Class[]
                {
                    _params.getClass ()
                });
        }
        catch ( NoSuchMethodException e )
        {
            Object param = _params[0];
            if (param != null)
            {
                Method method = resolveMethod (name, param.getClass ());
                if ( method == null )
                {
                    method = clazz.getMethod (name);
                }
                return method;
            }
        }
        return null;
    }

    private Method resolveMethod (String name, Class type)
    {
        try
        {
            return _target.getClass ().getMethod (name, type);
        }
        catch ( NoSuchMethodException e )
        {
            return null;
        }
    }
}
