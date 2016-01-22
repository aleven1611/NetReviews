/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utente.example.mywebsocket.Entity_User;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author UTENTE
 */
@Entity
public class Commenti implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long idUtente;
    private long idReview;
    private String commento;
    private long miPiace;

    public Commenti() {
    }

    public Commenti(long idUtente, long idReview, String commento) {
        this.idUtente = idUtente;
        this.idReview = idReview;
        this.commento = commento;
        miPiace = 0;
    }
    
    

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(long idUtente) {
        this.idUtente = idUtente;
    }

    public long getIdReview() {
        return idReview;
    }

    public void setIdReview(long idReview) {
        this.idReview = idReview;
    }

    public String getCommento() {
        return commento;
    }

  
    

    public long getMiPiace() {
        return miPiace;
    }

    public synchronized void incrementaMiPiace(){
        miPiace++;
    }
    
    public synchronized void decrementaMiPiace(){
        miPiace--;
    }

    
    
}
