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

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
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
import java.util.Properties;
import java.util.Timer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Timeout;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
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
     * @OnOpen allows us to intercept the creation of a new session. The session
     * class allows us to send data to the user. In the method onOpen, we'll let
     * the user know that the handshake was successful.
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info(session.getId() + " has opened a connection");
        sessions.add(session);       
    }

    /**
     * When a user sends a message to the server, this method will intercept the
     * message and allow us to react to it. For now the message is read as a
     * String.
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("Message from " + session.getId() + ": " + message);

    }
    

    @OnMessage
    public void onMessage(byte[] buff, Session session) {
        ByteArrayInputStream bais = null;
        ObjectInputStream obj = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream objout = null;
        try {
            log.info("Message from " + session.getId() + ": nuovo messaggio");

            bais = new ByteArrayInputStream(buff);
            obj = new ObjectInputStream(bais);
            Message msg = (Message) obj.readObject();

            if (msg.getType().equals("Login")) {
                login(objout, baos, session, msg);
            }

            if (msg.getType().equals("Registrazione")) {
                registrazione(objout, baos, session, msg);
            }

            if(msg.getType().equals("ReimpostaPass")){
                log.info("l'utente ha richiesto di reimpostare la password");
                RandomString rand=new RandomString(7);
                
                //testo dell'email
                String text="Salve, il codice seguente servirà per procedere col modulo di reimposta password."
                        + " Scrivere questo codice nella sezione reimposta password: "+rand.nextString();
                sendEmail(objout, baos, session, msg,text,"Reimposta Password");
            }
            
            if (msg.getType().equals("Commento")) {
                System.out.println("vedi: " + ((Commenti) msg.getCorpo()).getCommento());
                gr.addCommenti((Commenti) msg.getCorpo());
            }

            if (msg.getType().equals("findCommenti")) {
                try {
                    findCommenti(objout, baos, session, msg);
                } catch (IOException ex) {
                    Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    objout.close();
                    baos.close();
                }
            }

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    @OnClose
    public void onClose(Session session) {
        log.info("Session " + session.getId() + " has ended");
        sessions.remove(session);
    }

    public void login(ObjectOutputStream objout, ByteArrayOutputStream baos, Session session, Message msg) {
        Utente ut = (Utente) msg.getCorpo();
        log.info("l'utente:" + ut.getMail() + " sta tentando il login.");
        try {
            String pass = gr.findPass(ut.getMail());
            if (Login.validatePassword(ut.getPassword(), pass)) {
                log.info("Login effettuato correttamente da: " + ut.getMail());
                send(objout, baos, session, new Message("ValidateLogin", "Login effettuato!"));
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoResultException e) {
            log.severe("User o pass errati!");
            send(objout, baos, session, new Message("ErrLogin", "User o password errati!"));

        }
    }

    private void registrazione(ObjectOutputStream objout, ByteArrayOutputStream baos, Session session, Message msg) {
        log.info("Un utente sta tentando la registrazione");
        Utente ut = (Utente) msg.getCorpo();

        if (gr.insertUt(ut)) {
            log.info("OK, email non presente nel DB utente correttamente registrato");
            send(objout, baos, session, new Message("RegOk", "Email corretta! Utente registrato"));
            RandomString rand=new RandomString(7);
                String text="Salve, il codice seguente servirà per procedere per confermare l'email."
                        + " Scrivere questo codice nella sezione apposita: "+rand.nextString();
              
            sendEmail(objout, baos, session, msg,text,"Registrazione");
        } else {
            log.severe("Attenzione email già presente nel DB!");
            send(objout, baos, session, new Message("RegErr", "Email già presente nel DB!"));
        }

    }

    public void findCommenti(ObjectOutputStream objout, ByteArrayOutputStream baos, Session session, Message msg) throws IOException {
        Vector<Commenti> list = (Vector<Commenti>) gr.findCommenti((long) msg.getCorpo());

        baos = new ByteArrayOutputStream();
        objout = new ObjectOutputStream(baos);
        objout.writeObject(list);
        System.out.println("list: " + list);

        ByteBuffer bb = ByteBuffer.wrap(baos.toByteArray());
        session.getBasicRemote().sendBinary(bb);

        baos.flush();

    }

    //per inviare un messaggio m su di una sessione
    public void send(ObjectOutputStream objout, ByteArrayOutputStream baos, Session session, Message m) {
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

    //questo metodo viene invocato sia nella fase di registrazione per inviare il codice per convalidare l'email
    //sia per procedere con il modulo reimposta password
    private void sendEmail(ObjectOutputStream objout, ByteArrayOutputStream baos, Session ses, Message msg, String text,String subject) {
        final String username = "pieroscassacazz@gmail.com";
        final String password = "provapiero93";

        Utente ut = (Utente) msg.getCorpo();
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        javax.mail.Session session = javax.mail.Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });

        try {

            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("pieroscassacazz@gmail.com"));
            message.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse(ut.getMail()));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            log.info("email inviata correttamente!");
            
            //controllo qui il codice a che cosa dovrà servire: reimposta pass o registrazione
            if(subject.equalsIgnoreCase("Reimposta Password"))
                send(objout, baos, ses, new Message("OKRePass", "Email con codice inviata!"));
            else
                send(objout, baos, ses, new Message("OKEmailReg", "Email con codice inviata!"));

        } catch (MessagingException e) {
            log.severe("Attenzione! errore durante intoltro email... ");
            if(subject.equalsIgnoreCase("Reimposta Password"))
                send(objout, baos, ses, new Message("ErrRePass", "Errore intoltro email con codice!"));
            else
                send(objout, baos, ses, new Message("ErrEmailReg", "Errore intoltro email con codice!"));
        }
    }
    
}   
