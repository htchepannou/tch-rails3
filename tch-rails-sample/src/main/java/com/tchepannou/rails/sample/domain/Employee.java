/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tchepannou.rails.sample.domain;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.tchepannou.rails.core.api.ActiveRecord;
import java.io.Serializable;
import org.bson.types.ObjectId;

/**
 *
 * @author herve
 */
@Entity
public class Employee
    extends ActiveRecord
{
    @Id
    public ObjectId id;

    public String name;

    @Override
    public Serializable getId ()
    {
        return id;
    }
}
