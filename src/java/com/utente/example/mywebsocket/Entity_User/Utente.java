/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utente.example.mywebsocket.Entity_User;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.swing.ImageIcon;

/**
 *
 * @author UTENTE
 */
@Entity
public class Utente implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    private String mail;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String interessi;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    
    
    public Utente() {
    }

    public Utente(String nome, String cognome, String indirizzo, String interessi, String password, String mail,Date data) {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.interessi = interessi;
        this.password = password;
        this.mail = mail;
        this.data=data;
    }
    
    public Utente(String mail, String password){
        this.password = password;
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "Utente{" + ", nome=" + nome + ", cognome=" + cognome + ", indirizzo=" + indirizzo + ", interessi=" + interessi + ", password=" + password + ", mail=" + mail + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utente other = (Utente) obj;
        
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.cognome == null) ? (other.cognome != null) : !this.cognome.equals(other.cognome)) {
            return false;
        }
        if ((this.indirizzo == null) ? (other.indirizzo != null) : !this.indirizzo.equals(other.indirizzo)) {
            return false;
        }
        if ((this.interessi == null) ? (other.interessi != null) : !this.interessi.equals(other.interessi)) {
            return false;
        }
      
        if ((this.password == null) ? (other.password != null) : !this.password.equals(other.password)) {
            return false;
        }
        if ((this.mail == null) ? (other.mail != null) : !this.mail.equals(other.mail)) {
            return false;
        }
        return true;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail; //mail
    }
    
    public String getNome() {
        return nome;
    }
    
    public void setData(Date d) {
        this.data = d; //mail
    }
    
    public Date getData() {
        return data;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome; //cognome
    }


    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public String getInteressi() {
        return interessi;
    }

    public void setInteressi(String interessi) {
        this.interessi = interessi;
    }  
    
}
