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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import scrumifyd.GestionMeetings.models.Meeting;
import scrumifyd.util.MyDbConnection;
import scrumifyd.GestionMeetings.services.InterfaceMeeting;
import scrumifyd.GestionMeetings.services.MeetingService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class AddMeetingController implements Initializable {

    Connection con = null;
   
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
    @FXML
    private JFXComboBox Sprint;
    int user_id;
    int etat = 1;
    LocalDate today = LocalDate.now();

    ObservableList<String> listType = FXCollections.observableArrayList(
        "Daily scrum", "type2");

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
    Type.setItems(listType);
        // TODO
        //Database
        if (con == null) {
            Errors.setTextFill(Color.TOMATO);
            Errors.setText("Server Error : Check");
        } else {
            Errors.setTextFill(Color.GREEN);
            Errors.setText("Server is up : Good to go");
        //Comboboxes  
        //Teams    
            Sprint.getSelectionModel().clearSelection();
            ObservableList sprints = FXCollections.observableArrayList(fillComboBoxT());
            Sprint.setItems(sprints);
        }


 
 
 
 
 
 
  
   

    }
 public void setUserId(int user_id){
      this.user_id=user_id;
  }
    
    public AddMeetingController() {

        con = MyDbConnection.getInstance().getConnexion();
    }

    public List<String> fillComboBoxT() {

        List<String> list = new ArrayList<>();
        try {
            String query = "SELECT `id` FROM `sprint` ";

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                list.add(rs.getString("id"));

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddMeetingController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }
   

    @FXML
    public void SubmitButton(MouseEvent event) {

        if (event.getSource() == Submit) {
            // here

            if (AddM()) {

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Add Meeting");
                alert.setHeaderText("Results:");
                alert.setContentText("Added successfully!");

                alert.showAndWait();

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Add Meeting");
                alert.setHeaderText("Results:");
                alert.setContentText("ERROR!");

                alert.showAndWait();

            }

        }

    }

    public boolean AddM() {

        int res = 0;
        boolean s = false ;
        String name = Name.getText();
        String type = (String) Type.getValue(); 
        String place = Place.getText();    
        LocalDate meetingDate = MeetingDate.getValue();
        String sprint = (String) Sprint.getValue();    
        
        if (name.isEmpty() || Type.getValue()=="" || meetingDate.isBefore(LocalDate.now()) || Sprint.getValue()=="") {
            if(meetingDate.isBefore(LocalDate.now()))
            setLblError(Color.TOMATO, "Date is wrong");
                
            else
            setLblError(Color.TOMATO, "Empty/wrong credentials");

        } else {
                System.out.println("Add : "  + user_id);
                Meeting meeting = new Meeting(name, type, place,sprint, meetingDate );

                InterfaceMeeting Meetings = new MeetingService();
            try {
                res = Meetings.ajouterMeeting(meeting);
            } catch (SQLException ex) {
                Logger.getLogger(AddMeetingController.class.getName()).log(Level.SEVERE, null, ex);
            }
                if (res!=0) {
                    s=true;
                    setLblError(Color.GREEN, "Meeting Added Successfully.Redirecting..");
                     
                  
                                contentPane.getChildren().clear();

                        FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/meetings.fxml"));
                        Parent root =null;
                    try {
                        root = (Parent) loader.load();
                    } catch (IOException ex) {
                        Logger.getLogger(AddMeetingController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                        
                        contentPane.getChildren().add(root);



                 

                } else {
                    s=false ; 
                    setLblError(Color.RED, "Meeting error...");
                


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
            Logger.getLogger(AddMeetingController.class.getName()).log(Level.SEVERE, null, ex);
        }

        contentPane.getChildren().add(root);
    }

    private void setLblError(Color color, String text) {
        Errors.setTextFill(color);
        Errors.setText(text);
        System.out.println(text);
    }

   

}
