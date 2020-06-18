/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTeams.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import scrumifyd.GestionProjets.controllers.AddPController;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionTeams.models.Team;
import scrumifyd.GestionTeams.services.InterfaceTeam;
import scrumifyd.GestionTeams.services.TeamService;

/**
 * FXML Controller class
 *
 * @author Iheb
 */

  

public class AddteamController implements Initializable {

    @FXML
    private JFXTextField Name;
    @FXML
    private JFXButton AddButton;
    @FXML
    private Label Errors;
    @FXML
    private AnchorPane contentPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void AddButton(MouseEvent event) {
    
    
      if (event.getSource() == AddButton) {
            // here

            if (AddTeam()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add team");
                alert.setHeaderText("Results:");
                alert.setContentText("Added successfully!");

                alert.showAndWait();
                loadUI("GestionTeams","Team");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add team");
                alert.setHeaderText("Results:");
                alert.setContentText("ERROR!");

                alert.showAndWait();

            }

        }}
    
   
 public boolean AddTeam() {

        boolean res = false;
        String name = Name.getText();
     
        if (name.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");

        } else {
            //query
            try {
                Team team = new Team(name);

                TeamService Teams = new TeamService();
                res = Teams.addTeam(team);
                System.out.println(res);

                if (res) {
                    setLblError(Color.GREEN, "Team Added Successfully.Redirecting..");

                } else {
                    setLblError(Color.RED, "ERROR");
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return res;

    }
 
 

    private void back(MouseEvent event) {
        loadUI("GestionTeams","Team");

    }
     public void loadUI(String module , String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/"+module+"/views/" + ui + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contentPane.getChildren().add(root);
    }

    private void setLblError(Color color, String text) {
        Errors.setTextFill(color);
        Errors.setText(text);
        System.out.println(text);
    }

    
}
