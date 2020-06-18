/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.models;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import scrumifyd.GestionProjets.util.NotidDecoder;
import scrumifyd.GestionProjets.util.NotifEncoder;
/**
 *
 * @author Amira Doghri
 */
@ServerEndpoint( 
  value="/push/{username}", 
  decoders = NotidDecoder.class, 
  encoders = NotifEncoder.class )

public class NotifEndPoint  {
    


private Session session;
    private static Set<NotifEndPoint> NotifEndpoint = new CopyOnWriteArraySet<>();

    private static HashMap<String, String> users = new HashMap<>();
 
  
    
}
