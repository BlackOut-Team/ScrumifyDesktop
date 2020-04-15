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

            String req = "SELECT * FROM `activity` ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                
                Activity m = new Activity(result.getInt(1), result.getString("action"), result.getInt("viewed"));
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

    
    
}
