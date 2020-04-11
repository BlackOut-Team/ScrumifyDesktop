package scrumifyd.GestionProjets.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;

import java.sql.SQLException;
import java.time.Month;

import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
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

    List<Project> ListP = new ArrayList();
    InterfaceProjet Projects = new ProjectService();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      

      //  refreshNodes();

    }

    public ProjectsController() {

    }

    @FXML

    private void handleButtonAction(MouseEvent event) {
        refreshNodes();
    }

    private void refreshNodes() {


     
        pnl_scroll.getChildren().clear();
        try {
            ListP = Projects.getAllProjects();

        } catch (SQLException ex) {

            Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, "probleeem", ex);
        }

        Node nodes[] = new Node[ListP.size()];

        ListP.forEach((Project p) -> {
            int i = ListP.indexOf(p);
            i++;
            FXMLLoader fXMLLoader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/Item.fxml"));
            //separate date into separate day month year for both dates
            int dayy = p.getCreated().getDayOfMonth();
            Month monthh = p.getCreated().getMonth();
            int yearr = p.getCreated().getYear();
            int dayyd = p.getDuedate().getDayOfMonth();
            Month monthhd = p.getDuedate().getMonth();
            int yearrd = p.getDuedate().getYear();
            
            ItemController itemController = new ItemController(p.getName(), p.getEtat(), p.getDescription(), dayy, monthh, yearr, dayyd, monthhd, yearrd);
            fXMLLoader.setController(itemController);
            try {
                nodes[i] = fXMLLoader.load();
            } catch (IOException ex) {
                Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, "heere", ex);
            }  
            pnl_scroll.getChildren().addAll(nodes[i]);
            
            //pnl_scroll.getChildren().setAll((Node) fXMLLoader.load());
        });

    }

    @FXML
    public void AddProject(MouseEvent event) {

        loadUI("AddP");
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

    @FXML
    private void pendingProjects(MouseEvent event) {
        loadUI("ProjectsPending");

    }

    @FXML
    private void completedProjects(MouseEvent event) {
        loadUI("ProjectsCompleted");

    }

    
}
