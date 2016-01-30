/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back_server;

import com.utente.example.mywebsocket.Entity_Review.Review;
import com.utente.example.mywebsocket.Entity_Review.Ristorante;
import com.utente.example.mywebsocket.Entity_User.Utente;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author UTENTE
 */
@Startup
@Singleton
public class InitializeDB {
    
    @PersistenceContext(unitName = "NetReviewsPU")
    private EntityManager em;
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PostConstruct
    public void inizio(){
    }
    
    @PreDestroy
    public void elimina(){
    }
}
