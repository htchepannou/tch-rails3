/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.core.api;


/**
 *
 * @author herve
 */
public class MessageController
    extends AbstractController
{
    //-- Static
    public static final String CLASSNAME_SUFFIX = "MessageController";

    //-- Attribute
    private MessageContext _context;
    private transient String __name;

    //-- Controller override
    public Context getContext ()
    {
        return getMessageContext ();
    }


    //-- Public
    public void setMessageContext (MessageContext context)
    {
        _context = context;
    }

    public MessageContext getMessageContext ()
    {
        return _context;
    }


    /**
     * Returns the name of the controller
     */
    public final String getName ()
    {
        if ( __name == null )
        {
            __name = getName (getClass ());
        }
        return __name;
    }

    /**
     * Returns the computed name of a controller
     */
    public static String getName (Class<? extends MessageController> clazz)
    {
        String fqcn = clazz.getName ();
        int i = fqcn.lastIndexOf ('.');
        String name = i > 0
            ? fqcn.substring (i + 1)
            : fqcn;

        String xname = name.endsWith (CLASSNAME_SUFFIX)
            ? name.substring (0, name.length () - CLASSNAME_SUFFIX.length ())
            : name;
        return xname.substring (0, 1).toLowerCase () + xname.substring (1);
    }
}
