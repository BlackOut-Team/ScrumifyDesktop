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
import java.util.stream.Collectors;
import scrumifyd.GestionMeetings.models.Activity;

import static scrumifyd.GestionMeetings.services.MeetingService.connexion;
import scrumifyd.GestionProjets.services.UserSession;
import scrumifyd.GestionUsers.controllers.SigninController;
import scrumifyd.GestionUsers.models.User;
import scrumifyd.GestionUsers.services.UserCrud;
import scrumifyd.util.MyDbConnection;

/**
 *
 * @author hp
 */
public class ActivityService implements InterfaceActivity {

    static Connection connexion;

    public ActivityService() {
        connexion = MyDbConnection.getInstance().getConnexion();

    }

    @Override
    public List<Activity> getAllActivity() throws SQLException {
        List<Activity> Activity = new ArrayList<>();
        SigninController s = new SigninController();
        int user_idd = s.user.getUserId();
        UserCrud ucrud = new UserCrud();
        ArrayList<Integer> ll = ucrud.getTeams(user_idd);

        Statement stm = connexion.createStatement();
        ll.forEach((t) -> {
            String req = "SELECT a.* FROM activity a , team_user t WHERE t.team_id='" + t + "' and t.user_id=a.user_id";
            try {

                ResultSet res = stm.executeQuery(req);
                while (res.next()) {
                    Activity m = new Activity(res.getInt(1), res.getString("action"), res.getInt("viewed"), res.getInt("user_id"));
                    Activity.add(m);
                }
            } catch (SQLException ex) {
                Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        return Activity;

    }

    @Override
    public void supprimerActivity(int id) throws SQLException {
        String query = "DELETE FROM `Activity` WHERE id = " + id;
        Statement stm;
        try {
            stm = connexion.createStatement();
            stm.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(MeetingService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int ajouterActivity(Activity activity) throws SQLException {
        UserSession u = UserSession.getInstace(activity.getUser_id());
        int user_id = u.getUserId();
        String query = "INSERT INTO activity (action, user_id, viewed) VALUES ('" + activity.getAction() + "','" + user_id + "','0')";

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

    public User getUserAc(int id) {
        User u = new User();

        try {
            String req = "SELECT u.* FROM user u , activity a where a.user_id=u.id and a.id='" + id + "'";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                u.setId(result.getInt(1));
                u.setName(result.getString("name"));
                u.setLastname(result.getString("lastname"));
                u.setUsername(result.getString("username"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(ActivityService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return u;

    }
    public List<Activity> searchActivities(String key) throws SQLException{
                List<Activity> ac = new ArrayList<>();
                                List<Activity> ac1 = new ArrayList<>();


        List<Activity> act = getAllActivity();
        System.out.println(act);

        ac = act.stream().filter(t -> t.getAction().//convert to uppercase for checking
                contains(key)).//filter values containing black
                collect(Collectors.toList());
        System.out.println(ac);
        ac1 = act.stream().filter(t -> getUserAc(t.getId()).getUsername().//convert to uppercase for checking
                contains(key)).//filter values containing black
                collect(Collectors.toList());
        System.out.println(ac1);
        //  Projects.add(Projects1););
        ac.addAll(ac1);
        return ac;

    }

  
}
