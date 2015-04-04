/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.util;

import com.tchepannou.rails.core.TestActiveRecord;
import com.tchepannou.rails.core.action.TestActionController;
import com.tchepannou.rails.core.api.ActiveRecord;
import java.lang.reflect.Method;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class MethodInvokerTest
    extends TestCase
{
    public MethodInvokerTest (String testName)
    {
        super (testName);
    }

    public void testIndex ()
        throws Exception
    {
        TestActionController controller = new TestActionController();
        MethodInvoker invoker = new MethodInvoker ("index", controller, null);
        Method method = invoker.getMethod ();
        assertNotNull("method not found", method);
        assertEquals("Bad method", "index", method.getName ());

        Object result = invoker.invoke ();
        assertEquals("result", "index", result);
    }

    public void testCreate ()
        throws Exception
    {
        TestActionController controller = new TestActionController();
        MethodInvoker invoker = new MethodInvoker ("create", controller, null);
        Method method = invoker.getMethod ();
        assertNotNull("method not found", method);
        assertEquals("Bad method", "create", method.getName ());

        Object result = invoker.invoke ();
        assertEquals("result", "create", result);
    }


    public void testDoUpdate ()
        throws Exception
    {
        TestActiveRecord obj = new TestActiveRecord ();
        obj.setId (1);
        
        TestActionController controller = new TestActionController();
        MethodInvoker invoker = new MethodInvoker ("doUpdate", controller, new ActiveRecord[] {obj});
        Method method = invoker.getMethod ();
        assertNotNull("method not found", method);
        assertEquals("Bad method", "doUpdate", method.getName ());

        Object result = invoker.invoke ();
        assertEquals("result", "doUpdate(1)", result);
    }


    public void testDoDelete1 ()
        throws Exception
    {
        TestActiveRecord obj = new TestActiveRecord ();
        obj.setId (1);

        TestActionController controller = new TestActionController();
        MethodInvoker invoker = new MethodInvoker ("doDelete", controller, new ActiveRecord[] {obj});
        Method method = invoker.getMethod ();
        assertNotNull("method not found", method);
        assertEquals("Bad method", "doDelete", method.getName ());

        Object result = invoker.invoke ();
        assertEquals("result", "doDelete(1)", result);
    }

    public void testDoDelete2 ()
        throws Exception
    {
        TestActiveRecord obj = new TestActiveRecord ();
        obj.setId (1);
        TestActiveRecord obj2 = new TestActiveRecord ();
        obj2.setId (2);

        TestActionController controller = new TestActionController();
        MethodInvoker invoker = new MethodInvoker ("doDelete", controller, new TestActiveRecord[] {obj, obj2});
        Method method = invoker.getMethod ();
        assertNotNull("method not found", method);
        assertEquals("Bad method", "doDelete", method.getName ());

        Object result = invoker.invoke ();
        assertEquals("result", "doDelete({1,2})", result);
    }
}
