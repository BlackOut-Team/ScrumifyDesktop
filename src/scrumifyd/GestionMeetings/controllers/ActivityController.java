/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.controllers;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.Parent;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scrumifyd.GestionMeetings.models.Activity;
import scrumifyd.GestionMeetings.services.ActivityService;
import scrumifyd.GestionMeetings.services.InterfaceActivity;
import scrumifyd.GestionProjets.controllers.ProjectsController;
import scrumifyd.GestionProjets.controllers.emptyController;
import scrumifyd.GestionProjets.models.Project;

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
    //private Label Errors;
    int user_id;

    Activity aa;

    List<Activity> ListA = new ArrayList();
    InterfaceActivity Activity = new ActivityService();
    @FXML
    private JFXTextField searchBar;

    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        refreshNodes();
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
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

            if (!ListA.isEmpty()) {

                Node nodes[] = new Node[ListA.size() + 1];
                ListA.forEach((Activity activity) -> {

                    try {

                        int i = ListA.indexOf(activity);
                        i++;
                        FXMLLoader ActivityLoader = new FXMLLoader(ActivityController.this.getClass().getResource("/scrumifyd/GestionMeetings/views/ActivityBox.fxml"));
                        // ActivityBoxController box2 = ActivityLoader.getController();

                        nodes[i] = ActivityLoader.load();

                        if (activity.getViewed() == 0) {
                            ActivityBoxController box = ActivityLoader.getController();

                            box.setColor();

                            box.setAction(activity.getAction());

                            box.setUsername(Activity.getUserAc(activity.getId()).getUsername());

                            EventHandler<MouseEvent> supprimerHandler = new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent e) {
                                    if (e.getSource() == box.SupprimerButton) {
                                        InterfaceActivity aa = new ActivityService();
                                        //Project pp;
                                        //String idd = id.getText();
                                        try {
                                            aa.supprimerActivity(activity.getId());
                                            refreshNodes();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }
                                }

                            };

                            EventHandler<MouseEvent> viewHandler = new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent e) {
                                    if (e.getSource() == box.ViewButton) {
                                        box.setColor2();
                                        refreshNodes();

                                    }
                                }

                            };
                            box.SupprimerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, supprimerHandler);
                            box.ViewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, viewHandler);

                        } else {
                            ActivityBoxController box = ActivityLoader.getController();

                            //separate date into separate day month year for both dates
                            box.setColor2();
                            box.hideButton();

                            box.setAction(activity.getAction());
                            box.setUsername(Activity.getUserAc(activity.getId()).getUsername());

                            EventHandler<MouseEvent> supprimerHandler = new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent e) {
                                    if (e.getSource() == box.SupprimerButton) {
                                        InterfaceActivity aa = new ActivityService();
                                        //Project pp;
                                        //String idd = id.getText();
                                        try {
                                            aa.supprimerActivity(activity.getId());
                                            refreshNodes();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }
                                }

                            };
                            box.SupprimerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, supprimerHandler);

                        }

                        pnl_scroll.getChildren().addAll(nodes[i]);

                    } catch (IOException ex) {
                        Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
             contentPane.getChildren().clear();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/empty.fxml"));
                Parent root = null;
                try {
                    root = (Parent) loader.load();
                    emptyController sp = loader.getController();
                    sp.setUserId(user_id);
                    sp.setLabelColor(sp.lbl_allProjects);
                    sp.setLabelColor1(sp.lbl_completed);
                    sp.setLabelColor1(sp.lbl_currentprojects);

                } catch (IOException ex) {
                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                }

                contentPane.getChildren().add(root);
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

    public void refreshSearch(String key) throws InterruptedException {
        try {
            pnl_scroll.getChildren().clear();

            List<Activity> ListA = Activity.searchActivities(key);

            if (!ListA.isEmpty()) {

                Node nodes[] = new Node[ListA.size() + 1];
                ListA.forEach((Activity activity) -> {

                    try {

                        int i = ListA.indexOf(activity);
                        i++;
                        FXMLLoader ActivityLoader = new FXMLLoader(ActivityController.this.getClass().getResource("/scrumifyd/GestionMeetings/views/ActivityBox.fxml"));
                        // ActivityBoxController box2 = ActivityLoader.getController();

                        nodes[i] = ActivityLoader.load();

                        if (activity.getViewed() == 0) {
                            ActivityBoxController box = ActivityLoader.getController();

                            box.setColor();

                            box.setAction(activity.getAction());

                            box.setUsername(Activity.getUserAc(activity.getId()).getUsername());

                            EventHandler<MouseEvent> supprimerHandler = new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent e) {
                                    if (e.getSource() == box.SupprimerButton) {
                                        InterfaceActivity aa = new ActivityService();
                                        //Project pp;
                                        //String idd = id.getText();
                                        try {
                                            aa.supprimerActivity(activity.getId());
                                            refreshNodes();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }
                                }

                            };

                            EventHandler<MouseEvent> viewHandler = new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent e) {
                                    if (e.getSource() == box.ViewButton) {
                                        box.setColor2();
                                        refreshNodes();

                                    }
                                }

                            };
                            box.SupprimerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, supprimerHandler);
                            box.ViewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, viewHandler);

                        } else {
                            ActivityBoxController box = ActivityLoader.getController();

                            //separate date into separate day month year for both dates
                            box.setColor2();
                            box.hideButton();

                            box.setAction(activity.getAction());
                            box.setUsername(Activity.getUserAc(activity.getId()).getUsername());

                            EventHandler<MouseEvent> supprimerHandler = new EventHandler<MouseEvent>() {

                                @Override
                                public void handle(MouseEvent e) {
                                    if (e.getSource() == box.SupprimerButton) {
                                        InterfaceActivity aa = new ActivityService();
                                        //Project pp;
                                        //String idd = id.getText();
                                        try {
                                            aa.supprimerActivity(activity.getId());
                                            refreshNodes();
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                    }
                                }

                            };
                            box.SupprimerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, supprimerHandler);

                        }

                        pnl_scroll.getChildren().addAll(nodes[i]);

                    } catch (IOException ex) {
                        Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                ImageView empty = new ImageView("/scrumifyd/GestionProjets/images/empty-state.png");
                pnl_scroll.getChildren().add(empty);
                System.out.println("empty");
           

            }

        } catch (SQLException ex) {
            Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void searchBar(KeyEvent event) {

        searchBar.setOnKeyTyped((event3) -> {
            try {
                refreshSearch(searchBar.getText());
                if (searchBar.getText().isEmpty()) {
                    //refreshSearch(searchBar.getText());
                    refreshNodes();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }
}
