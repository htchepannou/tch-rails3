/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class TextResourceTest extends TestCase {
    
    public TextResourceTest(String testName) {
        super(testName);
    }

    public void testOutput ()
        throws Exception
    {
        TextResource r = new TextResource ("test", "text/plain");
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("test", resp.getContentAsString ());
    }

}
