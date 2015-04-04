/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.api.Resource;
import com.tchepannou.rails.mock.servlet.MockHttpServletRequest;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class RedirectResourceTest
    extends TestCase
{
    public RedirectResourceTest (String testName)
    {
        super (testName);
    }

    public void testOutputRedirectToURL ()
        throws Exception
    {
        HttpServletRequest req = new MockHttpServletRequest ();
        Map params = new HashMap ();
        params.put ("keyword", "ray");
        Resource r = new RedirectResource ("http://www.google.ca", params, req);
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("http://www.google.ca?keyword=ray", resp.getRedirectUrl ());
    }

    public void testOutputRedirectToController ()
        throws Exception
    {
        HttpServletRequest req = new MockHttpServletRequest ();
        Map params = new HashMap ();
        params.put ("keyword", "ray");
        Resource r = new RedirectResource ("/test", params, req);
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);
        assertEquals (req.getContextPath () + "/test?keyword=ray", resp.getRedirectUrl ());
    }
}
