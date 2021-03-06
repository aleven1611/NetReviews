/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package back_server;

import com.utente.example.mywebsocket.Entity_Review.Review;
import com.utente.example.mywebsocket.Entity_User.Commenti;
import com.utente.example.mywebsocket.Entity_User.Utente;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author UTENTE
 */
@Stateless
public class GestisciRecensioni {
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @PersistenceContext(unitName = "NetReviewsPU")
    private EntityManager em;
    
    public List<Review> allReviews(String type){
        Query q = em.createQuery("Select r From "+type+" r");
        
        return q.getResultList();
        
    }
    
    public synchronized void addCommenti(Commenti c){
        em.persist(c);
    }
    
    public List<Commenti> findCommenti(long idUtente){
        Query q = em.createQuery("Select u From Commenti u");
        
        return q.getResultList();
    }
}
