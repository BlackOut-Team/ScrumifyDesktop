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
import doryan.windowsnotificationapi.fr.Notification;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.models.Sprint;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.services.SprintInterface;
import scrumifyd.GestionProjets.services.SprintService;
import scrumifyd.ScrumifyD;
import scrumifyd.util.MyDbConnection;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class EditSprintController implements Initializable {

    @FXML
    private StackPane contentPane;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private JFXTextField Name;
    @FXML
    private JFXButton Submit;
    @FXML
    private JFXTextArea Description;
    @FXML
    private Label Errors;
    @FXML
    private JFXDatePicker Deadline;
    @FXML
    private JFXDatePicker startdate;
Project p ; 
int pid ; 

    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;

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

    public EditSprintController() {
                con = MyDbConnection.getInstance().getConnexion();

    }
    

    @FXML
    private void SubmitButton(MouseEvent event) throws AWTException {
         if (event.getSource() == Submit) {
            // here

            if (EditS()) {
                TrayNotification tray = new TrayNotification();
                AnimationType type= AnimationType.POPUP;
                tray.setAnimationType(type);
                tray.setRectangleFill(Color.valueOf("#16cabd"));
                tray.setTitle("Scrumify App");
                tray.setMessage("Sprint edited successfully  !");
                Image img = new Image (ScrumifyD.class.getResourceAsStream("/scrumifyd/images/scrumify.png"));
                tray.setImage(img);
                //tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(3000));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit sprint");
                alert.setHeaderText("Results:");
                alert.setContentText("Edited successfully!");
                alert.showAndWait();

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edit sprint");
                alert.setHeaderText("Results:");
                alert.setContentText("ERROR!");

                alert.showAndWait();

            }

        }
    }
    public boolean EditS() {

        int res = 0;
        boolean s=false;
        String name = Name.getText();
        String description = Description.getText();
        LocalDate deadline = Deadline.getValue();
        LocalDate startDate = startdate.getValue();
       


        if (name.isEmpty() || description.isEmpty() || deadline.isBefore(LocalDate.now()) ) {
            setLblError(Color.TOMATO, "Empty/wrong credentials");

        } else {
            Sprint sprint = new Sprint(name, description, startDate, deadline );
            SprintInterface Sprints = new SprintService();
            res = Sprints.updateSprint(pid,sprint );
            if (res!=0) {
                s=true;
                setLblError(Color.GREEN, "Project Edited Successfully.Redirecting..");

            
                
            } else {
                s=false;
                setLblError(Color.RED, "ERROR");
            }

        }
        return s;

    }

 
     public void setProject(Project p){
        this.p=p;
    }
    
    public void setProjectId(int pid){
        this.pid=pid;
    }
          public void setName(String Name) {
        this.Name.setText(Name);
    }

   public void setDeadline(LocalDate deadline){
       this.Deadline.setValue(deadline);
   }
   public void setStartdate(LocalDate startdate){
       this.startdate.setValue(startdate);
   }

    public void setDescription(String Description) {
        this.Description.setText(Description);
        
    }
    @FXML
    private void back(MouseEvent event) {
        loadUI("Projects");

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
