/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class emptyController implements Initializable {

    @FXML
    private StackPane contentPane;
    @FXML
    public JFXButton addP;
    @FXML
    public JFXButton addT;
    int user_id ; 
    @FXML
    private Text label_empty;
    @FXML
    public Label lbl_allProjects;
    @FXML
    public Label lbl_currentprojects;
 
    @FXML
    public Label lbl_completed;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addP(MouseEvent event) {
        try {
            contentPane.getChildren().clear();
            
            FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/AddP.fxml"));
            Parent root = (Parent) loader.load();
            AddPController sp= loader.getController();
            sp.setUserId(user_id);
            
            
            contentPane.getChildren().add(root);
//loadUI("GestionProjets", "AddP");
        } catch (IOException ex) {
            Logger.getLogger(emptyController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
    public void setLabel(String label){
        this.label_empty.setStyle("-fx-translate-y: 5px;");
        this.label_empty.setText(label);
    }
    public void setLabelColor(Label label){
        label.setStyle("-fx-text-fill: #16cabd;"+"-fx-font-weight: bold;");
    }
    public void setLabelColor1(Label label){
        label.setStyle("-fx-text-fill: #000000;" + "-fx-font-weight:regular;");
    }
  public void loadUI(String module , String ui){
             contentPane.getChildren().clear();

         Parent root=null;
       try{
           root = FXMLLoader.load(getClass().getResource("/scrumifyd/"+module+"/views/"+ui+".fxml"));
           
       } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        contentPane.getChildren().add(root);
   }
public void setUserId(int user_id){
    this.user_id=user_id;
}
    @FXML
    private void addT(MouseEvent event) {
                loadUI("GestionTeams","teams");

    }

  
    @FXML
    private void currentProjects(MouseEvent event) {
        loadUI("GestionProjets","ProjectsCurrent");
    }

 

    @FXML
    private void completedProjects(MouseEvent event) {
        loadUI("GestionProjets","ProjectsCompleted");

    }

    @FXML
    private void allProjects(MouseEvent event) {
        loadUI("GestionProjets","Projects");
    }
 

}
