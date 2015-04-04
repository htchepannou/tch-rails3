/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.jms.JMSException;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;

/**
 * Container related information
 * 
 * @author herve
 */
public interface ContainerContext
{
    /**
     * Returns the context of the servlet
     */
    public ServletContext getServletContext ();


    /**
     * Returns the list of {@link ActionInterceptor}
     */
    public List<Interceptor> getActionInterceptors ();

    /**
     * Returns the list of {@link JobInterceptor}
     */
    public List<Interceptor> getJobInterceptors ();

    /**
     * Return the list of {@link MessageInterceptor}
     */
    public List<Interceptor> getMessageInterceptors();

    /**
     * Returns the list of {@link MailInterceptor}
     */
    public List<Interceptor> getMailInterceptors ();

    /**
     * Finds a {@link Service} by its interface, or returns <code>null</code> if not found
     *
     * @param type  interface of the service requester
     */
    public Service findService (Class<? extends Service> type);

    /**
     * Return the base package of the controllers.
     * 
     */
    public String getBasePackage ();

    /**
     * Return the login URL
     */
    public String getLoginURL ();

    /**
     * Create a new instance of {@link Util}
     */
    public Util createUtil ();


    /**
     * Deliver an email.
     *
     * @param path Destination path. This path has the following format:
     *      <code>/&gt;controller_name>/&gt;method></code>, where:
     *      <ul>
     *          <li><code>/&gt;topic_name></code> name of the JMS Destination</li>
     *          <li><code>/&gt;method></code> is the name method that will receive the message </li>
     *      </ul>
     *      <br/>
     *      Example: <code>/test/login</code> maps to method <code>TestMessageListener.login()</code>
     *
     * @param message Message sent or <code>null</code> if no message to send
     *
     *
     * @throws MessagingException if any error when sending the email
     * @throws IOException if any error when generating the email content
     */
    public void deliver (String path, Serializable data)
        throws IOException, MessagingException;

    /**
     * Send a message to an JMS Destination. The message will be handled asynchronously
     *
     * @param path Destination path. This path has the following format:
     *      <code>/&gt;controller_name>/&gt;method></code>, where:
     *      <ul>
     *          <li><code>/&gt;topic_name></code> name of the JMS Destination</li>
     *          <li><code>/&gt;method></code> is the name method that will receive the message </li>
     *      </ul>
     *      <br/>
     *      Example: <code>/test/login</code> maps to method <code>TestMessageListener.login()</code>
     * 
     * @param message Message sent or <code>null</code> if no message to send
     *
     * @throws JMSException if any error occurs
     */
    public void sendMessage (String path, Serializable message)
        throws JMSException;
}
