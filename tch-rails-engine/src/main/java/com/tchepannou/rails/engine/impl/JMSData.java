package com.tchepannou.rails.engine.impl;

import java.io.Serializable;

/**
 * JMS JMSData sent
 */
public class JMSData implements Serializable
{
    private String _destination;
    private Serializable _data;

    public JMSData (String destination, Serializable data)
    {
        _destination = destination;
        _data = data;
    }

    public String getDestination ()
    {
        return _destination;
    }

    public Serializable getData ()
    {
        return _data;
    }
}
