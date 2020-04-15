package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.models.Sprint;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.services.SprintInterface;
import scrumifyd.GestionProjets.services.SprintService;
import scrumifyd.GestionUsers.services.SigninController;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ProjectsController implements Initializable {

    @FXML
    private VBox pnl_scroll;
    @FXML
    private Label lbl_currentprojects;
    @FXML
    private Label lbl_completed;
    @FXML
    private Pane AddProject;
    @FXML
    private StackPane contentPane;

    List<Project> ListP = new ArrayList();
    InterfaceProjet Projects = new ProjectService();
    int pid;
    Project p;
    int user_id;
    @FXML
    private FontAwesomeIconView AddProject1;
    @FXML
    private Label lbl_allProjects;
    @FXML
    private JFXTextField searchBar;
    List<Project> ListPa;

    /**
     * Initializes the controller class.
     *
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

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void refreshNodes() {

        try {

            pnl_scroll.getChildren().clear();
            SigninController s = new SigninController();

            user_id = s.user.getUserId();

            ListPa = Projects.getAllActiveProjects(user_id);

            if (!ListPa.isEmpty()) {

                Node nodes[] = new Node[ListPa.size() + 1];

                ListPa.forEach((Project project) -> {
                    try {
                        int i = ListPa.indexOf(project);
                        i++;
//             
                    
                        FXMLLoader loader = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/Item.fxml"));
                        //separate date into separate day month year for both dates
                        int dayy = project.getCreated().getDayOfMonth();
                        Month monthh = project.getCreated().getMonth();
                        int yearr = project.getCreated().getYear();
                        int dayyd = project.getDuedate().getDayOfMonth();
                        Month monthhd = project.getDuedate().getMonth();
                        int yearrd = project.getDuedate().getYear();
                        nodes[i] = loader.load();
                        ItemController item = loader.getController();
                        //item.setId(project.getId());
                        item.setName(project.getName());
                        if (project.getMaster_id() == user_id) {
                            item.setRole("Scrum Master");
                        } else if (project.getOwner_id() == user_id) {
                            item.lbl_role.setStyle("-fx-background-color:green");
                            item.setRole("Project Owner");
                            item.EditButton.setVisible(false);
                            item.ArchiveButton.setVisible(false);

                        }
                        else {
                            item.lbl_role.setStyle("-fx-background-color:#16cabd");
                            item.setRole("Developer");
                       }
                        item.setDescription(project.getDescription());

//               
                        item.setEtat(project.getEtat());
                        item.setCreated_day(dayy);

                        item.setDeadline_month(monthh);
                        item.setCreated_year(yearr);
                        item.setDeadline_day(dayyd);
                        item.setDeadline_month(monthhd);
                        item.setDeadline_year(yearrd);
                        pnl_scroll.getChildren().addAll(nodes[i]);
                        
                        EventHandler<MouseEvent> editHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.EditButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/EditP.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    EditPController sp = loader2.getController();

                                    sp.setProject(project);
                                    System.out.println(project.getId());
                                    sp.setProjectId(project.getId());
                                    sp.setName(project.getName());
                                    sp.setDescription(project.getDescription());
                                    sp.setDeadline(project.getDuedate());
                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                  
                        EventHandler<MouseEvent> archiveHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.ArchiveButton) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive project", ButtonType.YES, ButtonType.CANCEL);
                                alert.setTitle("Archive project");
                                alert.setHeaderText("Are you sure to archive this project ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.YES) {
                                    InterfaceProjet pr = new ProjectService();
                                    //Project pp;
                                    //String idd = id.getText();
                                    Project project1 = new Project(0);

                                    if (pr.archiveProject(project.getId())) {
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
                        };
                              
                        EventHandler<MouseEvent> showSprintHandler;
                        showSprintHandler = new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent e) {
                                FXMLLoader loader1 = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/Sprints.fxml"));
                                if (e.getSource() == item.showSprintsButton) {
                                    try {
                                        contentPane.getChildren().clear();
                                        Parent root = (Parent) loader1.load();
                                        ShowSprintController sp = loader1.getController();
                                        sp.pid.setText("" + project.getId());
                                        //sp.pp = Integer.parseInt(sp.pid.getText());
                                        SprintInterface Sprints = new SprintService();
                                        //System.out.println(sp.pp);
                                        try {
                                            sp.ListS = Sprints.getAllSprints(project.getId());
                                            sp.sprint_scroll.getChildren().clear();
                                            System.out.println(sp.ListS);
                                            Node nodes[] = new Node[sp.ListS.size() + 1];
                                            sp.ListS.forEach((Sprint s) -> {
                                                int i = sp.ListS.indexOf(s);
                                                i++;
                                                if (s.getEtat() == 1) {
                                                    try {
                                                        FXMLLoader loader = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/ItemS.fxml"));
                                                        //separate date into separate day month year for both dates
                                                        int dayy = s.getCreated().getDayOfMonth();
                                                        Month monthh = s.getCreated().getMonth();
                                                        int yearr = s.getCreated().getYear();
                                                        int dayyd = s.getDuedate().getDayOfMonth();
                                                        Month monthhd = s.getDuedate().getMonth();
                                                        int yearrd = s.getDuedate().getYear();
                                                        nodes[i] = loader.load();
                                                        ItemSController item = loader.getController();
                                                        item.setName(s.getName());
                                                        item.setDescription(s.getDescription());
                                                        item.setCreated_day(dayy);
                                                        item.setDeadline_month(monthh);
                                                        item.setCreated_year(yearr);
                                                        item.setDeadline_day(dayyd);
                                                        item.setDeadline_month(monthhd);
                                                        item.setDeadline_year(yearrd);
                                                        sp.sprint_scroll.getChildren().addAll(nodes[i]);
                                                        EventHandler<MouseEvent> editHandler = new EventHandler<MouseEvent>() {

                                                            @Override
                                                            public void handle(MouseEvent e) {
                                                                if (e.getSource() == item.EditButton) {
                                                                    try {
                                                                        FXMLLoader loader2 = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/EditS.fxml"));

                                                                        contentPane.getChildren().clear();
                                                                        Parent root = (Parent) loader2.load();
                                                                        EditSprintController sp = loader2.getController();

                                                                        sp.setProject(project);
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
                                                                        //Project pp;

                                                                        //String idd = id.getText();
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
                                                        
                                                       
                                                        
                                                        EventHandler<MouseEvent> showSprintDetailsHandler = new EventHandler<MouseEvent>() {

                                                            @Override
                                                            public void handle(MouseEvent e) {
                                                                    if (e.getSource() == item.LinkButton) {
                                                                    try {
                                                                        FXMLLoader loader2 = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionFeature/views/Feature.fxml"));

                                                                        contentPane.getChildren().clear();
                                                                        Parent root = (Parent) loader2.load();

                                                                      
                                                                      
                                                                        contentPane.getChildren().add(root);
                                                                    } catch (IOException ex) {
                                                                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                                                    }
                                                                }

                                                            
                                                            
                                                        };
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
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        System.out.println(sp.ListS);
                                        sp.pp += project.getId();
                                        //sp.setProjectId(p.getId());
                                        contentPane.getChildren().add(root);
                                    } catch (IOException ex) {
                                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        };
                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                        item.showSprintsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showSprintHandler);

                    } catch (IOException ex) {
                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
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

            Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, "probleeem", ex);
        }

    }

    @FXML
    public void AddProject(MouseEvent event) {
        try {
            contentPane.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/AddP.fxml"));
            Parent root = (Parent) loader.load();
            AddPController sp = loader.getController();
            sp.setUserId(user_id);
            System.out.println(user_id);

            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    private void currentProjects(MouseEvent event) {
        loadUI("ProjectsCurrent");
    }

    private void pendingProjects(MouseEvent event) {
        loadUI("ProjectsPending");

    }

    @FXML
    private void completedProjects(MouseEvent event) {
        loadUI("ProjectsCompleted");

    }

    @FXML
    private void allProjects(MouseEvent event) {
        refreshNodes();
    }

    public void refreshSearch(String key) throws InterruptedException {
        try {

            pnl_scroll.getChildren().clear();

            List<Project> ListPa = Projects.searchProjects(key,user_id);

            if (!ListPa.isEmpty()) {

                Node nodes[] = new Node[ListPa.size() + 1];

                ListPa.forEach((Project project) -> {
                    try {
                        int i = ListPa.indexOf(project);
                        i++;

                        FXMLLoader loader = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/Item.fxml"));
                        //separate date into separate day month year for both dates
                        int dayy = project.getCreated().getDayOfMonth();
                        Month monthh = project.getCreated().getMonth();
                        int yearr = project.getCreated().getYear();
                        int dayyd = project.getDuedate().getDayOfMonth();
                        Month monthhd = project.getDuedate().getMonth();
                        int yearrd = project.getDuedate().getYear();
                        nodes[i] = loader.load();
                        ItemController item = loader.getController();
                       
                        item.setName(project.getName());
                        if (project.getMaster_id() == user_id) {
                            item.setRole("Scrum Master");
                        } else if (project.getOwner_id() == user_id) {
                            item.lbl_role.setStyle("-fx-background-color:green");
                            item.setRole("Project Owner");
                            item.EditButton.setVisible(false);
                            item.ArchiveButton.setVisible(false);

                        }
                        else {
                            item.lbl_role.setStyle("-fx-background-color:#16cabd");
                            item.setRole("Developer");
                       }  
                        item.setDescription(project.getDescription());
                        item.setEtat(project.getEtat());
                        item.setCreated_day(dayy);
                        item.setDeadline_month(monthh);
                        item.setCreated_year(yearr);
                        item.setDeadline_day(dayyd);
                        item.setDeadline_month(monthhd);
                        item.setDeadline_year(yearrd);
                        pnl_scroll.getChildren().addAll(nodes[i]);
                        EventHandler<MouseEvent> editHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.EditButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/EditP.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    EditPController sp = loader2.getController();

                                    sp.setProject(project);
                                    System.out.println(project.getId());
                                    sp.setProjectId(project.getId());
                                    sp.setName(project.getName());
                                    sp.setDescription(project.getDescription());
                                    sp.setDeadline(project.getDuedate());
                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                        EventHandler<MouseEvent> archiveHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.ArchiveButton) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive project", ButtonType.YES, ButtonType.CANCEL);
                                alert.setTitle("Archive project");
                                alert.setHeaderText("Are you sure to archive this project ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.YES) {
                                    InterfaceProjet pr = new ProjectService();
                                    //Project pp;
                                    //String idd = id.getText();
                                    Project project1 = new Project(0);

                                    if (pr.archiveProject(project.getId())) {
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
                        };
                         EventHandler<MouseEvent>  meetingHandler = new EventHandler<MouseEvent>() {
                                                             
                                                            @Override
                                                            public void handle(MouseEvent e) {
                                                                        if (e.getSource() == item.MeetingButton) {
                                                                            try {
                                                                                contentPane.getChildren().clear();
                                                                                
                                                                                Parent root=null;
                                                                                
                                                                                root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionMeetings/views/meetings.fxml"));
                                                                                
                                                                                
                                                                                contentPane.getChildren().add(root);
                                                                            } catch (IOException ex) {
                                                                                Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                                                            }
                                                                        }
                                                            }  };
                        EventHandler<MouseEvent> showSprintHandler;
                        showSprintHandler = new EventHandler<MouseEvent>() {

                            @Override
                            public void handle(MouseEvent e) {
                                FXMLLoader loader1 = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/Sprints.fxml"));
                                if (e.getSource() == item.showSprintsButton) {
                                    try {
                                        contentPane.getChildren().clear();
                                        Parent root = (Parent) loader1.load();
                                        ShowSprintController sp = loader1.getController();
                                        sp.pid.setText("" + project.getId());
                                        sp.pp = Integer.parseInt(sp.pid.getText());
                                        SprintInterface Sprints = new SprintService();
                                        System.out.println(sp.pp);
                                        try {
                                            sp.ListS = Sprints.getAllSprints(project.getId());
                                            sp.sprint_scroll.getChildren().clear();
                                            System.out.println(sp.ListS);
                                            Node nodes[] = new Node[sp.ListS.size() + 1];
                                            sp.ListS.forEach((Sprint s) -> {
                                                int i = sp.ListS.indexOf(s);
                                                i++;
                                                if (s.getEtat() == 1) {
                                                    try {
                                                        FXMLLoader loader = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/ItemS.fxml"));
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
                                                        EventHandler<MouseEvent> editHandler = new EventHandler<MouseEvent>() {

                                                            @Override
                                                            public void handle(MouseEvent e) {
                                                                if (e.getSource() == item.EditButton) {
                                                                    try {
                                                                        FXMLLoader loader2 = new FXMLLoader(ProjectsController.this.getClass().getResource("/scrumifyd/GestionProjets/views/EditS.fxml"));

                                                                        contentPane.getChildren().clear();
                                                                        Parent root = (Parent) loader2.load();
                                                                        EditSprintController sp = loader2.getController();

                                                                        sp.setProject(project);
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
                                                                        //Project pp;

                                                                        //String idd = id.getText();
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
                                                        EventHandler<MouseEvent> showSprintDetailsHandler = new EventHandler<MouseEvent>() {

                                                            @Override
                                                            public void handle(MouseEvent e) {

                                                            }
                                                        };
                                                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                                                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                                                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showSprintDetailsHandler);
                                                    } catch (IOException ex) {
                                                        Logger.getLogger(ShowSprintController.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                } else {
                                                    i++;
                                                }
                                            });
                                        } catch (SQLException ex) {
                                            Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        System.out.println(sp.ListS);
                                        sp.pp += project.getId();
                                        //sp.setProjectId(p.getId());
                                        contentPane.getChildren().add(root);
                                    } catch (IOException ex) {
                                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }
                        };
                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                        item.MeetingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, meetingHandler);
                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                        item.showSprintsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showSprintHandler);

                    } catch (IOException ex) {
                        Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            } else {
                ImageView empty = new ImageView("/scrumifyd/GestionProjets/images/empty-state.png");
                pnl_scroll.getChildren().add(empty);
                System.out.println("empty");
           

            }

        } catch (SQLException ex) {

            Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, "probleeem", ex);
        }
    }

    @FXML
    private void searchBar(KeyEvent event) {
      
         searchBar.setOnKeyReleased((event3) -> {
             try {
                 refreshSearch(searchBar.getText());
                if (searchBar.getText()=="" ){
                    refreshSearch(searchBar.getText());
                    refreshNodes();
                }
                 
             } catch (InterruptedException ex) {
                 Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
             }
             

        });
         
         

    
            
        

    }

}
