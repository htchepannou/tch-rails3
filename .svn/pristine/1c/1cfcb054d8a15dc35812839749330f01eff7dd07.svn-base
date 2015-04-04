/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.annotation.Action;
import com.tchepannou.rails.core.resource.ErrorResource;
import com.tchepannou.rails.core.resource.FileResource;
import com.tchepannou.rails.core.resource.InputStreamResource;
import com.tchepannou.rails.core.resource.RedirectResource;
import com.tchepannou.rails.core.resource.RestoreViewResource;
import com.tchepannou.rails.core.resource.TextResource;
import com.tchepannou.rails.core.resource.ViewResource;
import com.tchepannou.rails.core.service.PersistenceService;
import com.tchepannou.rails.core.service.RenderService;
import com.tchepannou.rails.core.service.UserService;
import com.tchepannou.rails.core.util.MethodInvoker;
import com.tchepannou.util.MimeUtil;
import com.tchepannou.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * <p>
 *  <code>ActionControllers</code> are the core of web request.
 *  They are made up of actions (public methods) that are executed on  web request,
 *  then either render a view or redirect to another action.
 * </p>
 * <p>
 *  By default, each action render a view located in the <code>/view/</code> directory,
 *  corresponding to the name of the controller and action after executing code in the action.
 *  For example, the action <code>PostActionController.edit()</code> will render the view <code>/view/post/edit.vm</code>
 *  (<b>NOTE:</b> the view is defined using <a href="http://velocity.apache.org">Apache Velocity Template</a>)
 * </p>
 *
 * @author herve
 */
