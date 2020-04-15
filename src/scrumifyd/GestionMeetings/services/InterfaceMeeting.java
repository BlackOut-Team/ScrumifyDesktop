/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.services;

import java.sql.SQLException;
import java.util.List;
import scrumifyd.GestionMeetings.models.Meeting;

/**
 *
 * @author RAMI
 */
public interface InterfaceMeeting {
    public  List<Meeting> getAllMeetings() throws SQLException;
    void supprimerMeeting(int id)throws SQLException;
     int updateMeeting(int id ,Meeting meeting) throws SQLException ;
             int ajouterMeeting(Meeting meeting)throws SQLException;


}
