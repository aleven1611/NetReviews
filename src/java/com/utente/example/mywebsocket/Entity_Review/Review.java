/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utente.example.mywebsocket.Entity_Review;

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
public class Review {
    
    private String nome;
    private double voto;
    private String descrizione;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    public Review(){
        
    }

    public Review(String nome, double voto, String descrizione) {
        this.nome = nome;
        this.voto = voto;
        this.descrizione = descrizione;
    }

    @Override
    public String toString() {
        return " nome=" + nome + ", voto=" + voto + ", descrizione=" + descrizione + ", id=" + id;
    }

    /**
     * @return the desrizione
     */
    public String getDescrizione() {
        return descrizione;
    }

    /**
     * @param descrizione the descrizione to set
     */
    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the voto
     */
    public double getVoto() {
        return voto;
    }

    /**
     * @param voto the voto to set
     */
    public void setVoto(double voto) {
        this.voto = voto;
    }

    

    
    
    
}
