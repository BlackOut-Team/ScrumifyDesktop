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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.models.Sprint;

import scrumifyd.GestionProjets.services.SprintInterface;
import scrumifyd.GestionProjets.services.SprintService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class SprintSController implements Initializable {

    @FXML
    private Pane contentPane;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private AnchorPane suggestion1;
    @FXML
    private AnchorPane suggestion2;
    @FXML
    private AnchorPane custom;
    @FXML
    private Label lbl_suggestion1;
    @FXML
    private Label lbl_suggestion2;

    Project p;
    int pid;

    int weeks;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void setLabels(int suggestion1, int suggestion2) {

        this.lbl_suggestion1.setText("" + suggestion1);
        this.lbl_suggestion2.setText("" + suggestion2);
    }

    public void setProject(Project p) {
        this.p = p;
    }

    public void setProjectId(int id) {
        this.pid = pid;
    }

    @FXML
    private void selectFirst(MouseEvent event) throws SQLException {
        try {
            String value = this.lbl_suggestion1.getText();
            int num = Integer.valueOf(value);
            weeks = 0;
            for (int i = 0; i < num; i++) {
                
                SprintInterface spr = new SprintService();
                Sprint s = new Sprint(1,p.getId());
                spr.addDefaultSprint(s, p.getCreated(), weeks);
                weeks += 4;
                
            }
            
            contentPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/Sprints.fxml"));
            Parent root = (Parent) loader.load();
            ShowSprintController sp = loader.getController();
            
            loadS(sp);
            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(SprintSController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void loadS(ShowSprintController sp){
        try {
            sp.setProject(p);
            sp.setProjectId(pid);
            sp.pid.setText("" + p.getId());
            FXMLLoader loader1 = new FXMLLoader(SprintSController.this.getClass().getResource("/scrumifyd/GestionProjets/views/Sprints.fxml"));
            
            contentPane.getChildren().clear();
            Parent root = (Parent) loader1.load();

            SprintInterface Sprints = new SprintService();
            try {
                sp.ListS = Sprints.getAllSprints(p.getId());
            } catch (SQLException ex) {
                Logger.getLogger(SprintSController.class.getName()).log(Level.SEVERE, null, ex);
            }

            sp.sprint_scroll.getChildren().clear();

            System.out.println(sp.ListS);

            Node nodes[] = new Node[sp.ListS.size() + 1];

            sp.ListS.forEach((Sprint s) -> {

                int i = sp.ListS.indexOf(s);

                i++;

                if (s.getEtat() == 1) {
                    try {
                        FXMLLoader loader = new FXMLLoader(SprintSController.this.getClass().getResource("/scrumifyd/GestionProjets/views/ItemS.fxml"));
                        //separate date into separate day month year for both dates
                        int dayy = s.getCreated().getDayOfMonth();
                        Month monthh = s.getCreated().getMonth();
                        int yearr = s.getCreated().getYear();
                        int dayyd = s.getDuedate().getDayOfMonth();
                        Month monthhd = s.getDuedate().getMonth();
                        int yearrd = s.getDuedate().getYear();
                        nodes[i] = loader.load();
                        ItemSController item = loader.getController();
                        //item.setId(s.getId());
                        item.setName(s.getName());
                        item.setDescription(s.getDescription());
                        //item.setEtat(s.getEtat());
                        item.setCreated_day(dayy);
                        item.setDeadline_month(monthh);
                        item.setCreated_year(yearr);
                        item.setDeadline_day(dayyd);
                        item.setDeadline_month(monthhd);
                        item.setDeadline_year(yearrd);
                        sp.sprint_scroll.getChildren().addAll(nodes[i]);
                        EventHandler<MouseEvent> editHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.EditButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(SprintSController.this.getClass().getResource("/scrumifyd/GestionProjets/views/EditS.fxml"));
                                    contentPane.getChildren().clear();
                                    Parent root1 = (Parent) loader2.load();
                                    EditSprintController sp1 = loader2.getController();
                                    sp1.setProject(p);
                                    System.out.println(s.getId());
                                    sp1.setProjectId(s.getId());
                                    sp1.setName(s.getName());
                                    sp1.setDescription(s.getDescription());
                                    sp1.setStartdate(s.getCreated());
                                    sp1.setDeadline(s.getDuedate());
                                    contentPane.getChildren().add(root1);
                                }catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };

                        EventHandler<MouseEvent> archiveHandler = (MouseEvent e) -> {
                            
                            if (e.getSource() == item.ArchiveButton) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive sprint", ButtonType.YES, ButtonType.CANCEL);
                                alert.setTitle("Archive sprint");
                                alert.setHeaderText("Are you sure to archive this project ?");
                                
                                Optional<ButtonType> result = alert.showAndWait();
                                
                                if (result.get() == ButtonType.YES) {
                                    SprintInterface sr = new SprintService();
                                    //Project pp;
                                    
                                    //String idd = id.getText();
                                    Sprint sprint = new Sprint(0);
                                    if (sr.archiveSprint(s.getId())) {
                                        
                                        System.out.println("done");
                                        alert.hide();
                                        //refreshNodes();

                                    } else {
                                        System.out.println("error");
                                        alert.hide();
                                        
                                    }

                                    System.out.println("Ok pressed");
                                    
                                } else {
                                    System.out.println("canceled");
                                    // refreshNodes();
                                    
                                }
                                
                            }
                        };
                        EventHandler<MouseEvent> showSprintDetailsHandler = (MouseEvent e) -> {
                                                         if (e.getSource() == item.LinkButton) {

                                                             try {
                                                                 contentPane.getChildren().clear();
                                                                 FXMLLoader loaderr = new FXMLLoader(SprintSController.this.getClass().getResource("/scrumifyd/GestionTasks/views/FXMLDocument.fxml"));
                                                                 
                                                                 
                                                                 Parent root1 = (Parent) loaderr.load();
                                                                 
                                                                 contentPane.getChildren().add(root);
                                                             } catch (IOException ex) {
                                                                 Logger.getLogger(SprintSController.class.getName()).log(Level.SEVERE, null, ex);
                                                             }

                             }
                        };

                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                        item.LinkButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showSprintDetailsHandler);

                    } catch (IOException ex) {
                        Logger.getLogger(ShowSprintController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    i++;
                }

            });
            
            
//        System.out.println(sp.ListS);
//        sp.pp += p.getId();
//        //sp.setProjectId(p.getId());
//        contentPane.getChildren().add(root);
//
//
//
//
//                            item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
//                            item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
//                            item.showSprintsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showSprintHandler);
//
//                        } else {
//                            i++;
        } catch (IOException ex) {
            Logger.getLogger(SprintSController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        


                   
    }
    @FXML
        private void selectSecond(MouseEvent event) throws SQLException{
        try {
            String value = this.lbl_suggestion1.getText();
            int num = Integer.valueOf(value);
            weeks = 0;
            for (int i = 0; i < num; i++) {
                
             
                    SprintInterface spr = new SprintService();
                    Sprint s = new Sprint(p.getId());
                    spr.addDefaultSprint(s, p.getCreated(), weeks);
                    weeks += 2;
               
            }
            contentPane.getChildren().clear();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/Sprints.fxml"));
            Parent root = (Parent) loader.load();
            ShowSprintController sp = loader.getController();
            
            loadS(sp);
            contentPane.getChildren().add(root);
        

} catch (IOException ex) {
            Logger.getLogger(SprintSController.class
.getName()).log(Level.SEVERE, null, ex);
        }
    }




    @FXML
        private void selectCustom(MouseEvent event) {
        try {
            contentPane.getChildren().clear();
            FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/AddSprint.fxml"));
            Parent root = (Parent) loader.load();
            AddSprintController sp=  loader.getController();         
            sp.setProjectId(p.getId());
            contentPane.getChildren().add(root);
        



} catch (IOException ex) {
            Logger.getLogger(SprintSController.class

.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    private void back(MouseEvent event) {
        loadUI("AddP");

    }

    public void loadUI(String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/" + ui + ".fxml"));

        



} catch (IOException ex) {
            Logger.getLogger(DashboardController.class

.getName()).log(Level.SEVERE, null, ex);
        }
        contentPane.getChildren().add(root);
    }

}
