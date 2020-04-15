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
import doryan.windowsnotificationapi.fr.Notification;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.ScrumifyD;
import scrumifyd.util.MyDbConnection;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

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
    private JFXComboBox Team;
    @FXML
    private JFXComboBox PO;
    @FXML
    private Label Errors;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;
Project p ; 
int pid ; 
    int etat = 1;
    LocalDate today = LocalDate.now();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
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
        
            //Comboboxes  
        //Teams    
            Team.getSelectionModel().clearSelection();
            ObservableList teams = FXCollections.observableArrayList(fillComboBoxT());
            Team.setItems(teams);
        //Owners
            PO.getSelectionModel().clearSelection();
            ObservableList owners = FXCollections.observableArrayList(fillComboBoxO());
            PO.setItems(owners);
    }

    public EditPController() {
        con = MyDbConnection.getInstance().getConnexion();

    }
    
    public void setProject(Project p){
        this.p=p;
    }
    
    public void setProjectId(int pid){
        this.pid=pid;
    }
 public List<String> fillComboBoxT() {

        List<String> list = new ArrayList<>();
        try {
            String query = "SELECT `name` FROM `team` ";

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                list.add(rs.getString("name"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
    public List<String> fillComboBoxO() {

        List<String> list = new ArrayList<>();
        System.out.println("test");
        try {
            String query = "SELECT `name` FROM `person` ";

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                list.add(rs.getString("name"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }



    @FXML
    public void SubmitButton(MouseEvent event) throws AWTException {

        if (event.getSource() == Submit) {
            // here

            if (EditP()) {
                TrayNotification tray = new TrayNotification();
                AnimationType type= AnimationType.POPUP;
                tray.setAnimationType(type);
                tray.setRectangleFill(Color.valueOf("#16cabd"));
                tray.setTitle("Scrumify App");
                tray.setMessage("Project edited successfully  !");
                Image img = new Image (ScrumifyD.class.getResourceAsStream("/scrumifyd/images/scrumify.png"));
                tray.setImage(img);
                //tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(3000));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit project");
                alert.setHeaderText("Results:");
                alert.setContentText("Edited successfully!");
                alert.showAndWait();
                loadUI("Projects");

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

        int res = 0;
        boolean s=false;
        String name = Name.getText();
        String description = Description.getText();
        LocalDate deadline = Deadline.getValue();
        String team = (String) Team.getValue();     
        String productowner = (String) PO.getValue();
        // int team_id=0;
        // int owner_id=0;
        // int master_id=0;


        if (name.isEmpty() || description.isEmpty() || deadline.isBefore(LocalDate.now()) || Team.getValue()==""|| PO.getValue()=="") {
            setLblError(Color.TOMATO, "Empty/wrong credentials");

        } else {
            try {
                Project project = new Project(name, description, deadline );
                InterfaceProjet Projects = new ProjectService();
                 
            
                res = Projects.updateProject(pid,project );
               
                
                if (res!=0) {
                    s=true;
                    setLblError(Color.GREEN, "Project Edited Successfully.Redirecting..");
                    
                    loadUI("ProjectsCurrent");
                    
                    
                    
                    
                } else {
                    s=false;
                    setLblError(Color.RED, "ERROR");
                }
            } catch (SQLException ex) {
                Logger.getLogger(EditPController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return s;

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
        public void setName(String Name) {
        this.Name.setText(Name);
    }

   public void setDeadline(LocalDate deadline){
       this.Deadline.setValue(deadline);
   }


    public void setDescription(String Description) {
        this.Description.setText(Description);
        
    }

   
    



  

 

}
