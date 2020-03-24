
package scrumifyd.GestionProjets.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;

import java.time.Month;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.Parent;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ProjectsController implements Initializable {

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

    @FXML
    private AnchorPane item;
    @FXML
    private ImageView avatar;

    @FXML
    private VBox pnl_scroll;
    @FXML
    private Label lbl_currentprojects;
    @FXML
    private Label lbl_pending;
    @FXML
    private Label lbl_completed;
    @FXML
    private FontAwesomeIconView AddProject;
    @FXML
    private StackPane contentPane;
    
    List<Project>  ListP= new ArrayList();
    InterfaceProjet Projects = new ProjectService();




    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
        
         refreshNodes();
               
    }    

    public ProjectsController() {

    }
    
       @FXML

    private void handleButtonAction(MouseEvent event) {        
       refreshNodes();
    }
    
   
    private void refreshNodes()
    {

        
 
       
       try {
           /*  try {
           ListP = Projects.getAllProjects();
           
           System.out.println(ListP);
           
           pnl_scroll.getChildren().clear();
           Node nodes[] = new Node[ListP.size()];
           
           ListP.forEach((Project p)->{
           
           for(int i=0;i<ListP.size();i++){
           item.getChildren().clear();
           nodes[i] = showP(item,p);
           //nodes[i] = (Node)  FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/Item.fxml")) ;
           pnl_scroll.getChildren().addAll(nodes[i]);
           }
           });
           } catch (SQLException ex) {
           Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           */
           
           
           pnl_scroll.getChildren().clear();
           ListP = Projects.getAllProjects();
           
           System.out.println(ListP);
           
           Node nodes[] = new Node[ListP.size()];
           
           for (int i=0;i<ListP.size();i++){
               
               
               
               try {
                   nodes[i]= (Node) FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/Item.fxml"));
                   
                   
                   
               } catch (IOException ex) {
                   Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
               }
     
               pnl_scroll.getChildren().add(nodes[i]);
               
               /*nodes[i] = (Node) showP(item,p);
               System.out.println("testh");*/
               
               
           }
       } catch (SQLException ex) {
           Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
       }
       
        
        
     
    }
           
           @FXML
           public void AddProject(MouseEvent event) {
           
           loadUI("AddP");
       }
           
    
  public void loadUI(String ui){
      contentPane.getChildren().clear();
         Parent root = null;
       try{
           root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/"+ui+".fxml"));
           
       } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
              contentPane.getChildren().add(root);
   }

    @FXML
    private void currentProjects(MouseEvent event) {
        loadUI("ProjectsCurrent");
    }

    @FXML
    private void pendingProjects(MouseEvent event) {
                loadUI("ProjectsPending");

    }

    @FXML
    private void completedProjects(MouseEvent event) {
                loadUI("ProjectsCompleted");

    }

   
public Node showP(AnchorPane item,Project p){
                 
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
