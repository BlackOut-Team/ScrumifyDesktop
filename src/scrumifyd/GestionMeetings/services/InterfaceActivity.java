/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.services;

import java.sql.SQLException;
import java.util.List;
import scrumifyd.GestionMeetings.models.Activity;
import scrumifyd.GestionUsers.models.User;

/**
 *
 * @author hp
 */
public interface InterfaceActivity {

    public List<Activity> getAllActivity() throws SQLException;

    void supprimerActivity(int id) throws SQLException;

    public int ajouterActivity(Activity activity) throws SQLException;

    public User getUserAc(int id);

    public List<Activity> searchActivities(String key) throws SQLException;

}
