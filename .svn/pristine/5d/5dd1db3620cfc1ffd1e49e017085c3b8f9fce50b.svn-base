/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.service.impl;

import com.tchepannou.rails.core.impl.VelocityRenderService;
import com.tchepannou.rails.core.api.ServiceContext;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author herve
 */
public class VelocityRenderServiceTest
    extends AbstractServiceTest
{
    private VelocityRenderService _service;

    public VelocityRenderServiceTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _service = new VelocityRenderService ();
    }

    public void testInit ()
    {
        ServiceContext sc = createServiceContext ();
        _service.init (sc);

        assertTrue ("initialized", _service.isInitialized ());

    }

    public void testDestroy ()
    {
        ServiceContext sc = createServiceContext ();
        _service.init (sc);
        _service.destroy ();

        assertFalse ("initialized", _service.isInitialized ());
    }

    public void testRender ()
        throws Exception
    {
        /* init */
        ServiceContext sc = createServiceContext ();
        _service.init (sc);

        /* render */
        String path = "/view/test/hello";
        StringWriter writer = new StringWriter ();
        Map data = new HashMap ();
        data.put ("name", "Ray");

        _service.render (path, data, writer);

        assertEquals ("result", "<b>Hello Ray</b>", writer.toString ());
    }


    public void testRenderTXT ()
        throws Exception
    {
        /* init */
        ServiceContext sc = createServiceContext ();
        _service.init (sc);

        /* render */
        String path = "/view/test/hello.txt";
        StringWriter writer = new StringWriter ();
        Map data = new HashMap ();
        data.put ("name", "Ray");

        _service.render (path, data, writer);

        assertEquals ("result", "Hello Ray", writer.toString ());
    }
}
