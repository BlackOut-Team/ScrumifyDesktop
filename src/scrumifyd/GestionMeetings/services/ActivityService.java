/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import scrumifyd.GestionMeetings.models.Activity;

import static scrumifyd.GestionMeetings.services.MeetingService.connexion;
import scrumifyd.GestionProjets.services.UserSession;
import scrumifyd.util.MyDbConnection;

/**
 *
 * @author hp
 */
public class ActivityService implements InterfaceActivity{
 static Connection connexion;


    public ActivityService() {
        connexion = MyDbConnection.getInstance().getConnexion();

    }

    @Override
    public List<Activity> getAllActivity() throws SQLException {
     List<Activity> Activity= new ArrayList<>();
        try {

            String req = "SELECT A.id , A.user_id , A.action , A.viewed  ,  U.username FROM ACTIVITY A , USER U WHERE u.id = A.user_id ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                
                Activity m = new Activity(result.getInt(1),result.getString("action"), result.getInt("viewed") , result.getInt("user_id"),   result.getString(5));
                Activity.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Activity;
        
    }
@Override
    public void supprimerActivity(int id) throws SQLException {
        String query = "DELETE FROM `Activity` WHERE id = "+id;
        Statement stm;
        try {
            stm = connexion.createStatement();
             stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     
    @Override
        public int ajouterActivity(Activity activity)throws SQLException{
            UserSession u = UserSession.getInstace(activity.getUser_id());
            int user_id =u.getUserId();
         String query = "INSERT INTO activity (action, user_id, viewed) VALUES ('"+activity.getAction()+"','"+user_id+"','0')";

        Statement stm;
        try {
            stm = connexion.createStatement();
             stm.execute(query);
             return 1;
        } catch (SQLException ex) {
            Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
}
