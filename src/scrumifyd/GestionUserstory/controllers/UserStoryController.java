/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUserstory.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import scrumifyd.GestionUserstory.models.UserStory;
import scrumifyd.GestionUserstory.services.CrudUserstory;

/**
 * FXML Controller class
 *
 * @author youssef
 */
public class UserStoryController implements Initializable {

    private int id_feature;
    @FXML
    private Label slider_texr;

    @FXML
    private TextArea description_value;
    @FXML
    private Slider slider_value;
    @FXML
    private ComboBox<String> etat_value;
    @FXML
    private ComboBox<String> story_point_value;

    ObservableList<String> list = FXCollections.observableArrayList("1", "2", "3");
    ObservableList<String> list1 = FXCollections.observableArrayList("1", "2", "3", "4", "5", "6", "7");
    @FXML
    private AnchorPane contentPane;
    @FXML
    private FontAwesomeIconView Back;

    public void setId_feature(int id_feature) {
        this.id_feature = id_feature;
    }
    @FXML
    private TableColumn<UserStory, String> id;
    @FXML
    private TableColumn<UserStory, String> description;
    @FXML
    private TableColumn<UserStory, String> priority;
    @FXML
    private TableColumn<UserStory, String> story_point;
    @FXML
    private TableColumn<UserStory, String> etat;
    @FXML
    private TableView<UserStory> table;

    public void setTable(ObservableList<UserStory> tab) {
        table.setItems(tab);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        etat_value.setItems(list);
        story_point_value.setItems(list1);
        slider_value.valueProperty().addListener((obs, oldval, newVal)
                -> slider_value.setValue(newVal.intValue()));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        story_point.setCellValueFactory(new PropertyValueFactory<>("story_point"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));

    }

    @FXML
    private void moved(MouseEvent event) {
        slider_texr.setText(Double.toString(slider_value.getValue()));
    }

    @FXML
    private void movedkey(KeyEvent event) {
        slider_texr.setText(Double.toString(slider_value.getValue()));
    }

    @FXML
    private void add(ActionEvent event) {
        UserStory U = new UserStory();
        U.setDescription(description_value.getText());
        U.setEtat(Integer.parseInt(etat_value.getValue()));
        U.setIsDeleted(0);
        U.setPriority((int) slider_value.getValue());
        U.setFeature_id(id_feature);
        U.setStory_point(Integer.parseInt(story_point_value.getValue()));
        System.out.println(U.toString());
        CrudUserstory CU = new CrudUserstory();
        CU.ajouterUserStory(U);
      
        table.setItems(CU.afficherUserStory(id_feature));
    }

    @FXML
    private void update(ActionEvent event) {
        if (table.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
            alert.setHeaderText("UserStory");
            alert.setContentText("selectioner une case pour modifier");
            alert.showAndWait();
        } else {

            CrudUserstory CU = new CrudUserstory();
            UserStory U = table.getSelectionModel().getSelectedItem();
            U.setDescription(description_value.getText());
            U.setEtat(Integer.parseInt(etat_value.getValue()));
            U.setIsDeleted(0);
            U.setPriority((int) slider_value.getValue());
            U.setFeature_id(id_feature);
            U.setStory_point(Integer.parseInt(story_point_value.getValue()));
            CU.modifierUserStory(U.getId(), U);
            table.setItems(CU.afficherUserStory(id_feature));

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("done");
            alert.setHeaderText("modification");
            alert.setContentText("modification effectué");

            alert.showAndWait();
        }
    }

    @FXML
    private void pdf(ActionEvent event) {
    }

    @FXML
    private void fill_fields(MouseEvent event) {
        UserStory U = table.getSelectionModel().getSelectedItem();
        description_value.setText(U.getDescription());
        etat_value.setValue(String.valueOf(U.getEtat()));
        slider_value.setValue(Double.valueOf(U.getPriority()));
        slider_texr.setText(Double.toString(slider_value.getValue()));
        story_point_value.setValue(String.valueOf(U.getStory_point()));
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
            CrudUserstory a = new CrudUserstory();
            a.supprimerUserStory(table.getSelectionModel().getSelectedItem().getId());

            table.setItems(a.afficherUserStory(id_feature));

        }
    }
    public void loadUI(String module , String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/"+module+"/views/" + ui + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(UserStoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contentPane.getChildren().add(root);
    }


    @FXML
    private void Back(MouseEvent event) {
        loadUI("GestionFeature", "Feature");
        
    }
}
