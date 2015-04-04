/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.api.Resource;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class ErrorResourceTest
    extends TestCase
{
    public ErrorResourceTest (String testName)
    {
        super (testName);
    }

    public void testOutput ()
        throws Exception
    {
        Resource r = new ErrorResource (500, "error");
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("status", 500, resp.getStatus ());
        assertEquals ("Status text", "error", resp.getStatusMessage ());
    }
}
