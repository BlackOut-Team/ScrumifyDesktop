/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.util.ScrumifyUtil;

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
    private TableColumn<Project, JFXButton> clm_action;

    private ObservableList<Project> projectsList;

    List<Project> prList;
    @FXML
    private TableColumn<?, ?> clm_status1;
    @FXML
    private TableColumn<?, ?> clm_status11;
    @FXML
    private TableColumn<?, ?> clm_status12;
    @FXML
    private JFXButton pdfExport;

    /**
     * Initializes the controller class.
     *
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

            clm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            clm_description.setCellValueFactory(new PropertyValueFactory<>("description"));
            clm_created.setCellValueFactory(new PropertyValueFactory<>("created"));
            clm_deadline.setCellValueFactory(new PropertyValueFactory<>("duedate"));
            clm_status.setCellValueFactory(new PropertyValueFactory<>("etat"));

            //clm_action.setSortable(false);
            Callback<TableColumn<Project, JFXButton>, TableCell<Project, JFXButton>> cellFactory;

            cellFactory = (TableColumn<Project, JFXButton> param) -> {

                final TableCell<Project, JFXButton> cell = new TableCell<Project, JFXButton>() {

                    final JFXButton btn1 = new JFXButton("Archiver");
                    final JFXButton btn2 = new JFXButton("Restaurer");

                    @Override
                    public void updateItem(JFXButton item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            Project pr1 = (Project) getTableRow().getItem();

                            if (pr1.getEtat() == 1) {
                                btn1.setTextFill(Paint.valueOf("#c91616"));

                                setGraphic(btn1);
                                setText(null);
                            } else if (pr1.getEtat() == 0) {
                                btn2.setTextFill(Paint.valueOf("#16cabd"));

                                setGraphic(btn2);
                                setText(null);
                            }

                        }

                        btn1.setOnAction(event -> {
                            Project proj = getTableView().getItems().get(getIndex());
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive project", ButtonType.YES, ButtonType.CANCEL);
                            alert.setTitle("Archive project");
                            alert.setHeaderText("Are you sure to archive this project ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.YES) {
                                InterfaceProjet pr = new ProjectService();
                                //Project pp;
                                //String idd = id.getText();
                                Project project1 = new Project(0);

                                if (pr.archiveProject(proj.getId())) {
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
                        btn2.setOnAction(event -> {
                            Project project = getTableView().getItems().get(getIndex());

                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Restore project", ButtonType.YES, ButtonType.CANCEL);
                            alert.setTitle("Restore project");
                            alert.setHeaderText("Are you sure to restore this project ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.YES) {
                                InterfaceProjet pr = new ProjectService();
                                //Project pp;
                                //String idd = id.getText();
                                Project project1 = new Project(0);

                                if (pr.unarchiveProject(project.getId())) {
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
                };
                return cell;
            };

            clm_action.setCellFactory(cellFactory);

            ProjectTable.getItems().setAll(projectsList);

        } catch (SQLException ex) {
            Logger.getLogger(ProjectsAdminController.class.getName()).log(Level.SEVERE, null, ex);
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
    private void pdfExport(MouseEvent event) throws AWTException, MalformedURLException {
        List<List> printData = new ArrayList<>();
        String[] headers = {"ID", "   Name    ", "Description", "Etat", "    Created   ", "     Due date   "};
        printData.add(Arrays.asList(headers));
        for (Project pr : projectsList) {
            List<String> row = new ArrayList<>();
            String etat = ("" + pr.getEtat()).replaceAll("\t", " ");
            String id = ("" + pr.getId()).replaceAll("\t", " ");
            String created = ("" + pr.getCreated()).replaceAll("\t", " ");
            String duedate = ("" + pr.getDuedate()).replaceAll("\t", " ");

            row.add(id);
            row.add(pr.getName().replaceAll("\t", " "));
            row.add(pr.getDescription().replaceAll("\t", " "));
            row.add(etat);

            row.add(created);
            row.add(duedate);

            printData.add(row);
        }
        ScrumifyUtil.initPDFExprot(contentPane, contentPane, getStage(), printData);
    }

    private Stage getStage() {
        return (Stage) ProjectTable.getScene().getWindow();
    }

}
