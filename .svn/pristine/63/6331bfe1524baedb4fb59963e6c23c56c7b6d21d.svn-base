/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import com.tchepannou.rails.core.api.ServiceContext;
import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.service.JMSService;
import com.tchepannou.rails.core.util.JMSUtil;
import com.tchepannou.rails.engine.Engine;
import com.tchepannou.rails.engine.impl.JMSData;
import com.tchepannou.rails.mock.jms.MockConnectionFactory;
import com.tchepannou.rails.mock.jms.MockDestination;
import com.tchepannou.rails.mock.servlet.MockServletContext;
import java.io.Serializable;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class MessageControllerContainerTest 
    extends TestCase
    implements JMSService
{
    private Engine _cc;
    private String _topic;
    private Serializable _data;

    public MessageControllerContainerTest (String testName)
    {
        super (testName);
    }

    @Override
    protected void setUp ()
        throws Exception
    {
        super.setUp ();

        _cc = new Engine ();
        _cc.setBasePackage ("com.tchepannou.rails");
        _cc.setServletContext (new MockServletContext ());

        _topic = null;
        _data = null;

        MockDestination.clear ();
        
        register (JMSService.class, this);
    }


    //-- Test
    public void testInit()
        throws Exception
    {
        MessageControllerContainer container = new MessageControllerContainer ();
        container.init (_cc);

        assertNotNull("/test1/handler11", container.getMessageControllerWrapper ("/test1/handler11"));
        assertNotNull("/test1/handler12", container.getMessageControllerWrapper ("/test1/handler12"));
        assertNotNull("/test2/handler21", container.getMessageControllerWrapper ("/test2/handler21"));
        assertNull("/xxx/xxx", container.getMessageControllerWrapper ("/xxx/xxx"));
    }

    public void testDestroy ()
    {
        MessageControllerContainer container = new MessageControllerContainer ();
        container.init (_cc);
        container.destroy ();

        assertNull("/test1/handler11", container.getMessageControllerWrapper ("/test1/handler11"));
        assertNull("/test1/handler12", container.getMessageControllerWrapper ("/test1/handler12"));
        assertNull("/test2/handler21", container.getMessageControllerWrapper ("/test2/handler21"));
    }

    public void testSendMessage()
        throws Exception
    {
        _cc.init (new MockServletContext ());
        MessageControllerContainer container = _cc.getMessageContainer ();
        container.sendMessage ("/test1/handler11", "DATA");

        assertEquals("topic", "test1", _topic);
        assertNotNull("data", _data);
        assertEquals ("data.destination", "/test1/handler11", ((JMSData)_data).getDestination ());
        assertEquals ("data.data", "DATA", ((JMSData)_data).getData ());
    }

    //-- Private
    private void register(Class<? extends Service> clazz, Service srv)
    {
        _cc.registerService (clazz, srv);

        ServiceContext sc = _cc.createServiceContext ();
        srv.init (sc);
    }


    //-- JMSService overrides
    public void init (ServiceContext context)
    {
    }

    public void destroy ()
    {
    }

    public ConnectionFactory getConnectionFactory ()
    {
        return new MockConnectionFactory ();
    }

    public void sendMessage (String topic, Serializable data)
        throws JMSException
    {
        _topic = topic;
        _data = data;

        ConnectionFactory cf = getConnectionFactory ();
        JMSUtil.sendMessage (topic, data, cf);
    }
}
