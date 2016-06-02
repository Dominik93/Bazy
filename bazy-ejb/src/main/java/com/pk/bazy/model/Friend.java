/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pk.bazy.model;

import java.util.Date;

/**
 *
 * @author Dominik
 */
public class Friend extends User{
    
    private Date since;

    public Friend(String username, String password) {
        super(username, password);
    } 
    
    public Friend(String username, Date since) {
        super(username);
        this.since = since;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }
}
