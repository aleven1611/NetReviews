/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utente.example.mywebsocket.Entity_User;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author UTENTE
 */
@Entity
public class MiPiace implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long idUtente;
    private long idRicevente;
    private String type;
   

    public MiPiace(){
        
    }

    public MiPiace(long idUtente, long idRicevente, String type) {
        this.idUtente = idUtente;
        this.idRicevente = idRicevente;
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MiPiace other = (MiPiace) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.idUtente != other.idUtente) {
            return false;
        }
        if (this.idRicevente != other.idRicevente) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MiPiace{" + "id=" + id + ", idUtente=" + idUtente + ", idRicevente=" + idRicevente + ", type=" + type + '}';
    }
    
    

    public long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(long idUtente) {
        this.idUtente = idUtente;
    }

    public long getIdRicevente() {
        return idRicevente;
    }

    public void setIdRicevente(long idRicevente) {
        this.idRicevente = idRicevente;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   
    
}
