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
    
    private Utente u1,u2;
    private Review r1,r2;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PostConstruct
    public void inizio(){
        u1 = new Utente("adjkasd.asdjsai","mpav1611");
        u2 = new Utente("asdkasjd.sdjj","ciao33");
        
        r1 = new Ristorante("Vai mo'","risto bell","Via Napoli","328256");
        r2 = new Ristorante("Taverna 51","risto cazz","Via Atzori","222654");
        
        em.persist(u1);
        em.persist(u2);
        em.persist(r1);
        em.persist(r2);
    }
    
    @PreDestroy
    public void elimina(){
        em.remove(u1);
        em.remove(u2);
        em.remove(r1);
        em.remove(r2);
    }
}
