package com.tchepannou.rails.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that indicate an {@link ActionController}
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (value={ElementType.METHOD})
public @interface  Job
{
    String cron() default "";
}
