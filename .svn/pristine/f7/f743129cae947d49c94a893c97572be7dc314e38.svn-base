/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.service.OptionService;
import com.tchepannou.util.DateUtil;
import com.tchepannou.util.HTMLUtil;
import com.tchepannou.util.MimeUtil;
import com.tchepannou.util.StringUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringEscapeUtils;

/**
 * Utility functions to send to view
 * 
 * @author herve
 */
public class Util
{
    //-- Attributes
    private long _uid = System.currentTimeMillis ();
    private ContainerContext _context;

    //-- Public
    public void setContainerContext (ContainerContext context)
    {
        _context = context;
    }

    //-- OptionService method
    /**
     * Returns the value of a system option.
     * This method calls {@link OptionService#get(java.lang.String, java.lang.String)}
     *
     * @param name Name of the option
     * @param defaultValue default value to return if the option not set     
     */
    public String getOption (String name, String defaultValue)
    {
        OptionService srv = ( OptionService ) _context.findService (OptionService.class);
        return srv != null
            ? srv.get (name, defaultValue)
            : defaultValue;
    }

    /**
     * Returns the valud of the option {@link {OptionService#OPTION_SITE_URL}
     */
    public String getSiteUrl ()
    {
        return getOption (OptionService.OPTION_SITE_URL, "");
    }

    /**
     * Returns the value of the option {@link OptionService#OPTION_ASSET_URL}
     */
    public String getAssetUrl ()
    {
        return getOption (OptionService.OPTION_ASSET_URL, "");
//        boolean secure = false;
//        if (_context instanceof ActionContext)
//        {
//            ActionContext ac = (ActionContext)_context;
//            secure = ac.isSecure ();
//        }
//        return secure
//            ? getOption (OptionService.OPTION_ASSET_URL_HTTPS, "")
//            : getOption (OptionService.OPTION_ASSET_URL, "");
    }

    //-- String method
    public String repeat (String str, int count)
    {
        StringBuilder sb = new StringBuilder ();
        for ( int i = 0; i < count; i++ )
        {
            sb.append (str);
        }
        return sb.toString ();
    }

    //-- HTML methods
    public String encodeHtml (String str)
    {
        return encodeHtml (str, true);
    }

    public String encodeHtml (String str, boolean encodeCR)
    {
        return HTMLUtil.encode (str, encodeCR);
    }
    
    
    public boolean isHtml (String str)
    {
        return HTMLUtil.isHtml (str);
    }

    public String encodeUrl (String str)
    {
        try
        {
            return str != null
                ? URLEncoder.encode (str, "utf-8")
                : null;
        }
        catch ( UnsupportedEncodingException e )
        {
            return str;
        }
    }

    public String decodeHtml (String html)
    {
        return StringEscapeUtils.unescapeHtml (html);
    }

    public String encodeJavascript (String str)
    {
        return str != null
            ? StringEscapeUtils.escapeJavaScript (str)
            : StringUtil.EMPTY;
    }

    //-- XML methods
    public String encodeXml (String str)
    {
        return str != null 
            ? StringEscapeUtils.escapeXml (str)
            : StringUtil.EMPTY;
    }

    //-- CSV method
    public String encodeCsv (String str)
    {
        return str != null
            ? StringEscapeUtils.escapeCsv (str)
            : StringUtil.EMPTY;
    }

    //-- JSON
    public String encodeJson (String s)
    {
        if (s == null)
        {
            return StringUtil.EMPTY;
        }
        StringBuilder sb = new StringBuilder ();
        for ( int i = 0; i < s.length (); i++ )
        {
            char ch = s.charAt (i);
            switch ( ch )
            {
                case '"':
                    sb.append ("\\\"");
                    break;
                case '\\':
                    sb.append ("\\\\");
                    break;
                case '\b':
                    sb.append ("\\b");
                    break;
                case '\f':
                    sb.append ("\\f");
                    break;
                case '\n':
                    sb.append ("\\n");
                    break;
                case '\r':
                    sb.append ("\\r");
                    break;
                case '\t':
                    sb.append ("\\t");
                    break;
                case '/':
                    sb.append ("\\/");
                    break;
                default:
                    //Reference: http://www.unicode.org/versions/Unicode5.1.0/
                    if ( (ch >= '\u0000' && ch <= '\u001F') || (ch >= '\u007F' && ch <= '\u009F') || (ch >= '\u2000' && ch <= '\u20FF') )
                    {
                        String ss = Integer.toHexString (ch);
                        sb.append ("\\u");
                        for ( int k = 0; k < 4 - ss.length (); k++ )
                        {
                            sb.append ('0');
                        }
                        sb.append (ss.toUpperCase ());
                    }
                    else
                    {
                        sb.append (ch);
                    }
            }
        }//for
        return sb.toString ();
    }

    //-- Date methods
    public Date getToday ()
    {
        return DateUtil.today ();
    }
    public Date getTomorrow()
    {
        return DateUtil.tomorrow ();
    }
    public Date getNow ()
    {
        return new Date ();
    }
    public Date getYesterday ()
    {
        return DateUtil.yesterday ();
    }

    public int getYear ()
    {
        return datePart (Calendar.YEAR);
    }

    public int getYear (Date d)
    {
        return datePart (Calendar.YEAR, d);
    }

    public int getMonth ()
    {
        return datePart (Calendar.MONTH);
    }

    public int getMonth (Date d)
    {
        return datePart (Calendar.MONTH, d);
    }

    public int getDay ()
    {
        return datePart (Calendar.DAY_OF_MONTH);
    }

