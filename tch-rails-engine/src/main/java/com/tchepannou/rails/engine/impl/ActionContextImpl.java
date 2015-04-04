package com.tchepannou.rails.engine.impl;

import com.tchepannou.rails.core.api.ActionContext;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.FilePart;
import com.tchepannou.rails.core.service.OptionService;
import com.tchepannou.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Implementation of {@link ActionContext}
 */
public class ActionContextImpl 
    extends ContextImpl
    implements ActionContext
{
    //-- Attributes
    private HttpServletRequest _request;
    private HttpServletResponse _response;
    private Map __requestParameters;
    private List<FilePart> __requestFiles;
    
    public ActionContextImpl (ContainerContext cc, HttpServletRequest request, HttpServletResponse response)
    {
        super (cc);
        _request = request;
        _response = response;
    }

    //-- ActionContext overrides
    public HttpServletRequest getRequest ()
    {
        return _request;
    }

    public HttpServletResponse getResponse ()
    {
        return _response;
    }

    public Map getRequestParameters ()
    {
        if (__requestParameters == null)
        {
            load ();
        }
        return __requestParameters;
    }

    public List<FilePart> getRequestFiles ()
    {
        if (__requestFiles == null)
        {
            load ();
        }
        return __requestFiles;
    }


    //-- Private
    private void load ()
    {
        __requestFiles = new ArrayList<FilePart> ();
        __requestParameters = new HashMap ();
        boolean multipart = ServletFileUpload.isMultipartContent (_request);
        if ( multipart )
        {
            try
            {
                loadMultipart ();
            }
            catch (FileUploadException e)
            {
                throw new IllegalStateException ("File upload failure", e);
            }
        }
        else
        {
            loadSimple ();
        }
    }

    private void loadSimple ()
    {
        Map map = _request.getParameterMap();
        for (Iterator it = map.entrySet().iterator() ; it.hasNext () ; )
        {
            Map.Entry entry = (Map.Entry)it.next();
            Object value = entry.getValue();
            if (value instanceof String[])
            {
                String[] array = StringUtil.trim((String[])value);
                if (array.length == 1)
                {
                    value = array[0];
                }
            }
            else if (value instanceof String)
            {
                value = StringUtil.trim ((String)value);
            }
            __requestParameters.put ((String)entry.getKey(), value);
        }
    }

    private void loadMultipart ()
        throws FileUploadException
    {
        DiskFileItemFactory factory = new DiskFileItemFactory ();
        ServletFileUpload upload = new ServletFileUpload (factory);
        long fileSizeMax = 1024L * 1024L * getFileMaxsize ();
        upload.setFileSizeMax (fileSizeMax);
        List /* FileItem */ items = upload.parseRequest(_request);

        /* load parameters and files */
        for (Iterator it = items.iterator(); it.hasNext();)
        {
            FileItem item = (FileItem) it.next();
            String name = item.getFieldName ();
            if (item.isFormField())
            {
                String value = item.getString ();
                List<String> values = (List<String>)__requestParameters.get (name);
                if (values == null)
                {
                    values = new ArrayList<String> ();
                    __requestParameters.put (name, values);
                }
                values.add (value);
            }
            else
            {
                FilePartImpl file = new FilePartImpl (item);
                __requestFiles.add (file);
            }
        }

        /* convert values to string arrays */
        for (Object name : __requestParameters.keySet ())
        {
            List<String> values = (List<String>)__requestParameters.get (name);
            String[] xvalues = (String[])values.toArray (new String[] {});
            if (xvalues.length == 1)
            {
                __requestParameters.put (name, xvalues[0]);
            }
            else
            {
                __requestParameters.put (name, xvalues);
            }
        }
    }

    private long getFileMaxsize()
    {
        OptionService os = (OptionService)getContainerContext ().findService (OptionService.class);
        String value = os.get (OptionService.OPTION_ASSET_UPLOAD_MAX_SIZE, null);
        return StringUtil.toLong (value, OptionService.DEFAULT_ASSET_UPLOAD_MAX_SIZE);
    }
}
