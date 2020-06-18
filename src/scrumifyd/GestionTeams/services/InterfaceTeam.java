/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTeams.services;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionTeams.models.Team;

/**
 *
 * @author Iheb
 */
public interface InterfaceTeam  {
      public    boolean  addTeam(Team t) throws SQLException ;
      
    public  List<Team> getAllTeams() throws SQLException;
    public boolean archiveTeam(Team team) ;
    public boolean updateTeam(Team team) ;
    public boolean unarchiveTeam(Team team) ;

    
}
