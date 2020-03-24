/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.ProjectService;

/**
 *
 * @author Amira Doghri
 */
public class ScrumifyD extends Application {
    
    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void start(Stage stage) throws Exception {
        // Parent root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionUsers/views/Home.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/Dashboard.fxml"));

         //borderless .  
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(
      new Image(
      ScrumifyD.class.getResourceAsStream( "/scrumifyd/images/scrumify.png" ))); 
        stage.setResizable(true);
      
       
        //stage.setMaximized(false);

        //grab your root here
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
           });
        
         root.setOnMouseDragged((MouseEvent event) -> {
             stage.setX(event.getScreenX() - xOffset);
             stage.setY(event.getScreenY() - yOffset);
           });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
      

    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
  
    }
  
}
