package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.AWTException;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import scrumifyd.GestionMeetings.models.Activity;
import scrumifyd.GestionMeetings.services.ActivityService;
import scrumifyd.GestionMeetings.services.InterfaceActivity;
import scrumifyd.util.MyDbConnection;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.SprintInterface;
import scrumifyd.GestionProjets.services.SprintService;
import scrumifyd.GestionTeams.models.Team;
import scrumifyd.GestionUsers.models.User;
import scrumifyd.GestionUsers.services.SigninController;
import scrumifyd.ScrumifyD;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

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
            List<Team> Tlist = fillComboBoxT();
            ObservableList teams = FXCollections.observableArrayList();
            Tlist.forEach((t) -> {
                teams.add(t.getName());
            });
            Team.setItems(teams);
            //Owners
            PO.getSelectionModel().clearSelection();
            List<User> Olist = fillComboBoxO();
            ObservableList owners = FXCollections.observableArrayList();
            Olist.forEach((o) -> {
                owners.add(o.getName());
            });
            PO.setItems(owners);
        }

    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public AddPController() {

        con = MyDbConnection.getInstance().getConnexion();
    }

    public List<Team> fillComboBoxT() {

        List<Team> list = new ArrayList<>();
        try {
            String query = "SELECT `name`, `id` FROM `team` ";

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {

                Team t = new Team(rs.getInt("id"), rs.getString("name"));
                list.add(t);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    public List<User> fillComboBoxO() {

        List<User> list = new ArrayList<>();

        try {
//                        String query = "SELECT t.user_id , u.name FROM user u ,team_user t where t.role=3 and t.team_id="+team_id+" and u.id=t.user_id";

            String query = "SELECT `name` , `id` FROM `person` ";

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                User o = new User(rs.getInt("id"), rs.getString("name"));
                list.add(o);

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

            if (AddP()) {
                SigninController s = new SigninController();
                int user_id = SigninController.user.getUserId();
                TrayNotification tray = new TrayNotification();
                AnimationType type = AnimationType.POPUP;
                tray.setAnimationType(type);
                tray.setTitle("Scrumify App");
                tray.setMessage("New project Added !");

                Image img = new Image(ScrumifyD.class.getResourceAsStream("/scrumifyd/images/scrumify.png"));
                tray.setImage(img);
                //tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(3000));

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

        int res;
        boolean s = false;
        String name = Name.getText();
        String description = Description.getText();
        LocalDate deadline = Deadline.getValue();
        Team t = fillComboBoxT().get(Team.getSelectionModel().getSelectedIndex());
        User o = fillComboBoxO().get(PO.getSelectionModel().getSelectedIndex());

        if (name.isEmpty() || description.isEmpty() || deadline.isBefore(LocalDate.now()) || Team.getValue() == "" || PO.getValue() == "") {
            setLblError(Color.TOMATO, "Empty/wrong credentials");

        } else {
            try {
                System.out.println("Add : " + user_id);
                Project project = new Project(name, description, today, deadline, etat, t.getId(), o.getId(), user_id);
                InterfaceActivity a = new ActivityService();
                Activity ac = new Activity("vient d ajouter un nouveau projet pour votre équipe", 0, user_id);

                a.ajouterActivity(ac);
                InterfaceProjet Projects = new ProjectService();
                res = Projects.addProject(project);
                Project pp = Projects.getProject(res);
                if (res != 0) {
                    s = true;

                    setLblError(Color.GREEN, "Project Added Successfully.Redirecting..");
                    SprintInterface sprint = new SprintService();

                    int s1 = sprint.sprintSuggest1(project);
                    int s2 = sprint.sprintSuggest2(project);

                    try {
                        contentPane.getChildren().clear();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/SprintsSuggest.fxml"));
                        Parent root = (Parent) loader.load();
                        SprintSController sp = loader.getController();
                        sp.setLabels(s1, s2);
                        sp.setProject(pp);
                        sp.setProjectId(pp.getId());

                        contentPane.getChildren().add(root);

                    } catch (IOException ex) {
                        Logger.getLogger(AddPController.class.getName()).log(Level.SEVERE, null, ex);
                    }

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
