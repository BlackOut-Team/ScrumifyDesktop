/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;

import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import scrumifyd.GestionTasks.models.task;
import scrumifyd.GestionTasks.services.members_services;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import scrumifyd.GestionTasks.services.task_services;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import scrumifyd.GestionProjets.controllers.DashboardController;

/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class DetailsController implements Initializable {

    task task = new task();
    task_services ts = new task_services();
    members_services ms = new members_services();
    @FXML
    private ListView<String> l_members;
    @FXML
    private Label created;
    @FXML
    private Label updated;
    @FXML
    private Label priority;
    @FXML
    private Label finished;

    String i;
    task t;
    int id;
    @FXML
    private Pane contentPane;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private Label Errors;
    @FXML
    private JFXTextArea Description;
    @FXML
    private Label Title;
    @FXML
    private Label etat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setid(int id) {
        this.id = id;
        i = Integer.toString(id);
        System.out.println("id el set: " + i);
    }

    public void settext(int id) {
        String t = Integer.toString(id);
    }

    void settask(task t) {
        this.t = t;
    }

    void settitle(String title_edit) {
        Title.setText(title_edit);
    }

    void setEtat(int etat) {
        if (etat == 1) {
            this.etat.setStyle("-fx-text-fill : green;");

            this.etat.setText("Active");
        } else {
            this.etat.setStyle("-fx-text-fill : red;");

            this.etat.setText("Archived");
        }

    }

    void setPriority(int pr) {
        if (pr == 3) {
            this.priority.setStyle("-fx-text-fill : red;");
            this.priority.setText("Important");
        } else if (pr < 3) {
            this.priority.setStyle("-fx-text-fill : green;");

            this.priority.setText("Normal");
        }
    }

    void setdescription(String description_edit) {
        Description.setText(description_edit);
    }

    void setCreated(LocalDate created) {
        this.created.setText("" + created);
    }

    void setUpdated(LocalDate updated) {
        this.updated.setText("" + updated);

    }

    void setFinished(LocalDate finished) {
        this.finished.setText("" + finished);

    }

    @FXML
    private void back(MouseEvent event) {
        loadUI("Taskss");
    }

    public void loadUI(String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionTasks/views/" + ui + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contentPane.getChildren().add(root);
    }

    void setlist(String title) {

        String member = ts.get_members(title);
        if (member.equals("")) {
            System.out.println("pas de membres");
        } else {
            final String SEPARATEUR = ",";

            String mots[] = member.split(SEPARATEUR);
            List<String> names = new ArrayList<>();

            for (int i = 0; i < mots.length; i++) {
                names.add(ms.get_name(Integer.valueOf(mots[i])));
            }
            ObservableList<String> namesmembers = FXCollections.observableArrayList(names);
            l_members.setItems(namesmembers);
        }

    }

}
