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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import scrumifyd.util.MyDbConnection;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class AddPController implements Initializable {

    Connection con = null;
    PreparedStatement pst = null;
    String resultSet = null;

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
    private JFXComboBox<String> Team;
    @FXML
    private JFXComboBox PO;
    
    private ObservableList<String> teams = FXCollections.observableArrayList();
    private ObservableList<String> owners = FXCollections.observableArrayList();

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


       // fillComboBoxT(teams);
       


        

    }

    public AddPController() {    
        
                    con = MyDbConnection.getInstance().getConnexion();
    }
    public   void  fillComboBoxT(ObservableList<String> teams){
       
            String query = "select `name` from `team` ";
             try {
      
            Statement stm = con.createStatement();
            ResultSet rs = pst.executeQuery(query);
            System.out.println("test");
            while(rs.next()) {
                System.out.println("test1");
                teams.add(rs.getString("name"));
                System.out.println(teams);

            }
             Team.setItems(teams);
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    @FXML
    public void SubmitButton(MouseEvent event) {

        if (event.getSource() == Submit) {
            // here

            if (AddP()) {

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Add project");
                alert.setHeaderText("Results:");
                alert.setContentText("Added successfully!");

                alert.showAndWait();
                loadUI("ProjectsCurrent");

            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Add project");
                alert.setHeaderText("Results:");
                alert.setContentText("ERROR!");

                alert.showAndWait();

            }

        }

    }

    public boolean AddP() {

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
            try {
                Project project = new Project(name, description, today, deadline, etat);

                InterfaceProjet Projects = new ProjectService();
                res = Projects.addProject(project);

                if (res) {
                    setLblError(Color.GREEN, "Project Added Successfully.Redirecting..");

                } else {
                    setLblError(Color.GREEN, "Project Added Successfully.Redirecting..");
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
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
