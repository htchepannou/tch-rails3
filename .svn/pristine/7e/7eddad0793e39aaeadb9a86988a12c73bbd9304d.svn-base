/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mongodb;

import com.google.code.morphia.annotations.Id;
import com.tchepannou.rails.core.api.ActiveRecord;
import com.tchepannou.rails.core.bean.BeanResolver;
import com.tchepannou.util.DateUtil;
import com.tchepannou.util.StringUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import org.bson.types.ObjectId;

/**
 *
 * @author herve
 */
public class BeanMapperTest
    extends TestCase
    implements BeanResolver
{
    private BeanMapper _mapper;

    public BeanMapperTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _mapper= new BeanMapper ();
        _mapper.setBeanResolver (this);
    }


    public void testPopulate ()
        throws Exception
    {
        Map map = new HashMap ();
        ObjectId id = new ObjectId (new Date ());
        map.put ("id", id);
        map.put ("name", "Herve");
        map.put ("birthDate", "1973-12-27");
        map.put ("age", 30);
        map.put("creationDate", 100);

        Component r = new Component ();
        _mapper.populate (r, map);

        assertNull("id", r.id);
        assertEquals ("name", "Herve", r.name);
        assertEquals ("age", new Integer(30), r.age);
        assertEquals ("creationDate", 100l, r.creationDate);
        assertEquals ("birthDate", DateUtil.createDate (1973, 11, 27), r.birthDate);
    }
    public void testPopulateWithModel ()
        throws Exception
    {
        Map map = new HashMap ();
        ObjectId id = new ObjectId (new Date ());
        map.put ("id", id);
        map.put ("name", "Herve");
        map.put ("componentId", id.toString ());

        Composite c = new Composite ();
        _mapper.populate (c, map);
        
        assertNull("id", c.id);
        assertEquals ("name", "Herve", c.name);
        assertNotNull ("component", c.component);
        assertEquals ("component.id", id, c.component.id);
    }
    public void testPopulateWithModelNullID ()
        throws Exception
    {
        Map map = new HashMap ();
        ObjectId id = new ObjectId (new Date ());
        map.put ("id", id);
        map.put ("name", "Herve");
        map.put ("componentId", null);

        Composite c = new Composite ();
        _mapper.populate (c, map);

        assertNull("id", c.id);
        assertEquals ("name", "Herve", c.name);
        assertNull ("component", c.component);
    }


    public void testDescribe ()
        throws Exception
    {
        Component r = new Component ();
        ObjectId id = new ObjectId (new Date ());
        r.id = id;
        r.age = new Integer(30);
        r.creationDate = System.currentTimeMillis ();
        r.birthDate = DateUtil.today ();
        Map map = _mapper.describe (r);

        assertEquals("id", r.id, map.get("id"));
        assertEquals ("name", r.name, map.get("name"));
        assertEquals ("age", r.age, map.get("age"));
        assertEquals ("creationDate", r.creationDate, map.get("creationDate"));
        assertEquals ("birthDate", r.birthDate, map.get ("birthDate"));
    }
    public void testDescribeWithModel ()
        throws Exception
    {
        Component r = new Component ();
        ObjectId id = new ObjectId (new Date ());
        r.id = id;
        r.age = new Integer(30);
        r.creationDate = System.currentTimeMillis ();
        r.birthDate = DateUtil.today ();

        Composite c = new Composite ();
        c.id = new ObjectId (new Date());
        c.name = "Herve2";
        c.component = r;

        Map map = _mapper.describe (c);
        assertEquals("id", c.id, map.get("id"));
        assertEquals ("name", c.name, map.get("name"));
        assertEquals ("component", r, map.get("component"));
        assertEquals ("componentId", r.id, map.get("componentId"));
    }

    //-- BeanResolver overrides
    public Object resolve (Object key, Class type)
    {
        if (type.equals(Component.class))
        {
            Component c = new Component ();
            c.id = toObjectId (key);
            return c;
        }
        else if (type.equals(Composite.class))
        {
            Composite c = new Composite ();
            c.id = toObjectId (key);
            return c;
        }
        return null;
    }

    private ObjectId toObjectId (Object key)
    {
        if (key instanceof ObjectId)
        {
            return (ObjectId)key;
        }
        else if (key instanceof String)
        {
            return new ObjectId ((String)key);
        }
        return null;
    }


    //-- Static
    public static class Component
        extends ActiveRecord
    {
        @Id ()
        public ObjectId id;
        public String name;
        public Date birthDate;
        public Integer age;
        public long creationDate;

        @Override
        public Serializable getId ()
        {
            return id;
        }
    }

    public static class Composite
        extends ActiveRecord
    {
        @Id ()
        public ObjectId id;
        public String name;
        public Component component;

        @Override
        public Serializable getId ()
        {
            return id;
        }
    }
}
