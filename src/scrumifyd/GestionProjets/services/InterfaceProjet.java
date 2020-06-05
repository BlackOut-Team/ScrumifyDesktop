/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.services;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionUsers.models.User;

/**
 *
 * @author Amira Doghri
 */
public interface InterfaceProjet {

    public int addProject(Project p) throws SQLException;

    public Project getProject(int id) throws SQLException;

    public List<Project> getAllProjects() throws SQLException;

    public List<Project> getAllActiveProjects(int id) throws SQLException;

    public List<Project> getCurrentProjects(int id) throws SQLException;

    public List<Project> getCompletedProjects(int id) throws SQLException;

    public boolean archiveProject(int id);

    public int updateProject(int id, Project project) throws SQLException;
    public List<Project> searchProjects(String key,int user_id) throws SQLException ;

    public ObservableList<PieChart.Data> getProjectGraphStatistics(int user_id);
    
     public ObservableList<PieChart.Data> getProjectTimeGraphStatistics(int user_id) ;
     
         public ObservableList<PieChart.Data> getProjectGraphStatisticsB();
    
     public ObservableList<PieChart.Data> getProjectTimeGraphStatisticsB() ;

    public boolean unarchiveProject(int id);
   
    public List<User> getTeamMembers(int id);
     public List<Project> getDeadlines(int user_id);
        public int getRoleU(int pr_id, int user_id, int team_id) ;



}
