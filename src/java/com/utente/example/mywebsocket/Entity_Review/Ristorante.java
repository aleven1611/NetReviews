/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utente.example.mywebsocket.Entity_Review;

import java.io.Serializable;
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
public class Ristorante extends Review implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String indirizzo;
    private String cell;
    
    public Ristorante(){
        super();
    }
    
    public Ristorante(String nome, String descrizione, String indirizzo, String cell){
        super(nome, 0, descrizione);
        
        this.indirizzo = indirizzo;
        this.cell = cell;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "Ristorante{" + super.toString() + ", indirizzo=" + indirizzo + ", cell=" + cell + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.indirizzo != null ? this.indirizzo.hashCode() : 0);
        hash = 79 * hash + (this.cell != null ? this.cell.hashCode() : 0);
        
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
        
        final Ristorante other = (Ristorante) obj;
       
        if ((this.indirizzo == null) ? (other.indirizzo != null) : !this.indirizzo.equals(other.indirizzo)) {
            return false;
        }
        if ((this.cell == null) ? (other.cell != null) : !this.cell.equals(other.cell)) {
            return false;
        }
        return true;
    }


    
    
    
    
    
}
