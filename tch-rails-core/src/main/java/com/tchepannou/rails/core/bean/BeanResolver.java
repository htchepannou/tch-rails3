/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.bean;

/**
 *
 * @author herve
 */
public interface BeanResolver
{
    public Object resolve(Object key, Class type);
}
