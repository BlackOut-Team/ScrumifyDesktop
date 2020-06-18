/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
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
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class MeetingBoxController implements Initializable {

    @FXML
    private Label Name;
    @FXML
    private Label type;
    @FXML
    private Label place;
    @FXML
    private Label sprint;
    @FXML
    private Label deadline_day;
    @FXML
    private Label deadline_month;
    @FXML
    private Label deadline_year;
    @FXML
    private Label id;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;
    @FXML
    private AnchorPane item;
    @FXML
    private ImageView avatar;
    @FXML
    public FontAwesomeIconView SupprimerButton;
    @FXML
    public FontAwesomeIconView EditButton;

    int pid;
    @FXML
    public FontAwesomeIconView showSprintsButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {



    }

    public MeetingBoxController() {

    }

    public void setName(String Name) {
        this.Name.setText(Name);
    }

    public void setType(String type) {
        this.type.setText(type);
    }

    public void setPlace(String place) {
        this.place.setText(place);
    }

    public void setSprint(String sprint) {
        this.sprint.setText(sprint);
    }


    public void setDeadline_day(int deadline_day) {
        this.deadline_day.setText("" + deadline_day);
    }

    public void setDeadline_month(Month deadline_month) {
        this.deadline_month.setText("" + deadline_month);
    }

    public void setDeadline_year(int deadline_year) {
        this.deadline_year.setText("" + deadline_year);
    }

    public void setId(int id) {
        this.id.setText("" + id);
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public Label getName() {
        return Name;
    }

    public Label getType() {
        return type;
    }

    public Label getSprint() {
        return sprint;
    }

    public Label getId() {
        return id;
    }
    
   

}
