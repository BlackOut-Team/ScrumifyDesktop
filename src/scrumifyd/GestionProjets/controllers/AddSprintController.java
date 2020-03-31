/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.models.Sprint;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.services.SprintInterface;
import scrumifyd.GestionProjets.services.SprintService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class AddSprintController implements Initializable {

    @FXML
    private Pane contentPane;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private JFXTextField Name;
    @FXML
    private JFXButton Submit;
    @FXML
    private JFXTextArea Description;
    @FXML
    private JFXDatePicker Deadline;
    @FXML
    private Label Errors;
    @FXML
    private JFXButton AddagainButton;
    @FXML
    private JFXDatePicker startdate;

    Project p;
    int pid;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void back(MouseEvent event) {
        loadUI("ProjectsCurrent");
    }

    public void setProject(Project p) {
        this.p = p;
    }

    public void setProjectId(int pid) {
        this.pid = pid;
    }

    @FXML
    private void SubmitButton(MouseEvent event) {
        if (event.getSource() == Submit) {
            // here

            if (AddS()) {

                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Add Sprint");
                    alert.setHeaderText("Results:");
                    alert.setContentText("Added successfully!");
                    
                    alert.showAndWait();
                    
                    contentPane.getChildren().clear();
                    
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/Sprints.fxml"));
                    Parent root = (Parent) loader.load();
                    ShowSprintController sp = loader.getController();
                    sp.setProject(p);
                    
                    contentPane.getChildren().add(root);
                } catch (IOException ex) {
                    Logger.getLogger(AddSprintController.class.getName()).log(Level.SEVERE, null, ex);
                }

                   


            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Add sprint");
                alert.setHeaderText("Results:");
                alert.setContentText("ERROR!");

                alert.showAndWait();

            }

        }
    }

    @FXML
    private void AddagainButton(MouseEvent event) {
        if (AddS()) {

            try {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Add Sprint");
                alert.setHeaderText("Results:");
                alert.setContentText("Added successfully!");
                
                alert.showAndWait();
                
                contentPane.getChildren().clear();
                FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/AddSprint.fxml"));
                Parent root = (Parent) loader.load();
                AddSprintController sp=  loader.getController();
                //System.out.println(pid);
                sp.setProjectId(pid);
                contentPane.getChildren().add(root);
            } catch (IOException ex) {
                Logger.getLogger(AddSprintController.class.getName()).log(Level.SEVERE, null, ex);
            }
          

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Add sprint");
            alert.setHeaderText("Results:");
            alert.setContentText("ERROR!");

            alert.showAndWait();

        }

    }

    public boolean AddS() {

        int res = 0;
        boolean s = false;
        String name = Name.getText();
        String description = Description.getText();
        LocalDate startDate = startdate.getValue();
        LocalDate deadline = Deadline.getValue();

        int project_id = pid;
        int etat = 1;
        if (name.isEmpty() || description.isEmpty() || deadline.isBefore(startDate) || deadline.isBefore(LocalDate.now()) || startDate.isBefore(LocalDate.now())) {
            setLblError(Color.TOMATO, "Empty / wrong credentials");

        } else {
            //query
            try {

                Sprint sprint = new Sprint(name, description, startDate, deadline, etat, project_id);

                SprintInterface Sprints = new SprintService();
                res = Sprints.addSprint(sprint);
                System.out.println(res);
                
                if (res != 0) {
                    s = true;
                    setLblError(Color.GREEN, "Project Added Successfully.Redirecting..");

                 
                } else {
                    s = false;
                    setLblError(Color.RED, "Project error...");
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return s;

    }

    public void loadUI(String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/" + ui + ".fxml"));

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
