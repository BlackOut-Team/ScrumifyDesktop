/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.models.Sprint;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.services.SprintInterface;
import scrumifyd.GestionProjets.services.SprintService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ShowSprintController implements Initializable {

    @FXML
    private StackPane contentPane;
    @FXML
    private FontAwesomeIconView back;
   
    @FXML
    Label pid;
    @FXML
    private FontAwesomeIconView AddSprint;
    @FXML
     HBox sprint_scroll;

   int pp ;

    List<Sprint> ListS = new ArrayList();
    SprintInterface Sprints = new SprintService();
    Project p;
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

      
        refreshNodes();

    }

    public ShowSprintController() {

    }

    public void refreshNodes() {


    
            sprint_scroll.getChildren().clear();
            
            
            Node nodes[] = new Node[ListS.size() + 1];
            
            ListS.forEach((Sprint s)-> {
                
                int i = ListS.indexOf(s);
              
                                    i++;

                if (s.getEtat() == 1 ) {
                    try {
                        FXMLLoader loader = new FXMLLoader(ShowSprintController.this.getClass().getResource("/scrumifyd/GestionProjets/views/ItemS.fxml"));
                        //separate date into separate day month year for both dates
                        int dayy = s.getCreated().getDayOfMonth();
                        Month monthh = s.getCreated().getMonth();
                        int yearr = s.getCreated().getYear();
                        int dayyd = s.getDuedate().getDayOfMonth();
                        Month monthhd = s.getDuedate().getMonth();
                        int yearrd = s.getDuedate().getYear();
                        nodes[i] = loader.load();
                        ItemSController item = loader.getController();
                        item.setId(s.getId());
                        item.setName(s.getName());
                        item.setDescription(s.getDescription());
                        //item.setEtat(s.getEtat());
                        item.setCreated_day(dayy);
                        item.setDeadline_month(monthh);
                        item.setCreated_year(yearr);
                        item.setDeadline_day(dayyd);
                        item.setDeadline_month(monthhd);
                        item.setDeadline_year(yearrd);
                        sprint_scroll.getChildren().addAll(nodes[i]);
                        EventHandler<MouseEvent> editHandler = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                if (e.getSource() == item.EditButton) {
                                    try {
                                        FXMLLoader loader2 = new FXMLLoader(ShowSprintController.this.getClass().getResource("/scrumifyd/GestionProjets/views/EditS.fxml"));
                                        
                                        contentPane.getChildren().clear();
                                        Parent root = (Parent) loader2.load();
                                        EditSprintController sp = loader2.getController();
                                        
                                        sp.setProject(p);
                                        System.out.println(s.getId());
                                        sp.setProjectId(s.getId());
                                        sp.setName(s.getName());
                                        sp.setDescription(s.getDescription());
                                        sp.setStartdate(s.getCreated());
                                        sp.setDeadline(s.getDuedate());
                                        contentPane.getChildren().add(root);
                                    } catch (IOException ex) {
                                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                
                            }
                        };
                        
                        EventHandler<MouseEvent> archiveHandler = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                if (e.getSource() == item.ArchiveButton) {
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive sprint", ButtonType.YES, ButtonType.CANCEL);
                                    alert.setTitle("Archive sprint");
                                    alert.setHeaderText("Are you sure to archive this project ?");
                                    
                                    Optional<ButtonType> result = alert.showAndWait();
                                    
                                    if (result.get() == ButtonType.YES) {
                                        SprintInterface sr = new SprintService();
                                       
                                        
                                        Sprint sprint = new Sprint(0);
                                        if (sr.archiveSprint(s.getId())) {
                                            
                                            System.out.println("done");
                                            alert.hide();
                                            refreshNodes();
                                            
                                            
                                        } else {
                                            System.out.println("error");
                                            alert.hide();
                                            
                                        }

                                        System.out.println("Ok pressed");
                                        
                                    } else {
                                        System.out.println("canceled");
                                        refreshNodes();
                                        
                                    }
                                    
                                }
                            }
                        };                      
                        EventHandler<MouseEvent> showSprintHandler = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                
                                
                            }
                        };
                        
                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                    } catch (IOException ex) {
                        Logger.getLogger(ShowSprintController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                } else {
                    i++;
                }
                
                
            });
      
            
    
       

    }
//
//    public List<Sprint> getSprints() throws SQLException {
//        List<Sprint> Sprints = new ArrayList<>();
//        SprintInterface sp = new SprintService();
//        String ppp=this.pid.getText();
//        System.out.println(ppp);
//         int pidd = Integer.parseInt(ppp);
//         System.out.println(pidd);
//        Sprints = sp.getAllSprints(pidd);
//
//        return Sprints;
//    }
public String getProjectId(){
    String pi = this.pid.getText();
    int pp = Integer.parseInt(pi);
    return pi ;
}
    @FXML
    private void back(MouseEvent event) {
        loadUI("ProjectsCurrent");

    }

    public void setProject(Project p) {
        this.p = p;
    }

    public void setProjectId(int pid) {
        this.pp=pid;
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

    @FXML
    private void AddProject(MouseEvent event) {
    }

}
