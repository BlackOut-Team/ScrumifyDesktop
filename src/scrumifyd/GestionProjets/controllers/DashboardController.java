

package scrumifyd.GestionProjets.controllers;

import com.jfoenix.controls.JFXButton;
import scrumifyd.GestionUsers.services.HomeController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Amira Doghri
 */
public class DashboardController implements Initializable {
       
    @FXML 
    private Circle ExitButton;
    @FXML 
    private Circle MinimizeButton;
    @FXML
    private Circle resizeButton;
    
  
    @FXML
    private StackPane contentPane;
   
    
    
    
    @FXML
    private JFXButton Projects;
    @FXML
    private JFXButton settings;
   

    
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO      
     
    }    
    
  
    
    @FXML
     public void ExitButton(MouseEvent event) {
          if (event.getSource() == ExitButton){
    // get a handle to the stage
              Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
    // do what you have to do
    stage.close();
    }
    }
     @FXML
     public void MinimizeButton(MouseEvent event) {
          if (event.getSource() == MinimizeButton){
    // get a handle to the stage
              Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
    // do what you have to do
    stage.setIconified(true);
    }
    }
    @FXML
    public void resizeButton(MouseEvent event) {
         if (event.getSource() == resizeButton){
    // get a handle to the stage
              Node node = (Node) event.getSource();
              Stage stage = (Stage) node.getScene().getWindow();
    // do what you have to do
    if (stage.isMaximized())
    {
        stage.setMaximized(false);
    }
    else
    {
         stage.setMaxWidth(1366);
    stage.setMaxHeight(720);
        stage.setMaximized(true);
    }
   
    }
    }
    
    
 

 

    @FXML
    private void Projects(MouseEvent event) {
        loadUI("ProjectsCurrent");
    }

    @FXML
    private void settings(MouseEvent event) {
        loadUI("settings");
    }

    @FXML
    private void settings(ActionEvent event) {
        loadUI("settings");

    }

   
   public void loadUI(String ui){
             contentPane.getChildren().clear();

         Parent root=null;
       try{
           root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/"+ui+".fxml"));
           
       } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        contentPane.getChildren().add(root);
   }
    
}