    public int getDay (Date d)
    {
        return datePart (Calendar.DAY_OF_MONTH, d);
    }

    public int getMinute (Date d)
    {
        return datePart (Calendar.MINUTE, d);
    }

    public int getMinute ()
    {
        return datePart (Calendar.MINUTE);
    }

    public int getHour (Date d)
    {
        return datePart (Calendar.HOUR_OF_DAY, d);
    }

    public int getHour ()
    {
        return datePart (Calendar.HOUR_OF_DAY);
    }

    public int getSecond (Date d)
    {
        return datePart (Calendar.SECOND, d);
    }

    public int getSecond ()
    {
        return datePart (Calendar.SECOND);
    }

    private int datePart (int part)
    {
        Calendar cal = Calendar.getInstance ();
        return cal.get (part);
    }

    private int datePart (int part, Date date)
    {
        if ( date == null )
        {
            return -1;
        }
        else
        {
            Calendar cal = new GregorianCalendar ();
            cal.setTime (date);
            return cal.get (part);
        }
    }

    public String timespanText (Date date)
    {
        return date != null
            ? timespanText (date.getTime ())
            : null;
    }

    public String timespanText (long timeMillis)
    {
        long diffInSeconds = (System.currentTimeMillis () - timeMillis) / 1000;

        long min = (diffInSeconds = (diffInSeconds / 60)) >= 60
            ? diffInSeconds % 60
            : diffInSeconds;
        long hrs = (diffInSeconds = (diffInSeconds / 60)) >= 24
            ? diffInSeconds % 24
            : diffInSeconds;
        long days = (diffInSeconds = (diffInSeconds / 24)) >= 30
            ? diffInSeconds % 30
            : diffInSeconds;
        long months = (diffInSeconds = (diffInSeconds / 30)) >= 12
            ? diffInSeconds % 12
            : diffInSeconds;
        long years = (diffInSeconds = (diffInSeconds / 12));

        String txt;
        if ( years > 0 )
        {
            if ( years == 1 )
            {
                txt = "1 year ago";
            }
            else
            {
                txt = years + " years ago";
            }
        }
        else if ( months > 0 )
        {
            if ( months == 1 )
            {
                txt = "1 month ago";
            }
            else
            {
                txt = months + " months ago";
            }
        }
        else if ( days > 0 )
        {
            if ( days == 1 )
            {
                txt = "Yesterday";
            }
            else
            {
                long weeks = days / 7;
                if ( weeks == 1 )
                {
                    txt = "Last week";
                }
                else if ( weeks < 1 )
                {
                    txt = days + " days ago";
                }
                else
                {
                    txt = weeks + " weeks ago";
                }
            }
        }
        else if ( hrs > 0 )
        {
            if ( hrs == 1 )
            {
                txt = "1 hour ago";
            }
            else
            {
                txt = hrs + " hours ago";
            }
        }
        else if ( min > 0 )
        {
            if ( min == 1 )
            {
                txt = 1 + " minute ago";
            }
            else
            {
                txt = min + " minutes ago";
            }
        }
        else
        {
            txt = "few seconds ago";
        }
        return txt;
    }

    //-- Misc
    public String getMimeType (String path)
    {
        return MimeUtil.getInstance ().getMimeTypeByFile (path);
    }

    public long nextUID ()
    {
        return ++_uid;
    }

    //-- Localization method
    public List getLocales ()
    {
        List lst = Arrays.asList (Locale.getAvailableLocales ());
        Comparator c = new Comparator ()
        {
            public int compare (Object o1, Object o2)
            {
                return o1.toString ().compareTo (o2.toString ());
            }
        };
        Collections.sort (lst, c);
        return lst;
    }

    //-- Collection methods
    public List shuffle (List lst)
    {
        Collections.shuffle (lst);
        return lst;
    }
   
    public List randomFilter (Collection lst, int max)
    {
        /* Random indexes */
        int xmax = Math.min (max, lst.size ());
        List<Integer> idx = new ArrayList<Integer> ();
        while ( idx.size () < xmax )
        {
            double i = Math.random () * xmax;
            int ii = ( int ) i;
            if ( !idx.contains (ii) )
            {
                idx.add (ii);
            }
        }

        if ( lst instanceof List )
        {
            return randomFilter (( List ) lst, idx);
        }
        else
        {
            List xlst = new ArrayList ();
            xlst.addAll (lst);
            return randomFilter (xlst, idx);
        }
    }

    private List randomFilter (List lst, List<Integer> idx)
    {
        List result = new ArrayList ();
        for ( Integer i: idx )
        {
            result.add (lst.get (i));
        }
        return result;

    }
    
    
    //-- Other 
    public String toString(Throwable e)
    {
        if (e == null)
        {
            return null;
        }
        
        StringBuilder sb = new StringBuilder ();
        toString (e, sb, 0);
        
        return sb.toString ();
    }    
    
    private void toString(Throwable e, StringBuilder sb, int depth)
    {
        if (depth > 2 || e == null)
        {
            return;
        }
        
        Writer writer = new StringWriter ();
        try
        {
            PrintWriter w = new PrintWriter (writer, true);
            try
            {
                e.printStackTrace (w);
                sb.append (writer.toString ());
                
                Throwable cause = e.getCause ();
                if (cause != null)
                {
                    sb.append ("\n\nCause: ");
                    toString (cause, sb, ++depth);
                }
            }
            finally
            {
                w.close ();
            }
        }
        finally
        {
            try
            {
                writer.close ();
            }
            catch (IOException ex)
            {
            }
        }        
    }
}
