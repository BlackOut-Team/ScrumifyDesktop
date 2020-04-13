/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTeams.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;
import scrumifyd.GestionTeams.models.Affiche;
import scrumifyd.GestionTeams.models.Team;
import scrumifyd.GestionTeams.services.TeamService;

/**
 * FXML Controller class
 *
 * @author Iheb
 */
public class AffUserController implements Initializable {
    @FXML
    private AnchorPane contentPane;
    @FXML
    private JFXTextField Name;
    @FXML
    private JFXButton AffButton;
    @FXML
    private Label Errors;
    @FXML
    private TableColumn<Affiche, String> UserName;
    @FXML
    private TableColumn<Affiche, String> Email;
    @FXML
    private ChoiceBox<String> choicBoxRole;
    
    private int role;
    @FXML
    private TableColumn<Object, Number> Role;
    @FXML
    private TableView<Affiche> Tab;
    
 ObservableList<Affiche> oblist = FXCollections.observableArrayList();
    @FXML
    private JFXButton refresh;
  

    /** 
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        TeamService teamService = TeamService.getInstance();
        teamService.geeamUsers(teamService.getSelectedTeam().getId());
        initData();
        
        List<Affiche> teamList = new ArrayList<>();
        teamList = teamService.geeamUsers(teamService.getSelectedTeam().getId());
         oblist.clear();
        oblist.addAll(teamList);
       
           if (!teamList.isEmpty()) {
           
       
        Email.setCellValueFactory(new PropertyValueFactory<>("email"));
         UserName.setCellValueFactory(new PropertyValueFactory<>("username"));
          Role.setCellValueFactory(new PropertyValueFactory<>("role"));
       
           Tab.setItems(oblist);
           }else
           {System.out.println("scrumifyd.GestionTeams.controllers.teamsController.initialize()");}
    }    

    
    @FXML
    public void refreshTableData() {
         TeamService teamService = TeamService.getInstance();
             List<Affiche> teamList = new ArrayList<>();
             
        teamList = TeamService.geeamUsers(teamService.getSelectedTeam().getId());
         oblist.clear();
        oblist.addAll(teamList);
       Tab.setItems(oblist);
       
      
    }
    
    @FXML
    private void AddButton(MouseEvent event) {
        
        TeamService teamService = TeamService.getInstance();
        if (this.Name.getText().isEmpty() || this.getRole() == -1) {
            JOptionPane.showMessageDialog(
                    null, "Formulaire invalid");
            return;
        }
        if (teamService.isUserExistInTeam(this.Name.getText()) != -1 && teamService.isUserExistInTeam(this.Name.getText()) != 0) {
             JOptionPane.showMessageDialog(
                    null, "Ce member exist déja");
                return;
           
            }
        boolean isProductOwnerExist =  teamService.isProductOwnerExist(teamService.getSelectedTeam().getId());
        System.err.println("isProductOwnerExist" + isProductOwnerExist);
        if (getRole() == 3 && isProductOwnerExist) {
               JOptionPane.showMessageDialog(null, "Ce team à deja un product owner");
          } else {
            try {
           teamService.addMember(this.Name.getText() , getRole());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        }
            
        
        
        Name.clear();
          choicBoxRole.getSelectionModel().clearSelection();
         refreshTableData();
    }
    
    
    
    private void setLblError(Color color, String text) {
        Errors.setTextFill(color);
        Errors.setText(text);
        System.out.println(text);
    }

     /*@FXML
    private void setSelectRole(MouseEvent event) {
        String selectdRole = this.choicBoxRole.getSelectionModel().getSelectedItem();
        System.err.println("selectedrole" + selectdRole);
        if (selectdRole == null) {
            if(selectdRole.equals("Product Owner")) {
                        this.setRole(3);
            }
            if (selectdRole.equals("Developer")) {
                   this.setRole(2);

            }
        }
               
    }*/
      public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
    private void initData() {
         this.choicBoxRole.getItems().add("Product Owner");
        this.choicBoxRole.getItems().add("Developer");
        this.setRole(-1);
        this.choicBoxRole.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue , Number role1 , Number role2){
                System.out.println();
                 if(choicBoxRole.getItems().get((Integer) role2).equals("Product Owner")) {
                        setRole(3);
            }
            if (choicBoxRole.getItems().get((Integer) role2).equals("Developer")) {
                         setRole(2);

            }
            }
        });
        
 
    }
    
    
    
}
