/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.api;

import com.tchepannou.util.StringUtil;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


/**
 * This class is responsible of managing Internationalization the the application
 *
 * @author herve
 */
public class I18n
{
    //-- Static Attribute
    public static final String DEFAULT_BUNDLE_NAME = "Bundle";

    private static final String DATE_FORMAT = "EEEE, MMMM d, yyyy";
    private static final String TIME_FORMAT = "hh:mm";
    private static final String SHORT_DATE_FORMAT = "yyyy-MMM-dd";
    private static final String DATETIME_FORMAT = "yyyy-MMM-dd HH:mm";
    
    //-- Attributes
    private String _bundleName;
    private Locale _userLocale;
//    private Locale _siteLocale;
    private ResourceBundle __bundle;
    private boolean _bundleResolved;
    private DateFormat __dateTimeFormat;
    private DateFormat __dateFormat;
    private DateFormat __timeFormat;
    private DateFormat __shortDateFormat;
    private DateFormat __rssDateFormat;

    //-- Constructor
    public I18n (String bundleName, Locale userLocale)
    {
        _bundleName = bundleName;
        _userLocale = userLocale;
//        _siteLocale = siteLocale;
    }
    
    //-- Public Method
    public String translate (String key)
    {
        try
        {
            ResourceBundle bundle = getBundle ();
            return bundle != null ? bundle.getString (key) : key;
        }
        catch (MissingResourceException e)
        {
            return key;
        }
    }

    public String translate (String key, String...args)
    {
        String text = translate (key);
        if (args != null)
        {
            for (int i=0 ; i<args.length ; i++)
            {
                text = StringUtil.replace (text, "{" + i + "}", args[i]);
            }
        }
        return text;
    }

    public String translate (String key, Collection<String> args)
    {
        String[] params = args.toArray (new String[] {});
        return translate(key, params);
    }

    public String translate (String key, Map<String, String> args)
    {
        String text = translate (key);
        if (args != null)
        {
            for (String arg : args.keySet ())
            {
                text = StringUtil.replace (text, "{" + arg + "}", args.get (arg));
            }
        }
        return text;
    }

    public String formatMoney (Number amount)
    {
        return formatMoney (amount, _userLocale);
    }
    public String formatMoney (Number amount, Locale locale)
    {
        if (amount == null)
        {
            return StringUtil.EMPTY;
        }
        else
        {
            NumberFormat nf = locale != null ? NumberFormat.getCurrencyInstance (locale) : NumberFormat.getCurrencyInstance ();
            return nf.format (amount);
        }
    }

    public String formatNumber (Number number)
    {
        return formatNumber (number, _userLocale);
    }
    public String formatNumber (Number number, Locale locale)
    {
        if (number == null)
        {
            return StringUtil.EMPTY;
        }
        else
        {
            NumberFormat nf = locale != null ? NumberFormat.getIntegerInstance (locale) : NumberFormat.getIntegerInstance ();
            return nf.format (number);
        }
    }

    public String formatPercentage (Number amount)
    {
        return formatPercentage (amount, _userLocale);
    }
    public String formatPercentage (Number amount, Locale locale)
    {
        if (amount == null)
        {
            return StringUtil.EMPTY;
        }
        else
        {
            NumberFormat nf = locale != null ? NumberFormat.getPercentInstance (locale) : NumberFormat.getPercentInstance ();
            nf.setMinimumFractionDigits (2);
            nf.setMaximumFractionDigits (2);
            return nf.format (amount);
        }
    }

    public Currency getCurrency ()
    {
        return getCurrency (_userLocale);
    }

    public Currency getCurrency (Locale locale)
    {
        return Currency.getInstance (locale);
    }

    public String formatDecimal (Number number)
    {
        return formatDecimal (number, _userLocale);
    }
    public String formatDecimal (Number number, Locale locale)
    {
        if (number == null)
        {
            return StringUtil.EMPTY;
        }
        else
        {
            NumberFormat nf = locale != null ? NumberFormat.getNumberInstance (locale) : NumberFormat.getNumberInstance();
            nf.setMinimumFractionDigits (2);
            nf.setMaximumFractionDigits (2);
            return nf.format (number);
        }
    }

    public String formatDateTime (Date date)
    {
        if (date != null)
        {
            DateFormat fmt = getDateTimeFormat ();
            return fmt.format (date);
        }
        else
        {
            return StringUtil.EMPTY;
        }
    }

    public String formatDate (Date date)
    {
        if (date != null)
        {
            DateFormat fmt = getDateFormat ();
            return fmt.format (date);
        }
        else
        {
            return StringUtil.EMPTY;
        }
    }
    
    public String formatTime (Date date)
    {
        if (date != null)
        {
            DateFormat fmt = getTimeFormat ();
            return fmt.format (date);
        }
        else
        {
            return StringUtil.EMPTY;
        }
    }

    public String formatShortDate (Date date)
    {
        if (date != null)
        {
            DateFormat fmt = getShortDateFormat ();
            return fmt.format (date);
        }
        else
        {
            return StringUtil.EMPTY;
        }
    }

    public String formatRSSDate (Date date)
    {
        return date != null ? getRSSDateFormat ().format (date) : StringUtil.EMPTY;
    }

    public String formatDate (Date date, String format)
    {
        if (date != null)
        {
            DateFormat fmt = new SimpleDateFormat (format);
            return fmt.format (date);
        }
        else
        {
            return StringUtil.EMPTY;
        }
    }



    

    //-- Private methods
    private ResourceBundle getBundle ()
    {
        if (__bundle == null && !_bundleResolved)
        {
            _bundleResolved = true;
            try
            {
                __bundle = _userLocale != null
                    ? ResourceBundle.getBundle (_bundleName, _userLocale)
                    : ResourceBundle.getBundle (_bundleName);
            }
            catch (MissingResourceException e)
            {
                if (_userLocale != null)
                {
                    try
                    {
                        __bundle = ResourceBundle.getBundle (_bundleName);
                    }
                    catch (MissingResourceException ee)
                    {
                        throw new IllegalStateException ("Bundle not found");
                    }
                }
            }
        }
        return __bundle;
    }

    private DateFormat getDateTimeFormat ()
    {
        if (__dateTimeFormat == null)
        {
            __dateTimeFormat = new SimpleDateFormat (DATETIME_FORMAT);
        }
        return __dateTimeFormat;
    }

    private DateFormat getDateFormat ()
    {
        if (__dateFormat == null)
        {
            __dateFormat = new SimpleDateFormat (DATE_FORMAT);
        }
        return __dateFormat;
    }
    
    private DateFormat getTimeFormat ()
    {
        if (__timeFormat == null)
        {
            __timeFormat = new SimpleDateFormat (TIME_FORMAT);
        }
        return __timeFormat;        
    }

    private DateFormat getShortDateFormat ()
    {
        if (__shortDateFormat == null)
        {
            __shortDateFormat = new SimpleDateFormat (SHORT_DATE_FORMAT);
        }
        return __shortDateFormat;
    }

    public DateFormat getRSSDateFormat ()
    {
        if (__rssDateFormat == null)
        {
            __rssDateFormat =  new SimpleDateFormat("dd MMM yyyy HH:mm:ss Z");
        }
        return __rssDateFormat;
    }

    public String getBundleName ()
    {
        return _bundleName;
    }

    public Locale getUserLocale()
    {
        return _userLocale;
    }

}
