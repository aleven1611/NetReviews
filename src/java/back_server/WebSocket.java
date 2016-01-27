/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package back_server;

import com.utente.example.mywebsocket.Entity_User.Message;
import com.utente.example.mywebsocket.Entity_User.Commenti;
import com.utente.example.mywebsocket.Entity_User.Utente;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;


/**
 * Echo endpoint.
 *
 * @author Pavel Bucek (pavel.bucek at oracle.com)
 */
@ServerEndpoint("/echoServer") 
public class WebSocket {
    private static Logger log = Logger.getLogger(WebSocket.class.getName());
    private static Vector<Session> sessions = new Vector<Session>();
    
    @Inject
    private GestisciRecensioni gr;
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){
        log.info(session.getId() + " has opened a connection"); 
        
        sessions.add(session);
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){
        log.info("Message from " + session.getId() + ": " + message);

    }
 
    @OnMessage
    public void onMessage(byte[] buff, Session session){
        ByteArrayInputStream bais = null;
        ObjectInputStream obj = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream objout = null;
        try {
            log.info("Message from " + session.getId() + ": nuovo messaggio");
            
            bais = new ByteArrayInputStream(buff);
            obj = new ObjectInputStream(bais);
            Message msg = (Message) obj.readObject();
            
            if(msg.getType().equals("login")){
                login(objout,baos,session,msg);
            }
            
            if(msg.getType().equals("Commento")){
                System.out.println("vedi: "+((Commenti) msg.getCorpo()).getCommento());
                gr.addCommenti((Commenti) msg.getCorpo());
            }
                        
            if(msg.getType().equals("findCommenti")){
                try {
                    findCommenti(objout,baos,session,msg);
                } catch (IOException ex) {
                    Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
                }   finally{
                    objout.close();
                    baos.close();
                }
            }
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
            
        }           
       }
   
        
    @OnClose
    public void onClose(Session session){
        log.info("Session " +session.getId()+" has ended");
        sessions.remove(session);
    }
    
    
    
    public void login(ObjectOutputStream objout,ByteArrayOutputStream baos,Session session, Message msg){
        Utente ut=(Utente) msg.getCorpo();
                log.info("l'utente:"+ut.getMail()+" sta tentando il login.");
                
                try {
                    String pass=gr.findPass(ut.getMail());
                    if(Login.validatePassword(ut.getPassword(),pass)){
                        
                       log.info("Login effettuato correttamente da: "+ut.getMail());
                       send(objout,baos,session,new Message("ValidateLogin","Login effettuato!"));
                    }
                } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
                } catch(NoResultException e){
                    send(objout,baos,session,new Message("ErrLogin","User o password errati!"));
                   
                }
    }
    
    public void findCommenti(ObjectOutputStream objout,ByteArrayOutputStream baos,Session session, Message msg) throws IOException{
        Vector<Commenti> list = (Vector<Commenti>) gr.findCommenti((long)msg.getCorpo());
               
               baos = new ByteArrayOutputStream();
               objout = new ObjectOutputStream(baos);

               objout.writeObject(list);
               System.out.println("list: "+list);
               
               ByteBuffer bb = ByteBuffer.wrap(baos.toByteArray());
               
               session.getBasicRemote().sendBinary(bb);
               
               baos.flush();
              
        }
    
    //per inviare un oggetto
    public void send(ObjectOutputStream objout,ByteArrayOutputStream baos,Session session,Message m){
        try {
            baos = new ByteArrayOutputStream();
            objout = new ObjectOutputStream(baos);
            objout.writeObject(m);
            ByteBuffer bb = ByteBuffer.wrap(baos.toByteArray());
            session.getBasicRemote().sendBinary(bb);
            baos.flush();
            
        } catch (IOException ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
