/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionUsers.models.User;
import scrumifyd.util.MyDbConnection;

/**
 *
 * @author Amira Doghri
 */
public class ProjectService implements InterfaceProjet {

    static Connection connexion;

    public ProjectService() {
        connexion = MyDbConnection.getInstance().getConnexion();

    }

    @Override
    public int addProject(Project p) throws SQLException {
        try {
            PreparedStatement statement = connexion.prepareStatement(
                    "INSERT INTO PROJET (name ,description,created,duedate,etat,team_id,owner_id,master_id ) values (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, p.getName());
            statement.setString(2, p.getDescription());
            statement.setDate(3, java.sql.Date.valueOf(p.getCreated()));
            statement.setDate(4, java.sql.Date.valueOf(p.getDuedate()));
            statement.setInt(5, p.getEtat());
            statement.setInt(6, p.getTeam_id());
            statement.setInt(7, p.getOwner_id());
            statement.setInt(8, p.getMaster_id());
            statement.executeUpdate();
            ResultSet tableKeys = statement.getGeneratedKeys();
            if (tableKeys.next()) {

                int s = tableKeys.getInt(1);
                p.setId(s);
                return s;

            } else {
                return 0;
            }

       
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;

    }

    @Override

    public Project getProject(int id) throws SQLException {
        Project p = new Project();
        try {

            String req = "SELECT * FROM PROJET WHERE ID=" + id + " ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date createdd = result.getDate("created");
                Date duedate = result.getDate("duedate");
                LocalDate datec = Instant.ofEpochMilli(createdd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                p = new Project(result.getInt(1), result.getString("name"), result.getString("description"), datec, dated, result.getInt("etat"));
                return p;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return p;

    }

    @Override

    public List<Project> getAllProjects() throws SQLException {

        List<Project> Projects = new ArrayList<>();
        try {

            String req = "SELECT * FROM `projet` ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date createdd = result.getDate("created");
                Date duedate = result.getDate("duedate");
                LocalDate datec = Instant.ofEpochMilli(createdd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Project p = new Project(result.getInt(1), result.getString("name"), result.getString("description"), datec, dated, result.getInt("etat"));

                //System.out.println(p.getCellButton());
                Projects.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Projects;

    }

    @Override

    public List<Project> getAllActiveProjects(int id) throws SQLException {

        List<Project> Projects = new ArrayList<>();
        List<Project> Projectss = new ArrayList<>();

        try {

            String req = "SELECT * FROM projet   WHERE etat=1  ";

            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date createdd = result.getDate("created");
                Date duedate = result.getDate("duedate");
                LocalDate datec = Instant.ofEpochMilli(createdd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Project p = new Project(result.getInt(1), result.getString("name"), result.getString("description"), datec, dated, result.getInt("etat"), result.getInt("team_id"), result.getInt("owner_id"), result.getInt("master_id"));
                Projectss.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Projectss.forEach((pp) -> {
            if((pp.getMaster_id() == id) || (pp.getOwner_id() == id)){
                Projects.add(pp);
           
            }
                else {
                        List<User> teamlist = getTeamMembers(pp.getTeam_id());
                        
                teamlist.forEach((User t) -> {
               
                if ((t.getId() == id) )  {
                    Projects.add(pp);
                } else {
                    System.out.println(Projects.size());
                    
                }
                });
           
                }
        });

        return Projects;

    }

    @Override

    public boolean archiveProject(int id) {
        try {
            String update = "UPDATE PROJET SET ETAT=0 WHERE ID=" + id + "";
            PreparedStatement stm = connexion.prepareStatement(update);

            int res = stm.executeUpdate();
            return (res > 0);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override

    public boolean unarchiveProject(int id) {
        try {
            String update = "UPDATE PROJET SET ETAT=1 WHERE ID=" + id + "";
            PreparedStatement stm = connexion.prepareStatement(update);

            int res = stm.executeUpdate();
            return (res > 0);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override

    public int updateProject(int id, Project project) throws SQLException {
        try {
            String update = "UPDATE PROJET SET NAME=?, DESCRIPTION=? , DUEDATE= ?  WHERE ID=" + id + "";
            PreparedStatement stm = connexion.prepareStatement(update, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, project.getName());
            stm.setString(2, project.getDescription());
            stm.setDate(3, java.sql.Date.valueOf(project.getDuedate()));
            //stm.setInt(4, project.getTeam_id());
            //stm.setInt(5, project.getOwner_id());
            //stm.setInt(6, project.getMaster_id());

            if (stm.executeUpdate() > 0) {

                int s = id;
                return s;

            } else {
                return 0;
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    //Statistics  
    @Override

    public ObservableList<PieChart.Data> getProjectGraphStatistics(int user_id) {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {

            String qu2 = "SELECT COUNT(*) FROM PROJET where ETAT = 1";
            String qu3 = "SELECT COUNT(*) FROM PROJET where ETAT = 0";

            ResultSet rs = execQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Active Projects (" + count + ")", count));
            }
            rs = execQuery(qu3);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Archived Projects (" + count + ")", count));
            }

        } catch (SQLException e) {
        }
        return data;
    }
     @Override

    public ObservableList<PieChart.Data> getProjectTimeGraphStatistics(int user_id) {
                    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        try {
            List<Project> curr  = getCurrentProjects(user_id);
            List<Project> comp  = getCurrentProjects(user_id);
                
           
//                
//                String qu2 = "SELECT COUNT(*) FROM PROJET where ETAT = 1";
//                String qu3 = "SELECT COUNT(*) FROM PROJET where ETAT = 0";
                
//                ResultSet rs = execQuery(qu2);
//                if (rs.next()) {
//                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Current Projects (" + curr.size() + ")", curr.size()));
                
//                rs = execQuery(qu3);
//                if (rs.next()) {
//                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Completed Projects (" + comp.size() + ")", comp.size()));
                         return data;

 
  
          
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   
        return data;
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

    public List<Project> searchProjects(String key,int user_id) throws SQLException {

        List<Project> Projects = new ArrayList<>();
//        List<Project> Projects1 = new ArrayList<>();
//
//        List<Project> Pr = getAllActiveProjects(user_id);
//        
//        Projects = Pr.stream().filter(t -> t.getName().toUpperCase().//convert to uppercase for checking
//         contains(key)).//filter values containing black
//         collect(Collectors.toList())
//                ;
////        Projects1 = Pr.stream().filter(t -> t.getDescription().toUpperCase().//convert to uppercase for checking
////         contains(key)).//filter values containing black
////         collect(Collectors.toList())
//                ;
////        Projects.add((Project) Projects1);
        
        try {

            String req = "SELECT * FROM `projet` WHERE `etat`=1 and (`name` like ? or `description` like ?) ";
            PreparedStatement stm = connexion.prepareStatement(req);
            stm.setString(1, "%" + key + "%");
            stm.setString(2, "%" + key + "%");

            ResultSet result = stm.executeQuery();

            while (result.next()) {
                Date createdd = result.getDate("created");
                Date duedate = result.getDate("duedate");
                LocalDate datec = Instant.ofEpochMilli(createdd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Project p = new Project(result.getInt(1), result.getString("name"), result.getString("description"), datec, dated, result.getInt("etat"));
                Projects.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Projects;

        
    }

    @Override

    public List<Project> getCurrentProjects(int id) throws SQLException {

        List<Project> Projects = new ArrayList<>();
                List<Project> Projectss = new ArrayList<>();

        try {

            String req = "SELECT * FROM `projet` WHERE (`etat`=1 and `duedate` > CURRENT_DATE())  ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date createdd = result.getDate("created");
                Date duedate = result.getDate("duedate");
                LocalDate datec = Instant.ofEpochMilli(createdd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

                Project p = new Project(result.getInt(1), result.getString("name"), result.getString("description"), datec, dated, result.getInt("etat"), result.getInt("team_id"), result.getInt("owner_id"), result.getInt("master_id"));
                Projectss.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
 Projectss.forEach((pp) -> {
            if((pp.getMaster_id() == id) || (pp.getOwner_id() == id)){
                Projects.add(pp);
           
            }
                else {
                        List<User> teamlist = getTeamMembers(pp.getTeam_id());
                        
                teamlist.forEach((User t) -> {
               
                if ((t.getId() == id) )  {
                    Projects.add(pp);
                } else {
                    System.out.println(Projects.size());
                    
                }
                });
           
                }
        });

        return Projects;

    }

    @Override

    public List<Project> getCompletedProjects(int id) throws SQLException {

        List<Project> Projects = new ArrayList<>();
                List<Project> Projectss = new ArrayList<>();

        try {

            String req = "SELECT * FROM `projet` WHERE `etat`=1 and `duedate` < CURRENT_DATE() ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date createdd = result.getDate("created");
                Date duedate = result.getDate("duedate");
                LocalDate datec = Instant.ofEpochMilli(createdd.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Project p = new Project(result.getInt(1), result.getString("name"), result.getString("description"), datec, dated, result.getInt("etat"), result.getInt("team_id"), result.getInt("owner_id"), result.getInt("master_id"));
                Projectss.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
       Projectss.forEach((pp) -> {
            if((pp.getMaster_id() == id) || (pp.getOwner_id() == id)){
                Projects.add(pp);
           
            }
                else {
                        List<User> teamlist = getTeamMembers(pp.getTeam_id());
                        
                teamlist.forEach((User t) -> {
               
                if ((t.getId() == id) )  {
                    Projects.add(pp);
                } else {
                    System.out.println(Projects.size());
                    
                }
                });
           
                }
        });

        return Projects;

    }

    @Override

    public List<User> getTeamMembers(int id) {
        List<User> members_list = new ArrayList<>();
        try {

            //String req = "select m.user_id from team_user m , project c where c.team_id = "+id+"" ; 
//            String req = "select m.user_id , u.username from team_user m , user u ,projet c where c.team_id = "+id+" and u.id = m.user_id" ; 
            String req = "select t.user_id , u.username , t.role from team_user t  ,user u where t.team_id = " + id + " and u.id = t.user_id ";

            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {

                User u = new User(result.getInt(1), result.getString(2));
                members_list.add(u);

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return members_list;

    }
    @Override
    public List<Project> getDeadlines(int user_id){
        List<Project> Projects = new ArrayList<>();
        List<Project> Projectss = new ArrayList<>();
        

        try {

            String req = "SELECT * FROM projet   WHERE etat=1  ";

            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
              
                Date duedate = result.getDate("duedate");
                LocalDate dated = Instant.ofEpochMilli(duedate.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Project p = new Project(result.getInt(1), result.getString("name"), dated, result.getInt("team_id"), result.getInt("owner_id"), result.getInt("master_id"));
                Projectss.add(p);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        Projectss.forEach((pp) -> {
            if((pp.getMaster_id() == user_id) || (pp.getOwner_id() == user_id)){
                Projects.add(pp);
           
            }
                else {
                        List<User> teamlist = getTeamMembers(pp.getTeam_id());
                        
                teamlist.forEach((User t) -> {
               
                if ((t.getId() == user_id) )  {
                    Projects.add(pp);
                } else {
                    System.out.println(Projects.size());
                    
                }
                });
           
                }
        });

        return Projects;
    }
 @Override

    public ObservableList<PieChart.Data> getProjectGraphStatisticsB() {
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();
        try {

            String qu2 = "SELECT COUNT(*) FROM PROJET where ETAT = 1";
            String qu3 = "SELECT COUNT(*) FROM PROJET where ETAT = 0";

            ResultSet rs = execQuery(qu2);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Active Projects (" + count + ")", count));
            }
            rs = execQuery(qu3);
            if (rs.next()) {
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Archived Projects (" + count + ")", count));
            }

        } catch (SQLException e) {
        }
        return data;
    }
     @Override

    public ObservableList<PieChart.Data> getProjectTimeGraphStatisticsB() {
                    ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        try {
////            List<Project> curr  = getCurrentProjects(user_id);
////            List<Project> comp  = getCurrentProjects(user_id);
                
           
//                
                String qu2 = "SELECT COUNT(*) FROM PROJET where ETAT = 1";
                String qu3 = "SELECT COUNT(*) FROM PROJET where ETAT = 0";
                
                ResultSet rs = execQuery(qu2);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Current Projects (" + count + ")", count ));
                }
                
                rs = execQuery(qu3);
                if (rs.next()) {
                    int count = rs.getInt(1);
                    data.add(new PieChart.Data("Completed Projects (" + count + ")", count ));
                         return data;
                }

 
  
          
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   
        return data;
    }


}
