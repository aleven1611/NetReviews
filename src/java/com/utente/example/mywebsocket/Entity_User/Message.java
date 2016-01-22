/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utente.example.mywebsocket.Entity_User;

import java.io.Serializable;

/**
 *
 * @author UTENTE
 */
public class Message implements Serializable {
    
    private String type;
    private Object corpo;
    
    public Message(){
        
    }

    public Message(String type, Object corpo) {
        this.type = type;
        this.corpo = corpo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getCorpo() {
        return corpo;
    }

    public void setCorpo(Object corpo) {
        this.corpo = corpo;
    }
    
    
}
