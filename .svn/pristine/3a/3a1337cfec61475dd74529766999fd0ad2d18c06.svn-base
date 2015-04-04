/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author herve
 */
public class MockMessage
    implements Message, ObjectMessage, TextMessage
{
    //-- Attribute
    private String _id;
    private long _timestamp;
    private byte[] _correlationId;
    private Destination _replyTo;
    private Destination _destination;
    private int _deliveryMode;
    private boolean _redelivered;
    private String _type;
    private int _priority;
    private long _expiration;
    private Map _properties = new HashMap ();
    private Serializable _object;

    //-- Constructo
    public MockMessage()
    {

    }
    public MockMessage(Serializable obj)
    {
        _object = obj;
    }
    
    //-- ObjectMessage overrides
    public void setObject (Serializable object)
        throws JMSException
    {
        _object = object;
    }

    public Serializable getObject ()
        throws JMSException
    {
        return _object;
    }


    //-- TextMessage overrides
    public void setText (String string)
        throws JMSException
    {
        setObject (string);
    }

    public String getText ()
        throws JMSException
    {
        return (String)getObject ();
    }


    //-- Message override
    public String getJMSMessageID ()
        throws JMSException
    {
        return _id;
    }

    public void setJMSMessageID (String id)
        throws JMSException
    {
        _id = id;
    }

    public long getJMSTimestamp ()
        throws JMSException
    {
        return _timestamp;
    }

    public void setJMSTimestamp (long timestamp)
        throws JMSException
    {
        _timestamp = timestamp;
    }

    public byte[] getJMSCorrelationIDAsBytes ()
        throws JMSException
    {
        return _correlationId;
    }

    public void setJMSCorrelationIDAsBytes (byte[] correlationId)
        throws JMSException
    {
        _correlationId = correlationId;
    }

    public void setJMSCorrelationID (String correlationId)
        throws JMSException
    {
        _correlationId = correlationId != null ? correlationId.getBytes() : null;
    }

    public String getJMSCorrelationID ()
        throws JMSException
    {
        return new String(_correlationId);
    }

    public Destination getJMSReplyTo ()
        throws JMSException
    {
        return _replyTo;
    }

    public void setJMSReplyTo (Destination replyTo)
        throws JMSException
    {
        _replyTo = replyTo;
    }

    public Destination getJMSDestination ()
        throws JMSException
    {
        return _destination;
    }

    public void setJMSDestination (Destination destination)
        throws JMSException
    {
        _destination = destination;
    }

    public int getJMSDeliveryMode ()
        throws JMSException
    {
        return _deliveryMode;
    }

    public void setJMSDeliveryMode (int i)
        throws JMSException
    {
        _deliveryMode = i;
    }

    public boolean getJMSRedelivered ()
        throws JMSException
    {
        return _redelivered;
    }

    public void setJMSRedelivered (boolean redelivered)
        throws JMSException
    {
        _redelivered = redelivered;
    }

    public String getJMSType ()
        throws JMSException
    {
        return _type;
    }

    public void setJMSType (String type)
        throws JMSException
    {
        _type = type;
    }

    public long getJMSExpiration ()
        throws JMSException
    {
        return _expiration;
    }

    public void setJMSExpiration (long expiration)
        throws JMSException
    {
        _expiration = expiration;
    }

    public int getJMSPriority ()
        throws JMSException
    {
        return _priority;
    }

    public void setJMSPriority (int i)
        throws JMSException
    {
        _priority = i;
    }

    public void clearProperties ()
        throws JMSException
    {
        _properties.clear ();
    }

    public boolean propertyExists (String key)
        throws JMSException
    {
        return _properties.containsKey(key);
    }

    public boolean getBooleanProperty (String string)
        throws JMSException
    {
        return (Boolean)_properties.get(string);
    }

    public byte getByteProperty (String string)
        throws JMSException
    {
        return (Byte)_properties.get(string);
    }

    public short getShortProperty (String string)
        throws JMSException
    {
        return (Short)_properties.get(string);
    }

    public int getIntProperty (String string)
        throws JMSException
    {
        return (Integer)_properties.get(string);
    }

    public long getLongProperty (String string)
        throws JMSException
    {
        return (Long)_properties.get(string);
    }

    public float getFloatProperty (String string)
        throws JMSException
    {
        return (Float)_properties.get(string);
    }

    public double getDoubleProperty (String string)
        throws JMSException
    {
        return (Double)_properties.get(string);
    }

    public String getStringProperty (String string)
        throws JMSException
    {
        return (String)_properties.get(string);
    }

    public Object getObjectProperty (String string)
        throws JMSException
    {
        return (Object)_properties.get(string);
    }

    public Enumeration getPropertyNames ()
        throws JMSException
    {
        return new Vector (_properties.keySet()).elements();
    }

    public void setBooleanProperty (String string, boolean bln)
        throws JMSException
    {
        _properties.put(string, bln);
    }

    public void setByteProperty (String string, byte b)
        throws JMSException
    {
        _properties.put(string, b);
    }

    public void setShortProperty (String string, short s)
        throws JMSException
    {
        _properties.put(string, s);
    }

    public void setIntProperty (String string, int i)
        throws JMSException
    {
        _properties.put(string, i);
    }

    public void setLongProperty (String string, long l)
        throws JMSException
    {
        _properties.put(string, l);
    }

    public void setFloatProperty (String string, float f)
        throws JMSException
    {
        _properties.put(string, f);
    }

    public void setDoubleProperty (String string, double d)
        throws JMSException
    {
        _properties.put(string, d);
    }

    public void setStringProperty (String string, String string1)
        throws JMSException
    {
        _properties.put(string, string1);
    }

    public void setObjectProperty (String string, Object o)
        throws JMSException
    {
        _properties.put(string, o);
    }

    public void acknowledge ()
        throws JMSException
    {
    }

    public void clearBody ()
        throws JMSException
    {
    }
}
