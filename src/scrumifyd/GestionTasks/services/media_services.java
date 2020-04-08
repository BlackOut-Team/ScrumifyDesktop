/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.services;

import scrumifyd.util.MyDbConnection;
import doryan.windowsnotificationapi.fr.Notification;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mahdi
 */
public class media_services {
    Connection conn = MyDbConnection.getInstance().getConnexion();
   
    
            public void ajout_media(String name,String path) {
               
        try {
            String req = "INSERT INTO media (name,path) VALUES ('"+name+"','"+path+"')";
            
            PreparedStatement st = conn.prepareStatement(req);
            //st.setString(1, e.getNom());
            //st.setObject(2, e.getDate());
            //st.setObject(3, e.getDuree());
            st.executeUpdate();
            try {
                Notification.sendNotification("media", "file added",TrayIcon.MessageType.INFO);
            } catch (AWTException | MalformedURLException ex) {
                Logger.getLogger(media_services.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
            }       
}
