/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import com.tchepannou.rails.core.util.MethodInvoker;
import java.io.IOException;
import java.io.Serializable;
import javax.jms.JMSException;
import javax.mail.MessagingException;

/**
 * Abstract implementation of {@link Controller}
 * @author herve
 */
public abstract class AbstractController
    implements Controller
{
    //-- Public
    public MethodInvoker getMethodInvoker (String path, Serializable data)
    {
        Object[] records = data != null ? new Object[] {data} : null;
        String methodName = getMethodName (path);
        return methodName != null
            ? new MethodInvoker (methodName, this, records)
            : null;
    }

    protected String getMethodName (String path)
    {
        /* Extract the last part of the path */
        String xpath = path.startsWith ("/") ? path.substring (1) : path;
        int i = xpath.lastIndexOf ("/");
        String name = i > 0 ? xpath.substring (i + 1) : null;

        /* remove extension */
        int j = name.indexOf (".");
        return j > 0 ? name.substring (0, j) : name;
    }

    
    //-- Protected
    /**
     * Deliver an email
     *
     * @see ContainerContext#deliver(java.lang.String, java.io.Serializable)
     */
    protected final void deliver (String action, Serializable data)
        throws IOException,
               MessagingException
    {
        getContext ().getContainerContext ().deliver (action, data);
    }

    /**
     * Send a message to a MessageController
     *
     * @see ContainerContext#sendMessage(java.lang.String, java.io.Serializable)
     */
    protected final void sendMessage (String destination)
        throws JMSException
    {
        sendMessage (destination, null);
    }

    /**
     * @see ContainerContext#sendMessage(java.lang.String, java.io.Serializable)
     */
    protected final void sendMessage (String destination, Serializable message)
        throws JMSException
    {
        getContext ().getContainerContext ().sendMessage (destination, message);
    }


    protected final Service findService (Class<? extends Service> type)
    {
        return getContext ().getContainerContext ().findService (type);
    }

}
