package scrumifyd.GestionMeetings.controllers;

import com.google.maps.model.AddressComponentType;
import com.google.maps.model.PlaceDetails;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import scrumifyd.GestionMeetings.models.AutoCompleteAddressField;
import scrumifyd.GestionMeetings.models.AutoCompleteAddressField.AddressPrediction;
import scrumifyd.GestionMeetings.models.Meeting;
import scrumifyd.util.MyDbConnection;
import scrumifyd.GestionMeetings.services.InterfaceMeeting;
import scrumifyd.GestionMeetings.services.MeetingService;
import scrumifyd.GestionProjets.models.Sprint;
import scrumifyd.GestionProjets.services.ProjectSession;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
//private static final String API_KEY = ;
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
    AutoCompleteAddressField Places = new AutoCompleteAddressField();

    static public int pr_id;

    ObservableList<String> listType = FXCollections.observableArrayList(
            "Daily scrum", "type2");

    private AutoCompleteAddressField Place1 = new AutoCompleteAddressField();
    @FXML
    private VBox root;
    private Label result;
    public static ProjectSession projectSess = ProjectSession.getInstace(pr_id)  ;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override

    public void initialize(URL url, ResourceBundle rb) {

        Place1.setBackground(Background.EMPTY);
        Place1.setPromptText("Place");
        Place1.setStyle("fx-background-color:transparent;");
        Place1.setStyle("-fx-border-color:#16cabd");

        Place1.getEntryMenu().setOnAction((ActionEvent e)
                -> {
            ((MenuItem) e.getTarget()).addEventHandler(Event.ANY, (Event event)
                    -> {
                if (Place1.getLastSelectedObject() != null) {
                    Place1.setText(Place1.getLastSelectedObject().toString());
                    PlaceDetails place = AutoCompleteAddressField.getPlace((AddressPrediction) Place1.getLastSelectedObject());
                    if (place != null) {
                        Place1.setText(AutoCompleteAddressField.getComponentLongName(place.addressComponents, AddressComponentType.COUNTRY));
                    } else {
                        Place1.clear();
                    }
                }
            });
        });

        root.getChildren().addAll(Place1);

        Type.setItems(listType);

        Sprint.getSelectionModel().clearSelection();
            List<Sprint> Tlist = fillComboBoxT();
            ObservableList sprints = FXCollections.observableArrayList();
            if(!Tlist.isEmpty()){
            Tlist.forEach((t) -> {
                sprints.add(t.getName());
            });
            }
            else {
                Errors.setText("Le projet doit avoir au minimum un sprint pour créér une réunion !");
                Errors.setStyle("-fx-text-fill:red;");
                Name.setEditable(false);
                Place1.setEditable(false);
                Type.setEditable(false);
                MeetingDate.setEditable(false);
                Sprint.setEditable(false);
                
                
           }
            Sprint.setItems(sprints);
    }

// final WebView webView;
//        webView = new WebView(webEngine);
    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setProjectId(int pr_id) {
        this.pr_id = pr_id;
    }

    public AddMeetingController() {

        con = MyDbConnection.getInstance().getConnexion();
    }

 public List<Sprint> fillComboBoxT() {

        List<Sprint> list = new ArrayList<>();
        try {
            String query = "SELECT `name`, `id` FROM `sprint` where project_id="+projectSess.getProjectId()+"";

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
               
               
               Sprint s = new Sprint(rs.getInt("id"), rs.getString("name"));
               list.add(s);

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
        boolean s = false;
        String name = Name.getText();
        String type = (String) Type.getValue();
        String place = Place1.getText();
        LocalDate meetingDate = MeetingDate.getValue();
        Sprint sprint = fillComboBoxT().get(Sprint.getSelectionModel().getSelectedIndex());

        if (name.isEmpty() || Type.getValue() == "" || meetingDate.isBefore(LocalDate.now()) || Sprint.getValue() == "") {
            if (meetingDate.isBefore(LocalDate.now())) {
                setLblError(Color.TOMATO, "Date is wrong");
            } else {
                setLblError(Color.TOMATO, "Empty/wrong credentials");
            }

        } else {
            System.out.println("Add : " + user_id);
            Meeting meeting = new Meeting(name, place, type, sprint.getId() , meetingDate);

            InterfaceMeeting Meetings = new MeetingService();
            try {
                res = Meetings.ajouterMeeting(meeting);
            } catch (SQLException ex) {
                Logger.getLogger(AddMeetingController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (res != 0) {
                s = true;
                setLblError(Color.GREEN, "Meeting Added Successfully.Redirecting..");

                contentPane.getChildren().clear();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionMeetings/views/meetings.fxml"));
                Parent root = null;
                try {
                    root = (Parent) loader.load();
                } catch (IOException ex) {
                    Logger.getLogger(AddMeetingController.class.getName()).log(Level.SEVERE, null, ex);
                }

                contentPane.getChildren().add(root);

            } else {
                s = false;
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
