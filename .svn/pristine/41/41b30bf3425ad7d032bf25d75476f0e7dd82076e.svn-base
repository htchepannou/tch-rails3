/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core;

import com.tchepannou.util.IOUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author herve
 */
public class ObjectFactory
{
//    public static Container createContainer ()
//    {
//        MockServletContext sc = new MockServletContext ();
//        sc.setContextPath ("/");
//
//        Container obj = new Container ();
//        obj.setServletContext (sc);
//        obj.registerBasePackage ("com.tchepannou.rails.container");
//
//        obj.registerActionInterceptor (new LoginInterceptor ());
//        obj.registerActionInterceptor (new DebugInterceptor ());
//
//        return obj;
//    }
//
//    public static ActionContext createActionContext (Container container)
//    {
//        ServletContext sc = container.getServletContext ();
//        MockHttpServletRequest req = new MockHttpServletRequest (sc);
//        MockHttpServletResponse resp = new MockHttpServletResponse ();
//        ContainerContext cc = container.createContainerContext ();
//
//        return new ActionContext (cc, req, resp);
//    }
//
    public static File createTXTFile (String content)
        throws IOException
    {
        File f = File.createTempFile ("file", ".txt");
        IOUtil.copy (new ByteArrayInputStream (content.getBytes ()), f);
        return f;
    }

    public static InputStream createInputStream (String content)
    {
        return new ByteArrayInputStream (content.getBytes ());
    }
}
