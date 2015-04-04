/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.api;

import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author herve
 */
public interface Resource
{
    public static final String DEFAULT_ENCODING = "utf-8";
    
    public void output (HttpServletResponse response)
        throws IOException;
}
