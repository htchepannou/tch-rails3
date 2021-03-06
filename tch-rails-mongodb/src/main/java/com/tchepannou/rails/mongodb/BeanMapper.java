/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.mongodb;

import com.google.code.morphia.annotations.Id;
import com.tchepannou.rails.core.bean.AbstractBeanMapper;
import java.lang.reflect.Field;

/**
 * Implementation of {@link BeanUtilsBean} for populating active records
 *
 * @author herve
 */
class BeanMapper
    extends AbstractBeanMapper
{
    @Override
    protected boolean isId (Field f)
    {
        return f.getAnnotation (Id.class) != null;
    }

    @Override
    protected boolean isAutogenerated (Field f)
    {
        return isId (f);
    }


}