public class ActionController
    extends AbstractController
{
    //-- Static
    private static final String INDEX = "index";

    public static final String CLASSNAME_SUFFIX = "ActionController";
    public static final String FLASH_NOTICE = "notice";
    public static final String FLASH_ERROR = "error";
    
    public static final String ACTION_INDEX = "index";
    public static final String SESSION_USER_ID = "com.tchepannou.rails.user_id";
    public static final String SESSION_FLASH = "com.tchepannou.rails.flash";
    public static final String PARAM_REDIRECT = "redirect";
    public static final String PARAM_ID = "id";
    public static final String VAR_I18N = "i18n";
    public static final String VAR_REQUEST = "request";
    public static final String VAR_USER = "user";
    public static final String VAR_UTIL = "util";
    public static final String VAR_FLASH = "flash";


    //-- Attributes
    /**
     * When the response should expire.
     * By default, the value is <code>-1</code> which means the resource should not be cached
     */
    private long _expirySeconds = -1;

    /**
     * Last modification of the resource
     */
    private long _lastModificationDate = -1;

    /**
     * List of variables for the view.
     */
    private Map<String, Object> _viewVariables = new HashMap<String, Object> ();

    /**
     * Output resource
     */
    private Resource _resource;

    /**
     * Action context
     */
    private ActionContext _actionContext;


    /**
     * Constroller name
     */
    private String __name;

    /**
     * Headers
     */
    private Map<String, Object> _responseHeaders = new HashMap<String, Object> ();

    /**
     * Flash memory
     */
    private Map<String, List<String>> _flash = new HashMap<String, List<String>> ();




    //-- Flash Methods
    /**
     * Add a list of errors into the flash memory
     *
     * @param errors List of errors
     */
    public void addErrors (List<String> errors)
    {
        for (String error : errors)
        {
            addError (error);
        }
    }

    /**
     * Add an error into the flash memory
     *
     * @param errors List of errors
     */
    public void addError (String error)
    {
        addToFlash (FLASH_ERROR, error);

        // Rollback on error !!!
        getContext ().setRollback (true);
    }

    /**
     * Add a list of notices into the flash memory
     *
     * @param errors List of errors
     */
    public void addNotices(List<String> notices)
    {
        for (String notice : notices)
        {
            addNotice(notice);
        }
    }

    /**
     * Add a notice into the flash memory
     *
     * @param errors List of errors
     */
    public void addNotice (String notice)
    {
        addToFlash (FLASH_NOTICE, notice);
    }
    
    public Map getFlash ()
    {
        return _flash;
    }
    public void setFlash (Map<String, List<String>> flash)
    {
        _flash = flash;
    }

    public boolean hasErrors ()
    {
        return hasFlashMessage (FLASH_ERROR);
    }
    public boolean hasNotices ()
    {
        return hasFlashMessage (FLASH_NOTICE);
    }

    private void addToFlash(String type, String msg)
    {
        List<String> lst = (List<String>)_flash.get (type);
        if (lst == null)
        {
            lst = new ArrayList<String> ();
            _flash.put (type, lst);
        }
        if (!StringUtil.isEmpty (msg))
        {
            lst.add (msg);
        }
    }
    private boolean hasFlashMessage(String type)
    {
        List<String> lst = _flash.get (type);
        return lst != null && !lst.isEmpty ();
    }


    //-- Redirector methods
    /**
     * Redirect the user to the login page.
     * The login page is expected at <code>/login</code>
     *
     */
//    public void redirectToLogin (String redirectUrl)
//    {
//        ActionContext ctx = getActionContext ();
//        String redirectUrl = getRequestPathInfo (true);
//
//        String loginUrl = ctx.getContainerContext ().getLoginURL ();
//        Map<String, Object> params = new HashMap<String, Object> ();
//        params.put (PARAM_REDIRECT, redirectUrl);
//        redirectTo (loginUrl, params);
//    }

    /**
     * Returns the default Resource for this controller - in the case no resource available
     *
     * @param name  Name of the controller
     * @param action    Action
     */
    public Resource createDefaultResource (String name, String action)
        throws IOException
    {
        String path = "/" + name + "/" + action;
        int i = action.lastIndexOf (".");
        String ext = i > 0
            ? action.substring (i)
            : ".html";
        return createViewResource (path, false);
    }

    /**
     * Redirect to the page for displaying a model
     * @param model target model
     */
    protected void redirectTo (ActiveRecord model)
    {
        String url = redirectUrl (model);
        redirectTo (url);
    }

    /**
     * Redirect to a specific URL
     * @param URL target URL
     */
    protected void redirectTo (String url)
    {
        redirectTo (url, null);
    }

    /**
     * Redirect to a specific URL with parameters
     *
     * @param url Target URL
     * @param parameters Target parameters
     */
    protected void redirectTo (String url, Map parameters)
    {
        ActionContext ac = getActionContext ();
        HttpServletRequest req = ac.getRequest ();
        _resource = new RedirectResource (url, parameters, req);

        setSession (SESSION_FLASH, _flash);
    }

    protected void sendError (int status)
    {
        sendError (status, null);
    }

    /**
     * Send to the client an HTTP error
     *
     * @param status HTTP Status code
     * @param message status message
     */
    protected void sendError (int status, String message)
    {
        _resource = new ErrorResource (status, message);
    }

    protected void renderView (String path)
    {
        String mime = MimeUtil.getInstance ().getMimeTypeByFile (path);
        _resource = createViewResource (path, false);
    }

    /**
     * Restore a view with the current request stated
     *
     * @param view Name of the view to restore
     */
    protected void restoreView (String view)
    {
        _resource = createViewResource (view, true);
    }

    /**
     * Download a file to the client.
     *
     * @param file  File to download to the client
     * @param message status message
     */
    protected void renderFile (File file)
    {
        _resource = new FileResource (file);
    }

    /**
     * Download a stream to the client
     *
     * @param in data stream
     * @param contentType mime type of the stream
     */
    protected void renderStream (InputStream in, String contentType)
    {
        _resource = new InputStreamResource (in, contentType);
    }

    protected void renderText (String text)
    {
        renderText (text, MimeUtil.TEXT);
    }

    protected void renderText (String text, String contentType)
    {
        _resource = new TextResource (text, contentType);
    }

    private Resource createViewResource (String path, boolean restore)
    {
        RenderService rs = (RenderService)findService (RenderService.class);
        ContainerContext cc = getActionContext ().getContainerContext ();

        if ( restore )
        {
            addViewVariables (getRequestParameters ());
        }
        _viewVariables.put (VAR_I18N, getI18n ());
        _viewVariables.put (VAR_REQUEST, getRequest ());
        _viewVariables.put (VAR_USER, getUser ());
        _viewVariables.put (VAR_UTIL, cc.createUtil ());
        _viewVariables.put (VAR_FLASH, getFlash ());

        return restore
            ? new RestoreViewResource (path, rs, _viewVariables)
            : new ViewResource (path, rs, _viewVariables);
    }

    private String redirectUrl (ActiveRecord model)
    {
        String classname = model.getClass ().getSimpleName ();
        return "/" + classname.toLowerCase () + "/show?id=" + model.getId ();
    }


    //-- Service methods
    protected User findUser (Serializable userId)
    {
        UserService us = (UserService)findService (UserService.class);
        return us != null ? us.findUser (userId) : null;
    }



    //-- Header methods
    public String getRemoteIP ()
    {
        HttpServletRequest req = getRequest ();
        String ip = req.getHeader ("x-forwarded-for");
        if (StringUtil.isEmpty (ip))
        {
            ip = req.getRemoteAddr ();
        }
        return ip;
    }
    
    public Map<String, Object> getResponseHeaders ()
    {
        return _responseHeaders;
    }

    protected void addResponseHeader (String name, String value)
    {
        _responseHeaders.put (name, value);
    }

    protected void addResponseHeader (String name, Date value)
    {
        _responseHeaders.put (name, value);
    }

    /**
     * Ensure that the page never get cached on client computer
     */
    protected void expiresNow ()
    {
        _expirySeconds = -1;
    }

    protected void expiresIn (int seconds)
    {
        expiresIn (seconds, 0, 0, 0);
    }

    protected void expiresIn (int seconds, int minutes)
    {
        expiresIn (seconds, minutes, 0, 0);
    }

    protected void expiresIn (int seconds, int minutes, int hours)
    {
        expiresIn (seconds, minutes, hours, 0);
    }

    /**
     * Set the expiry of the page
     */
    protected void expiresIn (int seconds, int minutes, int hours, int days)
    {
        _expirySeconds = (long)seconds + (long)minutes * 60L + (long)hours * 60L * 60L + (long)days * 24L * 60L * 60L;
    }


    //-- SessionMethods
    /**
     * Returns a session object by it's name or <code>null</code> if not found
     *
     * @param name Name of the requested object
     */
    public final Object getSession (String name)
    {
        HttpSession session = getActionContext ().getRequest ().getSession (false);
        return session != null
            ? session.getAttribute (name)
            : null;
    }
    
    /**
     * Set an object into the session
     *
     * @param name name of the object
     * @param value value - if <Code>null</code>, the object will be removed from the session
     */
    public void setSession (String name, Object value)
    {
        HttpSession session = getActionContext ().getRequest ().getSession ();
        session.setAttribute (name, value);
    }

    /**
     * Remove an object from the session
     */
    public void removeSession (String name)
    {
        HttpSession session = getActionContext ().getRequest ().getSession ();
        session.removeAttribute (name);
    }

    /**
     * Invalidate the current session
     */
    public void resetSession ()
    {
        HttpSession session = getActionContext ().getRequest ().getSession ();
        session.invalidate ();
    }

    /**
     * Return the current user
     */
    public User getUser ()
    {
        Serializable id = ( Serializable ) getSession (SESSION_USER_ID);
        if ( id == null )
        {
            return null;
        }
        else
        {
            return findUser (id);
        }
    }

    /**
     * Set the user of the current session
     *
     * @param user  User to set - if <code>null</code>, the user will be reset
     */
    public void setUser (User user)
    {
        if ( user == null )
        {
            setSession (SESSION_USER_ID, null);
        }
        else
        {
            Serializable id = user.getId ();
            setSession (SESSION_USER_ID, id);
        }
    }

    /**
     * Set the user of the current session
     *
     * @param userId  ID of the user to set
     */
    public void setUserId (Serializable userId)
    {
        User user = findUser (userId);
        setUser (user);
    }



    //-- ViewVariable methods
    /**
     * Add a view variable
     *
     * @param name  Name of the variable
     * @param value Value of the variable
     */
    public void addViewVariable (String name, Object value)
    {
        _viewVariables.put (name, value);
    }

    /**
     * Add a set of view variables
     */
    public void addViewVariables (Map<String, Object> vars)
    {
        for (String name : vars.keySet ())
        {
            addViewVariable (name, vars.get(name));
        }
    }


    //-- Request Parameteres
    public List<FilePart> getRequestFiles ()
    {
        return _actionContext.getRequestFiles ();
    }
    public FilePart getRequestFile ()
    {
        List<FilePart> files = getRequestFiles ();
        return !files.isEmpty () ? files.get(0) : null;
    }

    public Map<String, Object> getRequestParameters ()
    {
        return _actionContext.getRequestParameters ();
    }

    public final String getRequestParameter (String name)
    {
        Map params = getRequestParameters ();
        return getRequestParameter (params, name);
    }

    public final String getRequestParameter (Map params, String name)
    {
        if ( params == null )
        {
            return null;
        }
        else
        {
            Object obj = params.get (name);
            if ( obj instanceof String )
            {
                return ( String ) obj;
            }
            else if ( obj instanceof String[] )
            {
                return (( String[] ) obj)[0];
            }
            else
            {
                return obj != null
                    ? obj.toString ()
                    : null;
            }
        }
    }

    public final String[] getRequestParameterValues (String name)
    {
        Map params = getRequestParameters ();
        return getRequestParameterValues (params, name);
    }

    public final String[] getRequestParameterValues (Map params, String name)
    {
        Object obj = params.get (name);
        if ( obj instanceof String )
        {
            return new String[]
                {
                    ( String ) obj
                };
        }
        else if ( obj instanceof String[] )
        {
            return ( String[] ) obj;
        }
        else if (obj == null)
        {
            return null;
        }
        else
        {
            Class type = obj.getClass ();
            if (type.isArray ())
            {
                Object[] objs = (Object[])obj;
                String[] array = new String[objs.length];
                for (int i=0 ; i<array.length ; i++)
                {
                    Object o = objs[i].toString ();
                    array[i] = o != null ? o.toString () : null;
                }
                return array;
            }
            else
            {
                return new String[] {obj.toString ()};
            }
        }
    }

    public final long getRequestParameterAsLong (String name, long defaultValue)
    {
        String value = getRequestParameter (name);
        return StringUtil.toLong (value, defaultValue);
    }

    public final ActiveRecord getRequestParameterAsActiveRecord (String name, Class<? extends ActiveRecord> type)
    {
        PersistenceService ps = (PersistenceService)findService (PersistenceService.class);
        if (ps == null)
        {
            return null;
        }
        
        long id = getRequestParameterAsLong (name, 0);
        return id > 0 ? ps.get (id, type) : null;
    }

    public final Enum getRequestParameterAsEnum (String name, Class<? extends Enum> type)
    {
        String value = getRequestParameter (name);        
        try
        {
            return value != null
                ? Enum.valueOf (type, value)
                : null;
        }
        catch (IllegalArgumentException e)
        {
            return null;
        }
    }

    public final int getRequestParameterAsInt (String name, int defaultValue)
    {
        String value = getRequestParameter (name);
        return StringUtil.toInt (value, defaultValue);
    }

    public final double getRequestParameterAsDouble (String name, double defaultValue)
    {
        String value = getRequestParameter (name);
        return StringUtil.toDouble (value, defaultValue);
    }

    public final Date getRequestParameterAsDate (String name, String format)
    {
        Object value = getRequestParameters ().get (name);
        if (value instanceof String)
        {
            try
            {
                return new SimpleDateFormat (format).parse (value.toString ());
            }
            catch (ParseException e)
            {
                return null;
            }
        }
        else if (value instanceof Date)
        {
            return (Date)value;
        }
        return null;
    }

    public final float getRequestParameterAsFloat (String name, float defaultValue)
    {
        String value = getRequestParameter (name);
        return StringUtil.toFloat (value, defaultValue);
    }

    //-- Method invoker methods
    /**
     * Returns the {@link MethodInvoker} associate with a given path 
     * or  <code>null</code> if the path is invalid
     * 
     * @param path
     */
    public MethodInvoker getMethodInvoker (String path)
    {
        String view = getView (path);
        int i = view.indexOf ('.');
        String methodName = i > 0 ? view.substring (0, i) : view;        
        Action action = getClass ().getAnnotation (Action.class);
        Class<? extends ActiveRecord> model = action != null ? action.modelClass () : null;
        
        if (model == null || ActiveRecord.class.equals (model) )
        {
            return new MethodInvoker (methodName, this);
        }
        else
        {
            String[] ids = getRequestParameterValues (PARAM_ID);
            Object[] records = findActiveRecord (ids);
            if (allFound (ids, records))
            {
                return new MethodInvoker (methodName, this, records);
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Returns the name of the vide associate with a given path
     */
    public String getView (String path)
    {
        String xpath = path.startsWith ("/") ? path.substring (1) : path;
        int i = xpath.lastIndexOf ("/");
        return i > 0 ? xpath.substring (i + 1) : INDEX;
    }

    private Object[] findActiveRecord (String[] ids)
    {
        if (ids == null || ids.length == 0)
        {
            return null;
        }
        else
        {
            Action action = getClass ().getAnnotation (Action.class);
            Class<? extends ActiveRecord> clazz = action != null ? action.modelClass () : null;
            if (clazz == null || ActiveRecord.class.equals(clazz))
            {
                return null;
            }
            else
            {
                PersistenceService ps = (PersistenceService)findService (PersistenceService.class);
                if (ps == null)
                {
                    return null;
                }

                Object[] objs = (Object[])Array.newInstance (clazz, ids.length);
                for (int i=0 ; i<ids.length ; i++)
                {
                    long id = StringUtil.toLong (ids[i], 0);
                    ActiveRecord ac = ps.get (id, clazz);
                    objs[i] = ac;
                }
                return objs;
            }
        }
    }

    private boolean allFound (String[] ids, Object[] records)
    {
        if (ids == null ||  ids.length == 0)
        {
            return true;
        }
        else if (records == null || records.length != ids.length)
        {
            return false;
        }
        else
        {
            for (Object record : records)
            {
                if (record == null)
                {
                    return false;
                }
            }
            return true;
        }
    }



    //-- Other methods
//    protected String getRequestPathInfo (boolean includeQueryString)
//    {
//        ActionContext ctx = getActionContext ();
//        HttpServletRequest req = ctx.getRequest ();
//        String url = req.getPathInfo ();
//
//        if ( includeQueryString )
//        {
//            StringBuilder qs = new StringBuilder ();
//            Map params = getRequestParameters ();
//            for ( Object name: params.keySet () )
//            {
//                String[] values = getRequestParameterValues (name.toString ());
//                if ( values != null )
//                {
//                    for ( int i = 0; i < values.length; i++ )
//                    {
//                        String value = values[i];
//                        if ( value != null )
//                        {
//                            if ( qs.length () > 0 )
//                            {
//                                qs.append ('&');
//                            }
//                            qs.append (name).append ('=').append (value);
//                        }
//                    }
//                }
//            }
//            if ( qs.length () > 0 )
//            {
//                url = url + "?" + qs.toString ();
//            }
//        }
//        return url;
//    }
//

    //-- Getter/Setter
    public long getLastModificationDate ()
    {
        return _lastModificationDate;
    }

    public void setLastModificationDate (long lastModificationDate)
    {
        this._lastModificationDate = lastModificationDate;
    }

    public long getExpirySeconds ()
    {
        return _expirySeconds;
    }

    public Map<String, Object> getViewVariables ()
    {
        return _viewVariables;
    }

    public Object getViewVariable(String name)
    {
        return _viewVariables.get (name);
    }

    public Resource getResource ()
    {
        return _resource;
    }

    public ActionContext getActionContext ()
    {
        return _actionContext;
    }

    public Context getContext ()
    {
        return getActionContext ();
    }


    public void setActionContext (ActionContext context)
    {
        this._actionContext = context;
    }

    public HttpServletRequest getRequest ()
    {
        return getActionContext ().getRequest ();
    }

    public HttpServletResponse getResponse ()
    {
        return getActionContext ().getResponse ();
    }

    public I18n getI18n ()
    {
        return I18nThreadLocal.get (getLocale ());
    }

    public Locale getLocale ()
    {
        return getRequest ().getLocale ();
    }

    /**
     * Returns the name of the controller
     */
    public final String getName ()
    {
        if ( __name == null )
        {
            __name = getName (getClass ());
        }
        return __name;
    }

    /**
     * Returns the computed name of a controller
     */
    public static String getName (Class<? extends ActionController> clazz)
    {
        Action annotation = clazz.getAnnotation (Action.class);
        if (annotation != null)
        {
            String name = annotation.name ();
            if (!StringUtil.isEmpty (name))
            {
                return name;
            }
        }

        String fqcn = clazz.getName ();
        int i = fqcn.lastIndexOf ('.');
        String name = i > 0
            ? fqcn.substring (i + 1)
            : fqcn;

        String xname = name.endsWith (CLASSNAME_SUFFIX)
            ? name.substring (0, name.length () - CLASSNAME_SUFFIX.length ())
            : name;
        return xname.substring (0, 1).toLowerCase () + xname.substring (1);
    }
}
