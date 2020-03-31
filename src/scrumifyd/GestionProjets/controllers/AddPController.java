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
import scrumifyd.util.MyDbConnection;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.SprintInterface;
import scrumifyd.GestionProjets.services.SprintService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class AddPController implements Initializable {

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
    private JFXTextArea Description;
    @FXML
    private JFXDatePicker Deadline;
    @FXML
    private Label Errors;
    @FXML
    private JFXComboBox Team;
    @FXML
    private JFXComboBox PO;
    int user_id;
    int etat = 1;
    LocalDate today = LocalDate.now();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            Team.getSelectionModel().clearSelection();
            ObservableList teams = FXCollections.observableArrayList(fillComboBoxT());
            Team.setItems(teams);
        //Owners
            PO.getSelectionModel().clearSelection();
            ObservableList owners = FXCollections.observableArrayList(fillComboBoxO());
            PO.setItems(owners);
        }


 
 
 
 
 
 
  
   

    }
 public void setUserId(int user_id){
      this.user_id=user_id;
  }
    
    public AddPController() {

        con = MyDbConnection.getInstance().getConnexion();
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
    public void SubmitButton(MouseEvent event) {

        if (event.getSource() == Submit) {
            // here

            if (AddP()) {

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Add project");
                alert.setHeaderText("Results:");
                alert.setContentText("Added successfully!");

                alert.showAndWait();

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

        int res = 0;
        boolean s = false ;
        String name = Name.getText();
        String description = Description.getText();   
        LocalDate deadline = Deadline.getValue();
        String team = (String) Team.getValue();     
        String productowner = (String) PO.getValue();
        int team_id=0;
        int owner_id=0;
        
        if (name.isEmpty() || description.isEmpty() || deadline.isBefore(LocalDate.now()) || Team.getValue()==""|| PO.getValue()=="") {
            setLblError(Color.TOMATO, "Empty/wrong credentials");

        } else {
            try {
                System.out.println("Add : "  + user_id);
                Project project = new Project(name, description, today,deadline, etat );

                InterfaceProjet Projects = new ProjectService();
                res = Projects.addProject(project);
                Project  pp = Projects.getProject(res);
                if (res!=0) {
                    s=true;
                    setLblError(Color.GREEN, "Project Added Successfully.Redirecting..");
                     SprintInterface sprint = new SprintService(); 

                     int s1=    sprint.sprintSuggest1(project);
                     int s2=    sprint.sprintSuggest2(project);
                  
      
                    try {
                                contentPane.getChildren().clear();

                        FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/SprintsSuggest.fxml"));
                        Parent root = (Parent) loader.load();
                        SprintSController sp= loader.getController();
                        sp.setLabels(s1, s2);
                        sp.setProject(pp);
                        sp.setProjectId(pp.getId());
                        
                        contentPane.getChildren().add(root);



                    } catch (IOException ex) {
                        Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                 

                } else {
                    s=false ; 
                    setLblError(Color.RED, "Project error...");
                }

            } catch (SQLException ex) {
                Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
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

   

}
