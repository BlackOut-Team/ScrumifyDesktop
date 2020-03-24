/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.util.MyDbConnection;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class EditPController implements Initializable {

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
    private JFXComboBox<?> Team;
    @FXML
    private JFXComboBox<?> PO;
    @FXML
    private Label Errors;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;

    int etat = 1;
    LocalDate today = LocalDate.now();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            Errors.setTextFill(Color.TOMATO);
            Errors.setText("Server Error : Check");
        } else {
            Errors.setTextFill(Color.GREEN);
            Errors.setText("Server is up : Good to go");
        }
    }

    public EditPController() {
        con = MyDbConnection.getInstance().getConnexion();

    }

    private void setFields(Project p) {

        Name.setText(p.getName());
        Description.setText(p.getDescription());
        // Deadline.set
        //Team.set
    }

    @FXML
    public void SubmitButton(MouseEvent event) {

        if (event.getSource() == Submit) {
            // here

            if (EditP()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit project");
                alert.setHeaderText("Results:");
                alert.setContentText("Edited successfully!");

                alert.showAndWait();
                loadUI("ProjectsCurrent");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edit project");
                alert.setHeaderText("Results:");
                alert.setContentText("ERROR!");

                alert.showAndWait();

            }

        }

    }

    public boolean EditP() {

        boolean res = false;
        String name = Name.getText();
        String description = Description.getText();
        // int team_id=0;
        // int owner_id=0;
        // int master_id=0;

        LocalDate deadline = Deadline.getValue();
        //String team = Team.getValue();
        //String productowner = PO.getValue();
        if (name.isEmpty() || description.isEmpty() || deadline.isBefore(LocalDate.now())) {
            setLblError(Color.TOMATO, "Empty credentials");

        } else {
            //query
            Project project = new Project(name, description, today, deadline, etat);
            InterfaceProjet Projects = new ProjectService();
            res = Projects.updateProject(project);
            if (res) {
                setLblError(Color.GREEN, "Project Edited Successfully.Redirecting..");

            } else {
                setLblError(Color.GREEN, "Project edited Successfully.Redirecting..");
            }

        }
        return res;

    }

    @FXML
    private void back(MouseEvent event) {
        loadUI("ProjectsCurrent");

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
