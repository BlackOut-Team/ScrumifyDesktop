 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.services;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import scrumifyd.GestionProjets.models.Project;
import static scrumifyd.GestionProjets.services.ProjectService.connexion;

public final class UserSession {

    private static UserSession instance;

    private int userId;
    private String username;
    private String avatar;


    private UserSession(int userId) {
        this.userId = userId;
    }

    public static UserSession getInstace(int userId) {
        if(instance == null) {
            instance = new UserSession(userId);
        }
        return instance;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername(int userId) {
        try {

            String req = "SELECT * FROM USER WHERE ID=" + userId + " ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                String username = result.getString("name");
                 return username;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return username;
    }
     public String getAvatar(int userId) {
        try {

            String req = "SELECT * FROM USER WHERE ID=" + userId + " ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                String avatar = result.getString("image");
                 return avatar;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return avatar;
    }

   

    public void cleanUserSession() {
        userId = 0;// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id='" + userId + "' ";
               
    }
}