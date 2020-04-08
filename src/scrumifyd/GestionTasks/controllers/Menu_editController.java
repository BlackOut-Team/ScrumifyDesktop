/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;

import scrumifyd.GestionTasks.models.task;
import scrumifyd.GestionTasks.services.task_services;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import scrumifyd.GestionTasks.controllers.FXMLDocumentController;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class Menu_editController implements Initializable {

    @FXML
    private Button btn_edit;
    FXMLDocumentController fxd=new FXMLDocumentController();
    task_services ts=new task_services();
     
    @FXML
    private Label label_id;
    int id;
    @FXML
    private TextField hethahowapart2;
    @FXML
    private TextField title_edit1;
    @FXML
    private TextArea description_edit1;
    @FXML
    private TextField priority_edit1;
    @FXML
    private TextField etat_edit1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        hethahowapart2.setVisible(false);
    }    
    public void setid(int id){
        this.id=id;
        hethahowapart2.setText(Integer.toString(id));
    }
 
    
    @FXML
    private void btn_edit(ActionEvent event) {
        String t,des,p,et;
        int pr,eta;
        t=title_edit1.getText();
        des=description_edit1.getText();
        p=priority_edit1.getText();
        pr=Integer.parseInt(p);
        et=etat_edit1.getText();
        eta=Integer.parseInt(et);
        ts.modifier(t, pr, eta, des, id);
    }

    void settitle(String title_edit) {
     title_edit1.setText(title_edit);}

    void setdescription(String description_edit) {
      description_edit1.setText(description_edit);}

    void setetat(int eta) {
        String t=Integer.toString(eta);
        etat_edit1.setText(t);
    }
    

    void setprio(int prio) {
        String p=Integer.toString(prio);
        priority_edit1.setText(p); //To change body of generated methods, choose Tools | Templates.
    }
    
}
