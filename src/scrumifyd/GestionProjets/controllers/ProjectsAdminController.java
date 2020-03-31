/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class ProjectsAdminController implements Initializable {

    @FXML
    private StackPane contentPane;
    @FXML
    private TableView<Project> ProjectTable;
    @FXML
    private TableColumn<Project, Integer> clm_id;
    @FXML
    private TableColumn<Project, String> clm_name;
    @FXML
    private TableColumn<Project, String> clm_description;
    @FXML
    private TableColumn<Project, LocalDate> clm_created;
    @FXML
    private TableColumn<Project, LocalDate> clm_deadline;
    @FXML
    private TableColumn<Project, String> clm_status;
    @FXML
    private TableColumn<Project, Boolean> clm_action;

    private ObservableList projectsList;

    List<Project> prList;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshtTable();
    }

    public ProjectsAdminController() {

    }

    public void refreshtTable() {
        try {
            prList = new ArrayList<>();
            InterfaceProjet pr = new ProjectService();

            prList = pr.getAllProjects();

            projectsList = FXCollections.observableArrayList(prList);

            //  prList.forEach((Project p) -> {
            clm_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            clm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            clm_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            clm_created.setCellValueFactory(new PropertyValueFactory<>("created"));
            clm_deadline.setCellValueFactory(new PropertyValueFactory<>("duedate"));
            clm_status.setCellValueFactory(new PropertyValueFactory<>("etat"));

            clm_action.setSortable(false);
            clm_action.setCellValueFactory((TableColumn.CellDataFeatures<Project, Boolean> p) -> new SimpleBooleanProperty(p.getValue() != null));

            clm_action.setCellFactory((TableColumn<Project, Boolean> param) -> {
                if (prList.get(0).getEtat()==1) {
                    return new ButtonCell();
//
                } else {
                    return new ButtonCellN();
                }
            });


            ProjectTable.getItems().setAll(projectsList);

            //    });
        } catch (SQLException ex) {
            Logger.getLogger(ProjectsAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private class ButtonCell extends TableCell<Project, Boolean> {

        final JFXButton cellButton = new JFXButton("Archiver");

        ButtonCell() {
            cellButton.setTextFill(Paint.valueOf("#16cabd"));
            cellButton.setOnAction((ActionEvent t) -> {
                
                
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive project", ButtonType.YES, ButtonType.CANCEL);
                alert.setTitle("Archive project");
                alert.setHeaderText("Are you sure to archive this project ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    InterfaceProjet pr = new ProjectService();
                    //Project pp;
                    //String idd = id.getText();
                    Project project1 = new Project(0);
                    
                    if (pr.archiveProject(prList.get(0).getId())) {
                        System.out.println("done");
                        alert.hide();
                        refreshtTable();
                    } else {
                        System.out.println("error");
                        alert.hide();
                    }
                    System.out.println("Ok pressed");
                } else {
                    System.out.println("canceled");
                    refreshtTable();
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

    private class ButtonCellN extends TableCell<Project, Boolean> {

        final JFXButton cellButton = new JFXButton("Restaurer");

        ButtonCellN() {
            cellButton.setTextFill(Paint.valueOf("#c91616"));
            cellButton.setOnAction((ActionEvent t) -> {
                  
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Restore project", ButtonType.YES, ButtonType.CANCEL);
                alert.setTitle("Restore project");
                alert.setHeaderText("Are you sure to restore this project ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    InterfaceProjet pr = new ProjectService();
                    //Project pp;
                    //String idd = id.getText();
                    Project project1 = new Project(0);
                    
                    if (pr.unarchiveProject(prList.get(0).getId())) {
                        System.out.println("done");
                        alert.hide();
                        refreshtTable();
                    } else {
                        System.out.println("error");
                        alert.hide();
                    }
                    System.out.println("Ok pressed");
                } else {
                    System.out.println("canceled");
                    refreshtTable();
                }
            });
        }

        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
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

}
