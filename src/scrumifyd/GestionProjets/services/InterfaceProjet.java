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

/**
 *
 * @author Amira Doghri
 */
public interface InterfaceProjet {


    public    boolean  addProject(Project p) throws SQLException ;
             public  List<Project> getAllProjects() throws SQLException;
               public boolean archiveProject(Project project) ;
                   public boolean updateProject(Project project) ;
                       public ObservableList<PieChart.Data> getProjectGraphStatistics() ;
                       public boolean unarchiveProject(Project project) ;




}

