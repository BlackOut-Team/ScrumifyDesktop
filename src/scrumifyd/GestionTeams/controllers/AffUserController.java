/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTeams.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import scrumifyd.GestionTeams.services.TeamService;

/**
 * FXML Controller class
 *
 * @author Iheb
 */
public class AffUserController implements Initializable {

    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXTextField Name;
    @FXML
    private JFXButton AffButton;
    @FXML
    private Label Errors;
    @FXML
    private TableColumn<?, ?> UserName;
    @FXML
    private TableColumn<?, ?> Email;

    /** 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TeamService teamService = TeamService.getInstance();
        System.err.println("selected Team" + teamService.getSelectedTeam());
    }    

    @FXML
    private void AddButton(MouseEvent event) {
        TeamService teamService = TeamService.getInstance();
        System.err.println("Name" + this.Name.getText());
        if (this.Name.getText().isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            return;
        }
        teamService.addMember(this.Name.getText());
        
    }
    
    private void setLblError(Color color, String text) {
        Errors.setTextFill(color);
        Errors.setText(text);
        System.out.println(text);
    }
    
    
}
