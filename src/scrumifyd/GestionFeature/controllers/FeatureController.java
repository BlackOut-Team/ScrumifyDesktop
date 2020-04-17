/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionFeature.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import scrumifyd.GestionFeature.models.Features;
import scrumifyd.GestionFeature.services.CrudFeatures;
import scrumifyd.GestionUserstory.controllers.UserStoryController;
import scrumifyd.GestionUserstory.services.CrudUserstory;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.AWTException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.util.ScrumifyUtil;

/**
 * FXML Controller class
 *
 * @author youssef
 */
public class FeatureController implements Initializable {

    @FXML
    private TableView<Features> table;
    @FXML
    private TableColumn<Features, String> id;
    @FXML
    private TableColumn<Features, String> name;
    @FXML
    private TableColumn<Features, String> etat;
    @FXML
    private Button delete;
    @FXML
    private Button update;
    @FXML
    private ChoiceBox<String> etat_field;
    @FXML
    private TextField name_field;
    ObservableList<String> list = FXCollections.observableArrayList("1", "2", "3");
    @FXML
    private AnchorPane contentPane;
    @FXML
    private FontAwesomeIconView Back;
    private ObservableList<Features> featureslist;
    @FXML
    private StackPane contentPane1;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        etat_field.setItems(list);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

        CrudFeatures a = new CrudFeatures();
        table.setItems(a.afficherFeature());

    }

    @FXML
    private void delete(ActionEvent event) {
        if (table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("selection");
            alert.setContentText("selectionner une colone du tableau pour supprimer");

            alert.showAndWait();
        } else {
            CrudFeatures a = new CrudFeatures();
            a.supprimerFeature(table.getSelectionModel().getSelectedItem().getId());

            table.setItems(a.afficherFeature());

        }
    }

    @FXML
    private void update(ActionEvent event) {
        if (table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("Feature");
            alert.setContentText("selectioner une case pour modifier");
            alert.showAndWait();
        } else {

            CrudFeatures CF = new CrudFeatures();
            Features F = table.getSelectionModel().getSelectedItem();
            F.setName(name_field.getText());
            F.setEtat(Integer.parseInt(etat_field.getValue()));
            CF.modifierFeature(F);
            table.setItems(CF.afficherFeature());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("done");
            alert.setHeaderText("modification");
            alert.setContentText("modification effectué");

            alert.showAndWait();
        }
    }

    @FXML
    private void fill_fields(MouseEvent event) {
        Features F = table.getSelectionModel().getSelectedItem();
        name_field.setText(F.getName());
        etat_field.setValue(String.valueOf(F.getEtat()));
    }

    @FXML
    private void add(ActionEvent event) {
        Features F = new Features();
        F.setName(name_field.getText());
        F.setEtat(Integer.parseInt(etat_field.getValue()));
        F.setIsDeleted(0);

        CrudFeatures CF = new CrudFeatures();
        CF.ajouterFeature(F);
        table.setItems(CF.afficherFeature());
    }

    @FXML
    private void PDF(ActionEvent event) throws AWTException, MalformedURLException {

        CrudFeatures a = new CrudFeatures();
        
        List<List> printData = new ArrayList<>();
            String[] headers = {"ID", "   Name    ", "Sprint", "Etat", "    Is deleted   ", "     Due date   "};
        printData.add(Arrays.asList(headers));
        for (Features fr : featureslist) {
            List<String> row = new ArrayList<>();
            String etat = ("" + fr.getEtat()).replaceAll("\t", " ");
            String id = ("" + fr.getId()).replaceAll("\t", " ");
            String created = ("" + fr.getIsDeleted()).replaceAll("\t", " ");
            String duedate = ("" + fr.getSprint_id()).replaceAll("\t", " ");

            row.add(id);
            row.add(fr.getName().replaceAll("\t", " "));
            row.add(duedate);
            row.add(etat);

            row.add(created);

            printData.add(row);
        }
        ScrumifyUtil.initPDFExprot(contentPane1, contentPane, getStage(), printData);

    }
 private Stage getStage() {
        return (Stage) table.getScene().getWindow();
    }

    @FXML
    private void UserStory(ActionEvent event) {
        if (table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("Userstory");
            alert.setContentText("selectioner une case pour avancer");
            alert.showAndWait();
        } else {
            try {
                FXMLLoader loader2 = new FXMLLoader(FeatureController.this.getClass().getResource("/scrumifyd/GestionUserstory/views/UserStory.fxml"));

                contentPane.getChildren().clear();
                Parent root = (Parent) loader2.load();

                UserStoryController a = loader2.getController();
                a.setId_feature(table.getSelectionModel().getSelectedItem().getId());

                CrudUserstory CU = new CrudUserstory();
                a.setTable(CU.afficherUserStory(table.getSelectionModel().getSelectedItem().getId()));

                contentPane.getChildren().add(root);

            } catch (IOException ex) {
                Logger.getLogger(FeatureController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadUI(String module, String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/" + module + "/views/" + ui + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(FeatureController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contentPane.getChildren().add(root);
    }

    @FXML
    private void Back(MouseEvent event) {
        loadUI("GestionUserstory", "UserStory");

    }

}
