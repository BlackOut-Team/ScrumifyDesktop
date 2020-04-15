/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.controllers;

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
import java.time.LocalDateTime;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import scrumifyd.GestionMeetings.models.Meeting;
import scrumifyd.GestionMeetings.services.InterfaceMeeting;
import scrumifyd.GestionMeetings.services.MeetingService;
import scrumifyd.util.MyDbConnection;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class EditMeetingController implements Initializable {

    @FXML
    private Pane contentPane;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private JFXTextField Name;
    @FXML
    private JFXButton Submit;
    @FXML
    private JFXComboBox Type;
    @FXML
    private JFXTextField Place;
    @FXML
    private JFXDatePicker MeetingDate;
    @FXML
    private Label Errors;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;
    Meeting m; 
     int mid ; 
    LocalDateTime today = LocalDateTime.now();

    ObservableList<String> listType = FXCollections.observableArrayList(
        "Daily scrum", "type2");
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            Type.setItems(listType);

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
        //Owners
    }

    public EditMeetingController() {
        con = MyDbConnection.getInstance().getConnexion();

    }
    
    public void setMeeting(Meeting m){
        this.m  =m;
    }
    
    public void setProjectId(int mid){
        this.mid=mid;
    }
 



    @FXML
    public void SubmitButton(MouseEvent event) {

        if (event.getSource() == Submit) {
            // here

            if (EditM()) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Edit Meeting");
                alert.setHeaderText("Results:");
                alert.setContentText("Edited successfully!");

                alert.showAndWait();
                loadUI("meetings");

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Edit Meeting");
                alert.setHeaderText("Results:");
                alert.setContentText("ERROR!");

                alert.showAndWait();

            }

        }

    }

    public boolean EditM() {

        int res = 0;
        boolean s=false;
        String name = Name.getText();
        String type = (String) Type.getValue(); 
        String place = Place.getText();
        LocalDate meetingDate = MeetingDate.getValue();

        if (name.isEmpty() ||Type.getValue()=="" ) {
            setLblError(Color.TOMATO, "Empty/wrong credentials");

        } else {
            try {
                Meeting meeting = new Meeting(name, type, place, meetingDate );
                InterfaceMeeting Meetings = new MeetingService();
                 
            
                res = Meetings.updateMeeting(mid,meeting );
               
                
                if (res!=0) {
                    s=true;
                    setLblError(Color.GREEN, "Meeting Edited Successfully.Redirecting..");
                    
                    loadUI("meetings");
                    
                    
                    
                    
                } else {
                    s=false;
                    setLblError(Color.RED, "ERROR");
                }
            } catch (SQLException ex) {
                Logger.getLogger(EditMeetingController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return s;

    }

    @FXML
    private void back(MouseEvent event) {
        loadUI("meetings");

    }

    public void loadUI(String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionMeetings/views/" + ui + ".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(EditMeetingController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setPlace(String Place) {
        this.Place.setText(Place);
    }
    
    public void setMeetingDate(LocalDate MeetingDate) {
        this.MeetingDate.setValue(MeetingDate);
    }

   

   
    



  

 

}
