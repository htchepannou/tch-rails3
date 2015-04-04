/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.service.impl;

import com.tchepannou.rails.core.impl.PropertiesOptionService;
import com.tchepannou.rails.core.api.ServiceContext;

/**
 *
 * @author herve
 */
public class PropertiesOptionServiceTest
    extends AbstractServiceTest
{
    private PropertiesOptionService _service;

    public PropertiesOptionServiceTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();
        
        _service = new PropertiesOptionService ();
    }

    public void testInit ()
    {
        ServiceContext sc = createServiceContext ();
        _service.init (sc);

        assertTrue ("initialized", _service.isInitialized ());
        assertFalse("names", _service.getNames ().isEmpty ());
        assertEquals ("property1", "value1", _service.get ("property1", null));
        assertEquals ("property2", "value2", _service.get ("property2", null));
        assertNull ("xx", _service.get ("xx", null));
    }

    public void testDestroy ()
    {
        ServiceContext sc = createServiceContext ();
        _service.init (sc);
        _service.destroy ();
        
        assertFalse ("initialized", _service.isInitialized ());
        assertTrue ("names", _service.getNames ().isEmpty ());
        assertNull ("property1", _service.get ("property1", null));
        assertNull ("property2", _service.get ("property2", null));
    }
}
