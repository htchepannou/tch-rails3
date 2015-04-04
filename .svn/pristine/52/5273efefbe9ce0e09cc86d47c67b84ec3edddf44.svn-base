/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.Interceptor;
import java.util.List;

/**
 *
 * @author herve
 */
public class AbstractControllerWrapper 
{
    //-- Protected
    protected int before (Controller controller, List<Interceptor> interceptors)
    {
        int size = interceptors.size ();
        for ( int i = 0; i < size; i++ )
        {
            Interceptor itc = interceptors.get (i);
            int status = itc.before (controller);
            if ( status != Interceptor.CONTINUE )
            {
                return status;
            }
        }
        return Interceptor.CONTINUE;
    }

    protected void after (Controller controller, List<Interceptor> interceptors)
    {
        int size = interceptors.size ();
        for ( int i = size - 1; i >= 0; i-- )
        {
            Interceptor itc = interceptors.get (i);
            itc.after (controller);
        }

    }
}
