/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.ObjectFactory;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import java.io.File;
import junit.framework.TestCase;


/**
 *
 * @author herve
 */
public class FileResourceTest extends TestCase {
    
    public FileResourceTest(String testName) {
        super(testName);
    }

    public void testOutput ()
        throws Exception
    {
        File file = ObjectFactory.createTXTFile ("test");
        FileResource r = new FileResource (file);
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("test", resp.getContentAsString ());
    }

}
