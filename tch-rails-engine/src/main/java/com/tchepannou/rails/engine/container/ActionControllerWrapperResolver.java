/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tchepannou.rails.engine.container;

import java.util.Map;

/**
 *
 * @author herve
 */
public interface ActionControllerWrapperResolver
{
    public ActionControllerWrapper resolve  (String path, Map<String, ActionControllerWrapper> map);
}
