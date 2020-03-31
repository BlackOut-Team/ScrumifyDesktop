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


    public    int  addProject(Project p) throws SQLException ;
     public Project getProject(int id) throws SQLException ;
    public  List<Project> getAllProjects() throws SQLException;
    public boolean archiveProject(int id) ;
    public int updateProject(int id , Project project)throws SQLException ;
    public ObservableList<PieChart.Data> getProjectGraphStatistics() ;
    public boolean unarchiveProject(int id) ;




}

