/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionTasks.models.task;
import scrumifyd.GestionTasks.services.task_services;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class EditTController implements Initializable {

    @FXML
    private Pane contentPane;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private JFXTextField Title;
    @FXML
    private JFXTextArea Description;

    @FXML

    private Spinner<Integer> etat;

    @FXML

    private Spinner<Integer> Priority;
    task_services ts = new task_services();
    task p;
    private TextField hethahowapart2;
    int idd;
    @FXML
    private JFXButton btn_edit;
    @FXML
    private Label Errors;
        final int initialValue = 0;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
 
        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1, initialValue);
 //Value factory.
        SpinnerValueFactory<Integer> valueFactory1 = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, initialValue);
        etat.setValueFactory(valueFactory);
                Priority.setValueFactory(valueFactory1);


    }

    public EditTController() {

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

    public void setTask(task p) {
        this.p = p;
    }

    public void setid(int id) {
        this.idd = id;
        //hethahowapart2.setText(Integer.toString(id));
    }

    @FXML
    private void btn_edit(ActionEvent event) {
        String t, des;
        int pr, eta;

        t = Title.getText();
        des = Description.getText();
        pr = Priority.getValue();
        eta = etat.getValue();
        ts.modifier(t, pr, eta, des, idd);
        loadUI("Taskss");
    }

    void settitle(String title_edit) {
        Title.setText(title_edit);
    }
    void setEtat(int etat){
        Object newValue =etat;
         this.etat.getValueFactory().setValue((Integer) newValue);

    }
  void setPriority(int pr){
         this.Priority.getValueFactory().setValue(pr);

    }    void setdescription(String description_edit) {
        Description.setText(description_edit);
    }

    void setetat(int eta) {
        String t = Integer.toString(eta);
        //etat.set
    }

    void setprio(int prio) {
        String p = Integer.toString(prio);
        // Priority.(p); //To change body of generated methods, choose Tools | Templates.
    }

}
