/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import scrumifyd.GestionProjets.services.InterfaceProjet;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.Month;

import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import scrumifyd.GestionProjets.services.ProjectService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ItemController implements Initializable {

    @FXML
    public Label Name;
    @FXML
    public Label master;
    @FXML
    public Label client;
    @FXML
    public Label etat;
    @FXML
    public Label team_member;
    @FXML
    public  Label Description;
    @FXML
    public Label created_day;
    @FXML
    public Label created_month;
    @FXML
    public Label created_year;
    @FXML
    public Label deadline_day;
    @FXML
    public Label deadline_month;
    @FXML
    public Label deadline_year;
    @FXML
    public Label id;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;
    @FXML
    private AnchorPane item;
    @FXML
    private ImageView avatar;
        InterfaceProjet Projects = new ProjectService();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
   
     
            // try {
            // TODO
            
           /* List<Project>  ListP= new ArrayList();
            
            ListP = Projects.getAllProjects();
            
            ListP.forEach((Project p) -> {
               // showP(p);
            Name.setText(p.getName());
           Description.setText(p.getDescription());
            
            int dayy=p.getCreated().getDayOfMonth();
            Month monthh=p.getCreated().getMonth();
            int yearr=p.getCreated().getYear();
            
            int dayyd=p.getDuedate().getDayOfMonth();
            Month monthhd=p.getDuedate().getMonth();
            int yearrd=p.getDuedate().getYear();
            
            created_day.setText(""+dayy);
            created_month.setText(""+monthh);
            created_year.setText(""+yearr);
            
            deadline_day.setText(""+dayyd);
            deadline_month.setText(""+monthhd);
            deadline_year.setText(""+yearrd);
            
            if (p.getEtat()==1)
            {
            etat.setText("Actiive");
            }
            else if (p.getEtat()==0)
            {
            etat.setText("Archived");
            }
            });
            
            //id.setText(p.getId());
            
            
            /*   Name.setText(p.getName());
            Description.setText(p.getDescription());
            
            int dayy=p.getCreated().getDayOfMonth();
            Month monthh=p.getCreated().getMonth();
            int yearr=p.getCreated().getYear();
            
            int dayyd=p.getDuedate().getDayOfMonth();
            Month monthhd=p.getDuedate().getMonth();
            int yearrd=p.getDuedate().getYear();
            
            created_day.setText(""+dayy);
            created_month.setText(""+monthh);
            created_year.setText(""+yearr);
            
            deadline_day.setText(""+dayyd);
            deadline_month.setText(""+monthhd);
            deadline_year.setText(""+yearrd);
            
            if (p.getEtat()==1)
            {
            etat.setText("Actiive");
            }
            else if (p.getEtat()==0)
            {
            etat.setText("Archived");
            
            }
            });*/
      
         
            
                   
                    }
    
    
   public ItemController() {
   
    }

    public ItemController(String Name, int etat, String Description, int created_day, Month created_month, int created_year, int deadline_day, Month deadline_month, int deadline_year) {
                                        
        
        
                                        this.Name.setText(Name);
                                        this.Description.setText(Description);

                                        this.created_day.setText(""+ created_day);
                                        this.created_month.setText("" + created_month);
                                        this.created_year.setText("" + created_year);
                                      
                                     this.deadline_day.setText("" + deadline_day);
                                      this.deadline_month.setText("" + deadline_month);
                                      this.deadline_year.setText("" + deadline_year);
                                      
                                      if (etat == 1) {
                                          this.etat.setText("Actiive");
                                      } else if (etat == 0) {
                                          this.etat.setText("Archived");
                                      }
    }
  
  
    
     
           


           
   
            
            
       
   
}