/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.core.util;

/**
 * Transaction isolation level
 * @author herve
 */
public enum IsolationLevel
{
        UNDEF,
        SERIALIZABLE,
        REPEATABLE_READS,
        READ_COMMITTED,
        READ_UNCOMMITTED
}
