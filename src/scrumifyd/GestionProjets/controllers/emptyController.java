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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class emptyController implements Initializable {

    @FXML
    private StackPane contentPane;
    @FXML
    private JFXButton addP;
    @FXML
    private JFXButton addT;
int user_id ; 
    /**
     * Initializes the controller class.
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
        } catch (IOException ex) {
            Logger.getLogger(emptyController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
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
    
    
    
    
 

 

}
