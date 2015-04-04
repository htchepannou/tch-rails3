/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.impl;

import com.tchepannou.rails.engine.container.ActionControllerWrapperResolver;
import com.tchepannou.rails.engine.container.ActionControllerWrapper;
import com.tchepannou.util.StringUtil;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author herve
 */
public class DefaultActionControllerWrapperResolver
    implements ActionControllerWrapperResolver
{
    private static final Logger LOG = LoggerFactory.getLogger (DefaultActionControllerWrapperResolver.class);
    
    
    public ActionControllerWrapper resolve (String path, Map<String, ActionControllerWrapper> map)
    {
        String xpath = path != null ? path.toLowerCase () : "";
        if (StringUtil.isEmpty (path))
        {
            xpath = "/home";
        }
        else if (path.startsWith ("/static/") )
        {
            xpath = "/static";
        }
        else if (path.startsWith ("/asset/") )
        {
            xpath = "/asset";
        }

        String name;
        int i = xpath.lastIndexOf ("/");
        if ( i == 0 )
        {
            name = xpath.substring (1);
        }
        else
        {
            String xuri = xpath.substring (0, i);
            int j = xuri.lastIndexOf ("/");
            name = j == 0
                ? xuri.substring (1)
                : xuri.substring (j + 1);
        }
        return map.get (name);
    }
    
}
