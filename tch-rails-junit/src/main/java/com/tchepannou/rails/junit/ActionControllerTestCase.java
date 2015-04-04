/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.junit;

import com.tchepannou.rails.core.api.ActionContext;
import com.tchepannou.rails.core.api.ActionController;
import com.tchepannou.rails.core.api.ContainerContext;
import com.tchepannou.rails.core.api.Controller;
import com.tchepannou.rails.core.api.FilePart;
import com.tchepannou.rails.core.api.Interceptor;
import com.tchepannou.rails.core.api.User;
import com.tchepannou.rails.mock.servlet.MockHttpServletRequest;
import com.tchepannou.rails.mock.servlet.MockHttpServletResponse;
import com.tchepannou.util.IOUtil;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author herve
 */
public class ActionControllerTestCase 
    extends ControllerTestCase
{
    private MockHttpServletRequest _request;
    private MockHttpServletResponse _response;
    private Collection<File> _files;


    public ActionControllerTestCase()
    {

    }
    public ActionControllerTestCase(String name)
    {
        super (name);
    }

    //-- TestCase override
    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();
        
        _request = new MockHttpServletRequest ();
        _response = new MockHttpServletResponse ();
        _files = null;

        getEngine ().getActionInterceptors ().add (new FilePartInterceptor ());
    }


    //-- Public
    public ActionController service (String path)
        throws IOException
    {
        return service(path, new HashMap ());
    }
    public ActionController service (String path, Map params)
        throws IOException
    {
        return service(path, params, (Collection)null);
    }
    public ActionController service (String path, Map params, File file)
        throws IOException
    {
        return service (path, params, Collections.singletonList (file));
    }
    public ActionController service (String path, Map params, Collection<File> files)
        throws IOException
    {
        _request.setParameters (params);
        _request.setPathInfo (path);
        _files = files;
        return getEngine ().getActionContainer ().service (_request, _response);
    }


    //-- Protected methods
    protected void assertErrors(ActionController ctl)
    {
        assertTrue ("No Error in the controller: ", ctl.hasErrors ());
    }
    protected void assertNoErrors(ActionController ctl)
    {
        assertFalse ("Errors in the controller: " + ctl.getFlash ().get (ActionController.FLASH_ERROR), ctl.hasErrors ());
    }
    protected void assertResponseStatusEquals(int expected)
    {
        assertEquals ("Invalid Status code", expected, _response.getStatus ());
    }
    protected void assertResponseStatusOK()
    {
        assertResponseStatusEquals (200);
    }

    protected void login (User user)
    {
        _request.getSession (true).setAttribute (ActionController.SESSION_USER_ID, user.getId ());
    }

    protected MockHttpServletRequest getRequest ()
    {
        return _request;
    }

    protected MockHttpServletResponse getResponse ()
    {
        return _response;
    }

    protected FilePart createFilePart (final File file)
    {
        return new FilePart () {

            public String getName ()
            {
                return file.getName ();
            }

            public long getSize ()
            {
                return file.length ();
            }

            public InputStream getInputStream ()
                throws IOException
            {
                byte[] bytes = getBytes (file);
                return new ByteArrayInputStream (bytes);
            }
        };
    }

    protected byte[] getBytes(File file)
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream ();
        IOUtil.copy (file, out);
        return out.toByteArray ();
    }


    //-- Inner class
    /**
     *
     */
    public class FilePartInterceptor
        implements Interceptor
    {

        public int before (Controller controller)
        {
            return CONTINUE;
        }

        public void after (Controller controller)
        {
        }

        public int before (Method method, Controller controller)
        {
            if (controller instanceof ActionController && _files != null)
            {
                final ActionContext delegate = (ActionContext)controller.getContext ();
                ActionContext ctx = new ActionContext () {

                    public HttpServletRequest getRequest ()
                    {
                        return delegate.getRequest ();
                    }

                    public HttpServletResponse getResponse ()
                    {
                        return delegate.getResponse ();
                    }

                    public Map getRequestParameters ()
                    {
                        return delegate.getRequestParameters ();
                    }

                    public List<FilePart> getRequestFiles ()
                    {
                        ArrayList<FilePart> parts = new ArrayList<FilePart> ();
                        for (File f : _files)
                        {
                            parts.add (createFilePart (f));
                        }
                        return parts;
                    }

                    public ContainerContext getContainerContext ()
                    {
                        return delegate.getContainerContext ();
                    }

                    public void setRollback (boolean rollback)
                    {
                        delegate.setRollback (rollback);
                    }

                    public boolean isRollback ()
                    {
                        return delegate.isRollback ();
                    }
                };
                ((ActionController)controller).setActionContext (ctx);
            }
            return CONTINUE;
        }

        public void after (Method method, Controller controller, Throwable e)
        {
        }
    }
}
