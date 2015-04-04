/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import java.util.Locale;

/**
 *
 * @author herve
 */
public class I18nThreadLocal
{
    //-- Static Attributes
    private static ThreadLocal<I18n> _i18n = new ThreadLocal<I18n> ();


    //-- Public method
    public static I18n get (Locale locale)
    {
        I18n i18n = _i18n.get ();
        if (i18n == null)
        {
            i18n = new I18n (I18n.DEFAULT_BUNDLE_NAME, locale);
            _i18n.set (i18n);
        }
        return i18n;
    }

    public static I18n get ()
    {
        return get (Locale.getDefault ());
    }
    
    public static void remove ()
    {
        _i18n.remove ();;
    }
}
