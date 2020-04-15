/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scrumifyd.GestionMeetings.models.Meeting;
import scrumifyd.GestionMeetings.services.InterfaceMeeting;
import scrumifyd.GestionMeetings.services.MeetingService;


/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class MeetingsController implements Initializable {

    @FXML
    private StackPane contentPane;
    
    @FXML
    private FontAwesomeIconView AddMeeting;
    @FXML
    private VBox pnl_scroll;
    @FXML
    private Label Errors;
    int user_id;

    /**
     * Initializes the controller class.
     */

    List<Meeting> ListM = new ArrayList();
    InterfaceMeeting Meetings = new MeetingService();
    
    Meeting mm;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

        refreshNodes();
    }    
 public void setUserId(int user_id){
      this.user_id=user_id;
  }
     public void supprimerMeeting(MouseEvent e, Meeting m) throws SQLException {
         FXMLLoader loader = new FXMLLoader(MeetingsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/MeetingBox.fxml"));

         MeetingBoxController meeting = loader.getController();
         if (e.getSource() == meeting.SupprimerButton) {
          InterfaceMeeting MS = new MeetingService();
                           
          MS.supprimerMeeting(m.getId());
          refreshNodes();
                                    
         
         }

     }
   
      public void refreshNodes() {

        try {

            pnl_scroll.getChildren().clear();

            ListM = Meetings.getAllMeetings();

            if (!ListM.isEmpty()) {

                Node nodes[] = new Node[ListM.size() + 1];
                ListM.forEach((Meeting meeting) -> {
                    try {
                        int i = ListM.indexOf(meeting);
                        i++;
                        if (true) {
                            FXMLLoader loader = new FXMLLoader(MeetingsController.this.getClass().getResource("/scrumifyd/GestionMeetings/views/MeetingBox.fxml"));
                            //separate date into separate day month year for both dates
                            int dayy = meeting.getMeetingDate().getDayOfMonth();
                            Month monthh = meeting.getMeetingDate().getMonth();
                            int yearr = meeting.getMeetingDate().getYear();
                            nodes[i] = loader.load();
                            MeetingBoxController box = loader.getController();
                            box.setId(meeting.getId());
                            box.setName(meeting.getName());
                            box.setType(meeting.getType());
                            box.setPlace(meeting.getPlace());
                            box.setDeadline_day(dayy);
                            box.setDeadline_month(monthh);
                            box.setDeadline_year(yearr);
                            pnl_scroll.getChildren().addAll(nodes[i]);
                               EventHandler<MouseEvent> supprimer = (MouseEvent e) -> {
                               
                                    if (e.getSource() == box.SupprimerButton) {
                                        try {
                                            InterfaceMeeting MS = new MeetingService();
                                            
                                            MS.supprimerMeeting(meeting.getId());
                                            refreshNodes();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(MeetingsController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                };
                               };
                               
                              EventHandler<MouseEvent> editHandler = new EventHandler<MouseEvent>() {
                                                             
                                                                @Override
                                                                public void handle(MouseEvent e) {
                             if (e.getSource() == box.EditButton) {
                                    try {
                                        System.out.print("here");
                                          FXMLLoader loader2 = new FXMLLoader(MeetingsController.this.getClass().getResource("/scrumifyd/GestionMeetings/views/EditM.fxml"));

                                        contentPane.getChildren().clear();
                                        Parent root = (Parent) loader2.load();
                                        EditMeetingController mc = loader2.getController();
                                        mc.setProjectId(meeting.getId());
                                        mc.setName(meeting.getName());
                                        mc.setPlace(meeting.getPlace());
                                        mc.setMeetingDate(meeting.getMeetingDate());
                                        contentPane.getChildren().add(root);
                                    } catch (IOException ex) {
                                        Logger.getLogger(MeetingsController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                        };};
                            box.SupprimerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, supprimer);
                            box.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);

                            };
                        
                    }catch (IOException ex) {
                        Logger.getLogger(MeetingsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }

        } catch (SQLException ex) {

            Logger.getLogger(MeetingsController.class.getName()).log(Level.SEVERE, "probleeem", ex);
        }

    }

    public Meeting getMm() {
        return mm;
    }

    public void setMm(Meeting mm) {
        this.mm = mm;
    }

       @FXML
    public void AddMeeting(MouseEvent event) {
        try {
            contentPane.getChildren().clear();
            
            FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionMeetings/views/AddMeeting.fxml"));
            Parent root = (Parent) loader.load();
            AddMeetingController sp= loader.getController();
            sp.setUserId(user_id);
            System.out.println(user_id);
            
            
            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(MeetingsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


   
    
}
