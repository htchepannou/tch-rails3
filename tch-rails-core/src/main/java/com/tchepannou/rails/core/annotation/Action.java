package com.tchepannou.rails.core.annotation;

import com.tchepannou.rails.core.api.ActiveRecord;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicate an {@link ActionController}
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (value={ElementType.TYPE})
public @interface  Action
{
    String name() default "";

    Class<? extends ActiveRecord> modelClass () default ActiveRecord.class;
}
