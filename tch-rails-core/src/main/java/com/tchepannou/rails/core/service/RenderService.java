/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.Service;
import com.tchepannou.rails.core.exception.RenderException;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * This is the service for rendering templates
 * 
 * @author herve
 */
public interface RenderService
    extends Service
{
    /**
     * Render a template
     *
     * @param path Path of the template
     * @param data Data to merge with the template
     * @param writer Output writer
     *
     * @throws RenderException if an error occurs in the rendering process
     * @throws IOException if any error occurs.
     */
    public void render (String path, Map data, Writer writer)
        throws RenderException, IOException;
}
