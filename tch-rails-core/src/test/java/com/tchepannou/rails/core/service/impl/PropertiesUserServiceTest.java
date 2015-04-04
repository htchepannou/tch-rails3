/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.service.impl;

import com.tchepannou.rails.core.impl.PropertiesUserService;
import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.User;

/**
 *
 * @author herve
 */
public class PropertiesUserServiceTest
    extends AbstractServiceTest
{
    private PropertiesUserService _service;

    public PropertiesUserServiceTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _service = new PropertiesUserService ();
    }

    public void testInit ()
    {
        ServiceContext sc = createServiceContext ();
        _service.init (sc);

        assertTrue ("initialized", _service.isInitialized ());

        User user = _service.findUser (1);
        assertNotNull ("user", user);
        assertEquals ("user.name", "ray", user.getName ());
        assertEquals ("user.displayName", "Ray Sponsible", user.getDisplayName ());
        assertEquals ("user.email", "ray.sponsible@gmail.com", user.getEmail ());
        assertEquals ("user.firstName", "Ray", user.getAttribute ("firstName"));


        user = _service.findUser (111);
        assertNull ("user", user);
    }

    public void testDestroy ()
    {
        ServiceContext sc = createServiceContext ();
        _service.init (sc);
        _service.destroy ();
        
        assertFalse ("initialized", _service.isInitialized ());
    }


}
