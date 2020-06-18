/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.controllers;

import com.jfoenix.controls.JFXButton;
import java.awt.AWTException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionProjets.controllers.ProjectsAdminController;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionUsers.models.User;
import scrumifyd.GestionUsers.services.UserCrud;
import scrumifyd.util.ScrumifyUtil;

/**
 * FXML Controller class
 *
 * @author AmiraDoghri
 */
public class UsersAdminController implements Initializable {

    @FXML
    private StackPane contentPane;
    @FXML
    private TableView<User> ProjectTable;
    @FXML
    private TableColumn<User, ?> clm_id;
    @FXML
    private TableColumn<User, ?> clm_name;
    @FXML
    private TableColumn<User, ?> clm_lastname;
    @FXML
    private TableColumn<User, ?> clm_username;
    @FXML
    private TableColumn<User, ?> clm_email;
    @FXML
    private TableColumn<User, Image> clm_avatar;
    @FXML
    private TableColumn<User, ?> clm_status;
    @FXML
    private TableColumn<User, ?> clm_lastlogin;
    @FXML
    private TableColumn<User, JFXButton> clm_action;

    private ObservableList<User> usersList;

    List<User> uList;
    UserCrud ur = new UserCrud();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshtTable();

    }

    public void refreshtTable() {
        //uList = new ArrayList<>();
        uList = ur.getAllUser();
        usersList = FXCollections.observableArrayList(uList);
        clm_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        clm_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        clm_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        clm_email.setCellValueFactory(new PropertyValueFactory<>("email"));
//        clm_avatar.setCellFactory((param) -> {
//            final TableCell<User, Image> cell = new TableCell<User, Image>() {
//                @Override
//                protected void updateItem(Image item, boolean empty) {
//                    super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
//                    if (empty) {
//                        setGraphic(null);
//                        setText(null);
//                    } else {
//                        //User ur1 = (User) getTableRow().getItem();
//                        //setGraphic(new ImageView(new Image("/scrumifyd/uploads/images/" + ur1.getImage())));
//                    }
//                }
//
//            };
//
//            return cell;
//
//        }
//        );
        clm_avatar.setCellValueFactory(new PropertyValueFactory<>("image"));
        clm_lastlogin.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
        Callback<TableColumn<User, JFXButton>, TableCell<User, JFXButton>> cellFactory;

        clm_action.setCellFactory((param) -> {

            final TableCell<User, JFXButton> cell = new TableCell<User, JFXButton>() {
                final JFXButton btn1 = new JFXButton("Disable");
                final JFXButton btn2 = new JFXButton("Enable");

                @Override
                public void updateItem(JFXButton item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        User ur1 = (User) getTableRow().getItem();

                        if (ur1.getEnabled()) {
                            btn1.setTextFill(Paint.valueOf("#c91616"));
                            setGraphic(btn1);
                            setText(null);
                        } else {
                            btn2.setTextFill(Paint.valueOf("#16cabd"));
                            setGraphic(btn2);
                            setText(null);
                        }

                    }

                    btn1.setOnAction(event -> {
                        User user = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Disable account", ButtonType.YES, ButtonType.CANCEL);
                        alert.setTitle("Disable account");
                        alert.setHeaderText("Are you sure to disable this account ?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.YES) {

                            if (ur.SupprimerUser(user.getId())) {
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
                        User user = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Enable account", ButtonType.YES, ButtonType.CANCEL);
                        alert.setTitle("Enable account");
                        alert.setHeaderText("Are you sure to enable this account ?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.YES) {

                            if (ur.enableUser(user.getId())) {
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

        });

        ProjectTable.getItems().setAll((Collection<? extends User>) (Collection<?>) usersList);

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

    @FXML
    private void pdfExport(MouseEvent event) throws AWTException, MalformedURLException {
//        List<List> printData = new ArrayList<>();
//        String[] headers = {"ID", "   Name    ", "Description", "Etat", "    Created   ", "     Due date   "};
//        printData.add(Arrays.asList(headers));
//        for (Project pr : projectsList) {
//            List<String> row = new ArrayList<>();
//            String etat = ("" + pr.getEtat()).replaceAll("\t", " ");
//            String id = ("" + pr.getId()).replaceAll("\t", " ");
//            String created = ("" + pr.getCreated()).replaceAll("\t", " ");
//            String duedate = ("" + pr.getDuedate()).replaceAll("\t", " ");
//
//            row.add(id);
//            row.add(pr.getName().replaceAll("\t", " "));
//            row.add(pr.getDescription().replaceAll("\t", " "));
//            row.add(etat);
//
//            row.add(created);
//            row.add(duedate);
//
//            printData.add(row);
//        }
//        ScrumifyUtil.initPDFExprot(contentPane, contentPane, getStage(), printData);
    }

    private Stage getStage() {
        return (Stage) ProjectTable.getScene().getWindow();
    }
}
