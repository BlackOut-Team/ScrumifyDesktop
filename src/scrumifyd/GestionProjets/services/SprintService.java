package scrumifyd.GestionProjets.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.models.Sprint;
import scrumifyd.util.MyDbConnection;

/**
 *
 * @author Amira Doghri
 */
public class SprintService implements SprintInterface {

    static Connection connexion;

    public SprintService() {
        connexion = MyDbConnection.getInstance().getConnexion();

    }

    @Override
    public int addSprint(Sprint s) throws SQLException {
        try {
            PreparedStatement statement = connexion.prepareStatement(
                    "INSERT INTO SPRINT (name ,project_id , description,duedate,etat,created ) values (?,?,?,?,?,?) ",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, s.getName());
            statement.setInt(2, s.getProject_id());
            statement.setString(3, s.getDescription());
            statement.setDate(4, java.sql.Date.valueOf(s.getDuedate()));
            statement.setInt(5, s.getEtat());
            statement.setDate(6, java.sql.Date.valueOf(s.getCreated()));

        statement.executeUpdate() ;
             ResultSet tableKeys = statement.getGeneratedKeys();
             if (tableKeys.next() ){
             
               int ss= tableKeys.getInt(1);
               s.setId(ss);
               return ss ;

             }
             
             else 
             {
                 return 0 ;
             }
         
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0 ;

    }

    @Override
    public int sprintSuggest1(Project p) {

        long weeks = ChronoUnit.WEEKS.between(p.getCreated(), p.getDuedate());
        int suggestion1 = (int) weeks / 4;

        return suggestion1;
    }

    @Override
    public int sprintSuggest2(Project p) {
        long weeks = ChronoUnit.WEEKS.between(p.getCreated(), p.getDuedate());
        int suggestion2 = (int) weeks / 2;
        return suggestion2;
    }

    @Override
    public List<Sprint> getAllSprints(int id) throws SQLException {
        List<Sprint> Sprints = new ArrayList<>();
       
        try {

            String req = "SELECT * FROM `sprint` WHERE `project_id`="+id+" ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date createdd = result.getDate("created"); 
                Date duedate = result.getDate("duedate");
                LocalDate datec = Instant.ofEpochMilli(createdd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Sprint s = new Sprint(result.getInt(1),result.getString("name"), result.getString("description"), datec, dated, result.getInt("etat"));
                Sprints.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Sprints;
    }

    @Override
    public boolean archiveSprint(int id ) {
        try {
            String update = "UPDATE SPRINT SET ETAT=0 WHERE ID="+id+"";
            PreparedStatement stm = connexion.prepareStatement(update);

    

            int res = stm.executeUpdate();
            return (res > 0);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public int updateSprint(int id ,Sprint sprint) {
        try {
            String update = "UPDATE sprint SET NAME=?, DESCRIPTION=? , CREATED= ? , DUEDATE=? WHERE ID="+id+"";
            PreparedStatement stm = connexion.prepareStatement(update);
            stm.setString(1, sprint.getName());
            stm.setString(2, sprint.getDescription());
            stm.setDate(3, java.sql.Date.valueOf(sprint.getCreated()));
            stm.setDate(4, java.sql.Date.valueOf(sprint.getDuedate()));

            if (stm.executeUpdate()>0){
                int s= id ;
                return s;
            }
            else 
            {
                return 0;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override
    public ObservableList<PieChart.Data> getSprintGraphStatistics() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {
            String qu1 = "SELECT COUNT(*) FROM SPRINT";
            String qu2 = "SELECT COUNT(*) FROM SPRINT where ETAT = 1";
            String qu3 = "SELECT COUNT(*) FROM SPRINT where ETAT = 0";

            ResultSet rs = execQuery(qu1);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Sprints (" + count + ")", count));
            }
            rs = execQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Active Sprints (" + count + ")", count));
            }
            rs = execQuery(qu3);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Archived Sprints (" + count + ")", count));
            }
        } catch (SQLException e) {
        }
        return data;
    }

    @Override
    public boolean unarchiveSprint(Sprint sprint) {
        try {
            String update = "UPDATE SPRINT SET ETAT=1 WHERE ID=?";
            PreparedStatement stm = connexion.prepareStatement(update);

            stm.setInt(1, sprint.getEtat());

            int res = stm.executeUpdate();
            return (res > 0);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
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

    @Override
    public void addDefaultSprint(Sprint s , LocalDate startdate, int weeks) throws SQLException {
        try {
            PreparedStatement statement = connexion.prepareStatement(
                    "INSERT INTO SPRINT (name ,project_id, description,duedate,etat,created ) values (?,?,?,?,?,?) ");
            statement.setString(1, "Sprint");
            
            statement.setInt(2, s.getProject_id());
            statement.setString(3, "Description");
            statement.setDate(4, java.sql.Date.valueOf(startdate.plusWeeks(weeks + 4)));
            statement.setInt(5, 1);
            statement.setDate(6, java.sql.Date.valueOf(startdate.plusWeeks(weeks)));
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
