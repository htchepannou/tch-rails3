/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.service.RenderService;
import com.tchepannou.rails.core.exception.RenderException;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class ViewResourceTest 
    extends TestCase
{    
    public ViewResourceTest(String testName) {
        super(testName);
    }

    public void testOutput ()
        throws Exception
    {
        RenderService rs = new RenderService () {

            public void render (String path, Map data, Writer writer)
                throws RenderException,
                       IOException
            {
                writer.write ("Hello " + data.get ("name"));
            }

            public void init (ServiceContext context)
            {
            }

            public void destroy ()
            {
            }
        };

        Map data = new HashMap ();
        data.put ("name", "Herve");

        ViewResource r = new ViewResource ("test/hello", rs, data);
        MockHttpServletResponse resp = new MockHttpServletResponse ();
        r.output (resp);

        assertEquals ("Hello Herve", resp.getContentAsString ());
    }


    //-- Controller
    public class TestActionController
        extends ActionController
    {
        public void hello ()
        {
            addViewVariable ("name", "Ray");
        }
    }
}
