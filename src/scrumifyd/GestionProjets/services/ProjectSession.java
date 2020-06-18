 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.services;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import scrumifyd.GestionProjets.models.Project;
import static scrumifyd.GestionProjets.services.ProjectService.connexion;

public final class ProjectSession {

    private static ProjectSession instance;

    private int projectId;
    private String projectName;

    private ProjectSession(int projectId) {
        this.projectId = projectId;
    }

    public static ProjectSession getInstace(int projectId) {
        if(instance == null) {
            instance = new ProjectSession(projectId);
        }
        return instance;
    }

    public int getProjectId() {
        return projectId;
    }

  
    public String getProjectname(int projectId) {
        try {

            String req = "SELECT * FROM PROJET WHERE ID=" + projectId + " ";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                String project_name = result.getString("name");
                 return project_name;

            }
        } catch (SQLException ex) {
            Logger.getLogger(ProjectService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return projectName;
    }

   

    public void cleanUserSession() {
        projectId = -1;// or null
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + projectName + "' ";
               
    }
}