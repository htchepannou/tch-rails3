/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.util;

import com.tchepannou.rails.core.api.Util;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author herve
 */
public class UtilTest extends TestCase {
    
    public UtilTest(String testName) {
        super(testName);
    }

    public void testRandomFilter ()
    {
        List<Integer> lst = new ArrayList<Integer> ();
        for (int i=0 ; i<100 ; i++)
        {
            lst.add (i);
        }

        List<Integer> result = new Util ().randomFilter (lst, 20);
        assertEquals ("size", 20, result.size ());
        assertEquals ("duplate numbers", result.size (), new HashSet<Integer>(result).size ());
    }


    public void testRandomFilterSmall ()
    {
        List<Integer> lst = new ArrayList<Integer> ();
        for (int i=0 ; i<5 ; i++)
        {
            lst.add (i);
        }

        List<Integer> result = new Util ().randomFilter (lst, 20);
        assertEquals ("size", 5, result.size ());
        assertEquals ("duplate numbers", result.size (), new HashSet<Integer>(result).size ());
    }
}
