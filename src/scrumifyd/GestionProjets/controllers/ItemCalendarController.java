/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author AmiraDoghri
 */
public class ItemCalendarController implements Initializable {

    @FXML
    private AnchorPane item;
    @FXML
    private ImageView avatar;
    @FXML
    private Label Name;
    @FXML
    private Label master;
    @FXML
    private Label client;
    @FXML
    private Label etat;
    @FXML
    private Label lbl_role;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
      public void setName(String Name) {
        this.Name.setText(Name);
    }
}
