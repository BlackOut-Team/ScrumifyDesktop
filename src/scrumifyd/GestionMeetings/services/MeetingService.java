/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.services;

import java.awt.Component;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javafx.scene.layout.HBox;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.table.DefaultTableModel;
import scrumifyd.util.MyDbConnection;
import scrumifyd.GestionMeetings.models.Meeting;
import static scrumifyd.GestionMeetings.services.MeetingService.connexion;

/**
 *
 * @author hp
 */
public final class MeetingService implements InterfaceMeeting {

    static Connection connexion;


    public MeetingService() {
        connexion = MyDbConnection.getInstance().getConnexion();

    }


  
    
  
     public List<Meeting> getAllMeetings() throws SQLException {
      List<Meeting> Meetings = new ArrayList<>();
        try {

            String req = "SELECT * FROM `meetings` ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date MeetingDate;
                MeetingDate = result.getDate("MeetingDate");
                LocalDate MeetingD = Instant.ofEpochMilli(MeetingDate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Meeting m = new Meeting(result.getInt(1), result.getString("name"), result.getString("place"), result.getString("type"),MeetingD);
                Meetings.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Meetings;
    }
    public void supprimerMeeting(int id)throws SQLException{
        String query = "DELETE FROM `Meetings` WHERE id = "+id;
        Statement stm;
        try {
            stm = connexion.createStatement();
             stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }       
    
    @Override
        public int ajouterMeeting(Meeting meeting)throws SQLException{
         String query = "INSERT INTO meetings (name, place, type,sprint, meetingDate) VALUES ('"+meeting.getName()+"','"+meeting.getPlace()+"','"+meeting.getType()+"','"+meeting.getSprint()+"','"+meeting.getMeetingDate()+"')";

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
    
    
    @Override

    public int updateMeeting(int id ,Meeting meeting) throws SQLException {
        try {
            String update = "UPDATE meetings SET NAME=?, PLACE=? , TYPE=?, SPRINT=? , MEETINGDATE= ?  WHERE ID="+id+"";
            PreparedStatement stm = connexion.prepareStatement(update,Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, meeting.getName());
            stm.setString(2, meeting.getPlace());
            stm.setString(3, meeting.getType());
            stm.setString(4, meeting.getSprint());
            
            stm.setDate(5, java.sql.Date.valueOf(meeting.getMeetingDate()));
            //stm.setInt(4, project.getTeam_id());
            //stm.setInt(5, project.getOwner_id());
            //stm.setInt(6, project.getMaster_id());

            if (stm.executeUpdate()>0){
              
                
               int s=id;
               return s ;

             }
             
             else 
             {
                 return 0 ;
             }
         
         

        } catch (SQLException ex) {
            Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    
    
    
    
     public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            Statement stmt = connexion.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }
}
