/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.controllers;

import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;

import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scrumifyd.GestionMeetings.models.Activity;
import scrumifyd.GestionMeetings.services.ActivityService;
import scrumifyd.GestionMeetings.services.InterfaceActivity;


/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */


public class ActivityController implements Initializable {

    @FXML
    private StackPane contentPane;
    
    @FXML
    private VBox pnl_scroll;
    @FXML
    private Label Errors;
    int user_id;

    Activity aa;
    /**
     * Initializes the controller class.
     */

    List<Activity> ListA = new ArrayList();
    InterfaceActivity Activity = new ActivityService();
    
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            refreshNodes();
    }    
 public void setUserId(int user_id){
      this.user_id=user_id;
  }
     public void supprimerActivity(MouseEvent e, Activity a) throws SQLException {
         FXMLLoader loader = new FXMLLoader(ActivityController.this.getClass().getResource("/scrumifyd/GestionMeetings/views/ActivityBox.fxml"));

         ActivityBoxController activity = loader.getController();
         if (e.getSource() == activity.SupprimerButton) {
          InterfaceActivity MS = new ActivityService();
                           
          MS.supprimerActivity(a.getId());
          refreshNodes();
                                    
         
         }

     }
   
      public void refreshNodes() {

        try {
            pnl_scroll.getChildren().clear();
            
            ListA = Activity.getAllActivity();
                                System.out.println(ListA);

            if (!ListA.isEmpty()) {
                
                Node nodes[] = new Node[ListA.size() + 1];
                ListA.forEach((Activity activity) -> {
                    System.out.println(activity);
                    
                    try {
                        
                        int i = ListA.indexOf(activity);
                        i++;
                        FXMLLoader ActivityLoader = new FXMLLoader(ActivityController.this.getClass().getResource("/scrumifyd/GestionMeetings/views/ActivityBox.fxml"));
                                                    ActivityBoxController box2 = ActivityLoader.getController();

                            nodes[i] = ActivityLoader.load();

                        if (activity.getViewed() == 0) {
                                                        ActivityBoxController box = ActivityLoader.getController();

                            //separate date into separate day month year for both dates
                            box.setColor();
                        
                            box.setAction(activity.getAction());
                            box.setViewed(activity.getViewed());
                            box.setUsername(activity.getUsername());
                            EventHandler<MouseEvent> supprimerHandler = new EventHandler<MouseEvent>() {

                                                            @Override
                                                            public void handle(MouseEvent e) {
                              if (e.getSource() == box2.SupprimerButton) {
                                    InterfaceActivity aa = new ActivityService();
                                    //Project pp;
                                    //String idd = id.getText();
                                try {
                                    aa.supprimerActivity(activity.getId());
            refreshNodes();
                                } catch (SQLException ex) {
                                    Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            
                        }}
                            
                            };
                                                        box2.SupprimerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, supprimerHandler);
                        }else{
                            ActivityBoxController box = ActivityLoader.getController();


                            //separate date into separate day month year for both dates
                            box.setColor2();
                            box.hideButton();
                        
                            box.setAction(activity.getAction());
                            box.setViewed(activity.getViewed());
                            box.setUser(activity.getUser_id());
                            
                        }
                                                    
                                                    
                          
                                                    pnl_scroll.getChildren().addAll(nodes[i]);

                    }catch (IOException ex) {
                        Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
            
            
             
                            
        } catch (SQLException ex) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

  
  public Activity getActivity() {
        return aa;
    }

    public void setActivity(Activity aa) {
        this.aa = aa;
    }


   
    
}
