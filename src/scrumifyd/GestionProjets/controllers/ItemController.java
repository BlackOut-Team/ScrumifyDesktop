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
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.util.MyDbConnection;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ItemController implements Initializable {

    @FXML
    private Label Name;
    @FXML
    private Label master;
    @FXML
    private Label client;
    @FXML
    private Label etat;
    @FXML
    private Label team_member;
    @FXML
    private Label Description;
    @FXML
    private Label created_day;
    @FXML
    private Label created_month;
    @FXML
    private Label created_year;
    @FXML
    private Label deadline_day;
    @FXML
    private Label deadline_month;
    @FXML
    private Label deadline_year;
    @FXML
    private Label id;
    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;
    @FXML
    private AnchorPane item;
    @FXML
    private ImageView avatar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // try {
            // TODO
            
         /*   List<Project>  ListP= new ArrayList();
            InterfaceProjet Projects = new ProjectService();
            ListP = Projects.getAllProjects();
            
            
                
            
            ListP.forEach((Project p) -> {
                                showP(p);
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
                con = MyDbConnection.getInstance().getConnexion();

    }
   public Node showP(Project p){
                 
                item.setId("item");
                //id.setText(p.getId());
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

                item.getChildren().addAll(id , Name,Description, created_day, created_month, created_year);
                
                return item;


   }
            
  
    
     
           


           
   
            
            
       
   
}