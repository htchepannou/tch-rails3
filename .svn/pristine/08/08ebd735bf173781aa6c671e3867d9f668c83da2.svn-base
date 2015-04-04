/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import java.lang.reflect.Method;

/**
 *
 * @author herve
 */
public interface Interceptor
{
    /** Continue to the next interceptor */
    public static final int CONTINUE = 0;

    /* Stop the execution of the interceptor chain */
    public static final int STOP = 1;


    //-- Public methods
    /**
     * This method is called prior accessing a controller
     *
     * @param controller controller
     */
    public int before (Controller controller);

    /**
     * This method is called after accessing a controller
     *
     * @param controller controller
     */
    public void after (Controller controller);

    /**
     * This method is called prior accessing an action method of a controller
     *
     * @param controller controller
     * @param method action method
     */
    public int before (Method method, Controller controller);

    /**
     * This method is called after accessing an action method of a controller
     *
     * @param controller controller
     * @param method action method
     */
    public void after (Method method, Controller controller, Throwable e);
}
