/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utente.example.mywebsocket.Entity_Review;

import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.swing.ImageIcon;

/**
 *
 * @author UTENTE
 */
@Entity
public class Libro extends Review {
 
    
    private String autore;
    private String trama;
    private String casaEditrice;
    
    public Libro(){
        
    }

    public Libro(String autore, String trama, String casaEditrice, String nome, double voto, String descrizione) {
        super(nome, voto, descrizione);
        this.autore = autore;
        this.trama = trama;
        this.casaEditrice = casaEditrice;
    }

    @Override
    public String toString() {
        return "Libro{" + super.toString() + ", autore=" + autore + ", trama=" + trama + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.autore != null ? this.autore.hashCode() : 0);
        hash = 23 * hash + (this.trama != null ? this.trama.hashCode() : 0);
        hash = 23 * hash + (this.casaEditrice != null ? this.casaEditrice.hashCode() : 0);
        
        hash += super.hashCode();
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        
        if(!super.equals(obj))
            return false;
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Libro other = (Libro) obj;
        if ((this.autore == null) ? (other.autore != null) : !this.autore.equals(other.autore)) {
            return false;
        }
        if ((this.trama == null) ? (other.trama != null) : !this.trama.equals(other.trama)) {
            return false;
        }
        if ((this.casaEditrice == null) ? (other.casaEditrice != null) : !this.casaEditrice.equals(other.casaEditrice)) {
            return false;
        }
        return true;
    }

    

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    public String getCasaEditrice() {
        return casaEditrice;
    }

    public void setCasaEditrice(String casaEditrice) {
        this.casaEditrice = casaEditrice;
    }
    
    
    
}
