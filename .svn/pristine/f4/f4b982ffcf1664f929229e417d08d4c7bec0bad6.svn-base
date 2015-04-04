/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.mock.jms;

import com.tchepannou.util.StringUtil;
import java.util.Enumeration;
import java.util.Vector;
import javax.jms.ConnectionMetaData;
import javax.jms.JMSException;

/**
 *
 * @author herve
 */
public class MockConnectionMetaData
    implements ConnectionMetaData
{

    public String getJMSVersion ()
        throws JMSException
    {
        return "1.1";
    }

    public int getJMSMajorVersion ()
        throws JMSException
    {
        return major(getJMSVersion ());
    }

    public int getJMSMinorVersion ()
        throws JMSException
    {
        return minor(getJMSVersion ());
    }

    public String getJMSProviderName ()
        throws JMSException
    {
        return "Tchepannou Inc";
    }

    public String getProviderVersion ()
        throws JMSException
    {
        return "3.0";
    }

    public int getProviderMajorVersion ()
        throws JMSException
    {
        return major(getProviderVersion ());
    }

    public int getProviderMinorVersion ()
        throws JMSException
    {
        return minor(getProviderVersion ());
    }

    public Enumeration getJMSXPropertyNames ()
        throws JMSException
    {
        return new Vector ().elements ();
    }

    private int major (String version)
    {
        return StringUtil.toInt(version.split("\\.")[0], 0);
    }
    private int minor (String version)
    {
        return StringUtil.toInt(version.split("\\.")[1], 0);
    }

}
