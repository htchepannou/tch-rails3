/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.resource;

import com.tchepannou.rails.core.service.RenderService;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

/**
 * @author herve
 *
 * @see ActionController#renderView(java.lang.String)
 * @see ActionController#renderView(java.lang.String, java.lang.String)
 */
public class ViewResource
    extends AbstractResource
{
    //-- Attribute
    private RenderService _renderer;
    private Map _viewVariables;
    private String _path;


    //-- Constructor
    public ViewResource (String path, RenderService renderer, Map viewVariables)
    {
        _path = path;
        _renderer = renderer;
        _viewVariables = viewVariables;
    }


    //-- Resource overrides
    public void output (OutputStream out)
        throws IOException
    {
        OutputStreamWriter writer = new OutputStreamWriter (out);
        try
        {
            String path = _path;
            if (!_path.startsWith ("/"))
            {
                path = "/" + path;
            }
            path = "/view" + path;

            _renderer.render (path, _viewVariables, writer);
        }
        finally
        {
            writer.close ();
        }
    }

    //-- Getter
    public String getPath ()
    {
        return _path;
    }
}
