/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionTasks.services.task_services;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ItemTaskController implements Initializable {

    @FXML
    private Label Name;

    @FXML
    private Label team_member;
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
    public FontAwesomeIconView EditButton;
    @FXML
    public FontAwesomeIconView ArchiveButton;
    @FXML
    public FontAwesomeIconView showSprintsButton;
    @FXML
    private AnchorPane item;
    @FXML
    private Label priority;
    @FXML
    public MenuButton menu;
    @FXML
    public MenuItem action1;
    @FXML
    public MenuItem action2;
    task_services task = new task_services();
    int id;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        menu.setPopupSide(Side.BOTTOM);

    }

    public ItemTaskController() {
    }

    public void setName(String Name) {
        this.Name.setText(Name);
    }

    public void setCreated_day(int created_day) {
        this.created_day.setText("" + created_day);
    }

    public void setCreated_month(int created_month) {
        this.created_month.setText("" + created_month);
    }

    public void setCreated_year(int created_year) {
        this.created_year.setText("" + created_year);
    }

    public void setDeadline_day(int deadline_day) {
        this.deadline_day.setText("" + deadline_day);
    }

    public void setDeadline_month(int deadline_month) {
        this.deadline_month.setText("" + deadline_month);
    }

    public void setDeadline_year(int deadline_year) {
        this.deadline_year.setText("" + deadline_year);
    }

    public void setid(int id) {
        this.id = id;
    }

    public void action1(ActionEvent event) {
        if (action1.getText().equals("Move to to do")) {
            task.move_to_do(id);
        } else if (action1.getText().equals("Move to doing")) {
            task.move(id);
        } else if (action1.getText().equals("Move to done")) {
            task.move_to_done(id);
        }

    }

    public void action2(ActionEvent event) {
        if (action2.getText().equals("Move to to do")) {
            task.move_to_do(id);

        } else if (action2.getText().equals("Move to doing")) {
            task.move(id);
        } else if (action2.getText().equals("Move to done")) {
            System.out.println(id);
            task.move_to_done(id);
        }
    }

}
