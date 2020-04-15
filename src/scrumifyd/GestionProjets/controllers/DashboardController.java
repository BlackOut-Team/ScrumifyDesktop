

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
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import scrumifyd.GestionUsers.services.SigninController;
import scrumifyd.ScrumifyD;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

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
    @FXML
    private AnchorPane Dashboard;
    @FXML
    private JFXButton teams;
   
    
  
    
    int user_idd;
    @FXML
    private JFXButton deconnexion;
    @FXML
    private JFXButton CalendarButton;
    @FXML
    private Label username;
    @FXML
    private JFXButton tasksOpen;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
        try {
            // TODO
            contentPane.getChildren().clear();

            FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/Projects.fxml"));
            Parent root = (Parent) loader.load();
            SigninController s = new SigninController();
            user_idd = s.user.getUserId();
            this.username.setText("" + s.user.getUsername(user_idd));
            ProjectsController sp= loader.getController();
            sp.setUserId(user_idd);
            
            
            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
    }    

    public void setContentPane(StackPane contentPane) {
        this.contentPane = contentPane;
    }

    public StackPane getContentPane() {
        return contentPane;
    }
    
  public void setUserId(int user_id){
      this.user_idd=user_id;
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
    
    
    private void addP(MouseEvent event) {
        loadUI("GestionProjets","AddP");
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

    private void addT(MouseEvent event) {
                loadUI("GestionTeams","Team");

    }

 

    @FXML
    private void Projects(MouseEvent event) {
        try {
            contentPane.getChildren().clear();
            
            FXMLLoader  loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/Projects.fxml"));
            Parent root = (Parent) loader.load();
            ProjectsController sp= loader.getController();
            sp.setUserId(user_idd);
            
            
            contentPane.getChildren().add(root);
//loadUI("GestionProjets","Projects");
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void settings(MouseEvent event) {
        loadUI("GestionProjets","settings");
    }

    private void settings(ActionEvent event) {
        loadUI("GestionProjets","settings");

    }

   


    @FXML
    private void openTeams(MouseEvent event) {
        loadUI("GestionTeams","Team");
    }

    @FXML
    private void deconnexion(MouseEvent event) {
              if (event.getSource() == deconnexion) {
              
                try {
               TrayNotification tray = new TrayNotification();
                AnimationType type= AnimationType.POPUP;
                tray.setAnimationType(type);
                tray.setRectangleFill(Color.valueOf("#16cabd"));
                tray.setTitle("Scrumify App");
                tray.setMessage("Logged out  !");
                Image img = new Image (ScrumifyD.class.getResourceAsStream("/scrumifyd/images/scrumify.png"));
                tray.setImage(img);
                //tray.setNotificationType(NotificationType.SUCCESS);
                tray.showAndDismiss(Duration.millis(3000));
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/scrumifyd/GestionUsers/views/signin.fxml")));
                    stage.setScene(scene);                
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SigninController.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    }

    @FXML
    private void CalendarButton(MouseEvent event) {
          loadUI("GestionProjets","calendarandstat");
    }

    @FXML
    private void tasksOpen(MouseEvent event) {
        loadUI("GestionTasks", "Taskss");
    }
    
}
