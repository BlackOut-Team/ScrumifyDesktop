/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.services;

import scrumifyd.GestionUsers.services.HomeController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author oXCToo
 */
public class ProjectsController implements Initializable {
    
    @FXML 
    private VBox pnl_scroll;
      
    @FXML 
    private Circle ExitButton;
    @FXML 
    private Circle MinimizeButton;
    @FXML
    private Circle resizeButton;
    @FXML
    private FontAwesomeIconView AddProject;
    @FXML
    private AnchorPane Dashboard;
    
    
    private void handleButtonAction(MouseEvent event) {        
       refreshNodes();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
         refreshNodes();
    }    
    
    private void refreshNodes()
    {
        pnl_scroll.getChildren().clear();
        
        Node [] nodes = new  Node[15];
        
        for(int i = 0; i<10; i++)
        {
            try {
                nodes[i] = (Node)FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/Item.fxml"));
               pnl_scroll.getChildren().add(nodes[i]);
                
            } catch (IOException ex) {
                Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }  
        
        
    }
    @FXML
    private void ExitButton(MouseEvent event) {
          if (event.getSource() == ExitButton){
    // get a handle to the stage
              Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
    // do what you have to do
    stage.close();
    }
    }
    private void MinimizeButton(MouseEvent event) {
          if (event.getSource() == MinimizeButton){
    // get a handle to the stage
              Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
    // do what you have to do
    stage.setIconified(true);
    }
    }
    @FXML
    void resizeButton(MouseEvent event) {
         if (event.getSource() == resizeButton){
    // get a handle to the stage
              Node node = (Node) event.getSource();
              Stage stage = (Stage) node.getScene().getWindow();
    // do what you have to do
    stage.setMaxWidth(1920);
    stage.setMaxHeight(1080);
    }
    }

   /* private void AddProject(MouseEvent event) {
     
           if (event.getSource() == AddProject) {
          
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/scrumifyd/views/AddP.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
    }*/
    
   
   
    
}
