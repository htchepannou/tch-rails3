/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.service;

import com.tchepannou.rails.core.api.Service;

/**
 *
 * @author herve
 */
public interface OptoutService extends Service
{
    public boolean hasOptout (String email);
}
