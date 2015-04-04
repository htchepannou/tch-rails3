package com.tchepannou.rails.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotation that indicate whether a ActionController class or method requires
 * user to be logged in
 */
@Retention (RetentionPolicy.RUNTIME)
@Target (value={ElementType.TYPE, ElementType.METHOD})
public @interface  RequireUser
{
    String[] permissions() default "";
}
