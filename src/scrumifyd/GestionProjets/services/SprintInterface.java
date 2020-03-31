/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.services;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.models.Sprint;

/**
 *
 * @author Amira Doghri
 */
public interface SprintInterface {

    public int addSprint(Sprint s) throws SQLException ;
    
     public void addDefaultSprint(Sprint s,LocalDate startdate,int week) throws SQLException;


    public int sprintSuggest1(Project p);

    public int sprintSuggest2(Project p);

    public List<Sprint> getAllSprints(int id) throws SQLException;

    public boolean archiveSprint(int id);

    public int updateSprint(int id ,Sprint sprint);

    public ObservableList<PieChart.Data> getSprintGraphStatistics();

    public boolean unarchiveSprint(Sprint sprint);

}
