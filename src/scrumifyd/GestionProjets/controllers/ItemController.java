/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Month;
import java.util.Optional;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.ProjectService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ItemController implements Initializable {

    @FXML
    private Label Name;
    @FXML
    private Label master;
    @FXML
    private Label client;
    @FXML
    private Label etat;
    @FXML
    private Label team_member;
    @FXML
    private Label Description;
    @FXML
    private Label created_day;
    @FXML
    private Label created_month;
    @FXML
    private Label created_year;
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
    InterfaceProjet Projects = new ProjectService();
    @FXML
    public FontAwesomeIconView ArchiveButton;
    @FXML
    public FontAwesomeIconView EditButton;

    Project p;
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

    public ItemController() {

    }

    public void setName(String Name) {
        this.Name.setText(Name);
    }

    public void setMaster(String master) {
        this.master.setText(master);
    }

    public void setClient(String client) {
        this.client.setText(client);
    }

    public void setEtat(int etat) {
        if (etat == 1) {
            this.etat.setText("Active project");
        } else {
            this.etat.setText("Archived project");

        }
    }

    public void setDescription(String Description) {
        this.Description.setText(Description);
    }

    public void setCreated_day(int created_day) {
        this.created_day.setText("" + created_day);
    }

    public void setCreated_month(Month created_month) {
        this.created_month.setText("" + created_month);
    }

    public void setCreated_year(int created_year) {
        this.created_year.setText("" + created_year);
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



   

}
