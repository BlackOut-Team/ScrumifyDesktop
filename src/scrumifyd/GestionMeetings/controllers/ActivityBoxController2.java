/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author hp
 */
public class ActivityBoxController2 implements Initializable{

   
    @FXML
    private AnchorPane box;
    @FXML
    private ImageView avatar;
    @FXML
    private Label Action;
    @FXML
    private Label Viewed;
    private Label User;
    @FXML
    public FontAwesomeIconView SupprimerButton;
    @FXML
    public FontAwesomeIconView ViewedButton;

   
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }

    public ActivityBoxController2() {

    }

    public void setAction(String Action) {
        this.Action.setText(Action);
    }

    public void setViewed(int Viewed) {
        this.Viewed.setText("" + Viewed);
    }

  


    
    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public Label getAction() {
        return Action;
    }

    public Label getViewed() {
        return Viewed;
    }


   
    public void setUser(String user) {
        this.User.setText("" + user);
    }

    public Label getUser() {
        return User;
    }
    
}
