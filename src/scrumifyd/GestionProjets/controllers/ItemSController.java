/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ItemSController implements Initializable {

    @FXML
    private Label Name;
    @FXML
    private Label id;
    @FXML
    private Label created_day;
    @FXML
    private Label deadline_day;
    @FXML
    private Label created_month;
    @FXML
    private Label created_year;
    @FXML
    private Label deadline_month;
    @FXML
    private Label deadline_year;
    @FXML
    private Label Description;
    @FXML
    public FontAwesomeIconView EditButton;
    @FXML
    public FontAwesomeIconView ArchiveButton;
    @FXML
    public FontAwesomeIconView LinkButton;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        public void setName(String Name) {
        this.Name.setText(Name);
    }


//    public void setEtat(int etat) {
//        if (etat == 1) {
//            this.etat.setText("Active project");
//        } else {
//            this.etat.setText("Archived project");
//
//        }
//    }

    public void setDescription(String Description) {
        this.Description.setText(Description);
    }

    public void setCreated_day(int created_day) {
        this.created_day.setText("" + created_day);
    }

    public void setCreated_month(Month created_month) {
        this.created_month.setText("" + created_month);
    }

    public void setCreated_year(int created_year) {
        this.created_year.setText("" + created_year);
    }

    public void setDeadline_day(int deadline_day) {
        this.deadline_day.setText("" + deadline_day);
    }

    public void setDeadline_month(Month deadline_month) {
        this.deadline_month.setText("" + deadline_month);
    }

    public void setDeadline_year(int deadline_year) {
        this.deadline_year.setText("" + deadline_year);
    }



  


}
