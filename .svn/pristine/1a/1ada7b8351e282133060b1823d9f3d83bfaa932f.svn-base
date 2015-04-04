/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.sample.action;

import com.tchepannou.rails.core.annotation.Action;
import com.tchepannou.rails.core.annotation.RequireUser;
import com.tchepannou.rails.core.annotation.Template;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.FilePart;
import com.tchepannou.util.IOUtil;
import com.tchepannou.util.MimeUtil;
import com.tchepannou.util.StringUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.jms.JMSException;

/**
 *
 * @author herve
 */
@Template (name="default")
@Action
public class ExampleActionController
    extends ActionController
{
    public void view()
    {
        addViewVariable ("hello", "Hello World");
    }

    public void text()
    {
        renderText ("Hello World", "text/plain");
    }

    public void pdf ()
    {
        InputStream in = getClass ().getResourceAsStream ("/sample.pdf");
        renderStream (in, MimeUtil.PDF);
    }

    public void file ()
        throws IOException
    {
        /* create temporary file */
        InputStream in = getClass ().getResourceAsStream ("/sample.pdf");
        File out = File.createTempFile ("sample", ".pdf");
        IOUtil.copy (in, out);


        /* rendering the file */
        renderFile (out);
    }

    public void formSubmit ()
    {
        /* validation */
        String name = getRequestParameter ("name");
        int age = getRequestParameterAsInt ("age", -1);
        if (StringUtil.isEmpty (name))
        {
            addError ("The name field is mandatory");
        }
        if (age <= 0)
        {
            addError ("The age is invalid");
        }

        /* process */
        if (!hasErrors ())
        {
            addViewVariable ("name", name);
            addViewVariable ("age", age);
        }
        else
        {
            restoreView ("/example/form");
        }
    }

    public void uploadSubmit ()
    {
        /* validation */
        FilePart file = getRequestFile ();
        if (file == null)
        {
            addError ("The file is invalid");
        }

        /* process */
        if (!hasErrors ())
        {
            addViewVariable ("name", file.getName ());
            addViewVariable ("size", file.getSize());
        }
        else
        {
            restoreView ("/example/upload");
        }
    }

    public void jmsSubmit ()
        throws JMSException
    {
        String msg = getRequestParameter ("msg");
        sendMessage ("/hello/say", msg);

        addViewVariable ("msg", msg);
    }

    @RequireUser
    public void secure()
    {
        
    }
}
