/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.services;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Shape;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import scrumifyd.util.MyDbConnection;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class HomeController implements Initializable {

  
    
   
    @FXML
    private Label Errors;
    @FXML
    private TextField Name;
    @FXML
    private TextField Lastname;
    @FXML
    private TextField Email;
    @FXML
    private TextField Password;
    @FXML
    private TextField Password1;
    @FXML
    private Button open_signin;
    @FXML
    private Button btnSignup;
    @FXML 
    private Circle ExitButton;
    @FXML 
    private Circle MinimizeButton;
    @FXML
    private Circle resizeButton;



    /// Declarations 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Pane vbox;
    @FXML
    private StackPane stackPane;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
        if (con == null) {
            Errors.setTextFill(Color.TOMATO);
            Errors.setText("Server Error : Check");
        } else {
            Errors.setTextFill(Color.GREEN);
            Errors.setText("Server is up : Good to go");
        }
    }    
    @FXML
    public void open_signin(MouseEvent event) {

       //if (event.getSource() == open_signin) {
          
           try {
               /*try {
               
               //add you loading or delays - ;-)
               Node node = (Node) event.getSource();
               Stage stage = (Stage) node.getScene().getWindow();
               //stage.setMaximized(true);
               stage.close();
               Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/scrumifyd/GestionUsers/views/signin.fxml")));
               
               stage.setScene(scene);
               stage.show();
               
               } catch (IOException ex) {
               System.err.println(ex.getMessage());
               }*/
               Parent root=FXMLLoader.load(getClass().getResource("/scrumifyd/GestionUsers/views/signin.fxml"));
               Scene SignupScene = open_signin.getScene();
               root.translateXProperty().set(SignupScene.getHeight()); 
               stackPane.getChildren().add(root);
               Timeline timeline = new Timeline();
               KeyValue keyValue = new KeyValue(root.translateXProperty(),0,Interpolator.EASE_IN);
               KeyFrame keyFrame = new KeyFrame(Duration.millis(200),keyValue);
               timeline.getKeyFrames().add(keyFrame);
               timeline.play();
               timeline.setOnFinished((ActionEvent event2)->{
               stackPane.getChildren().remove(anchorPane);
                   
               
               });
               
           } catch (IOException ex) {
               Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
           }
           

        //    }
           
          
       }
    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignup) {
            //login here
            if (SignUp()>0){
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/scrumifyd/GestionProjets/views/Dashboard.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }



    public HomeController() {
        con = MyDbConnection.getInstance().getConnexion();

    }

    private int SignUp() {
        int result = 0 ;
        String name=Name.getText();
        String lastname=Lastname.getText();
        String email = Email.getText();
        String password = Password.getText();
        String password1 = Password1.getText();
        if(email.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
      
        } else if (!password.equals(password1))
        {
            setLblError(Color.TOMATO, "Passwords not identical");
            result=0;
        }
        else {
            //query
            String sql = "INSERT INTO PERSON (name ,lastname , email , password) values ('"+name+"','"+lastname+"','"+email+"','"+password+"')";
            try {
                preparedStatement = con.prepareStatement(sql);
              
                result = preparedStatement.executeUpdate();
                if ( result > 0 ) {
                         setLblError(Color.GREEN, "Registration Successful..Redirecting..");

               
                } else {
                         setLblError(Color.TOMATO, "Enter Correct Email/Password");
                              result=0;

                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
    
        
        return result;
    }
    
    private void setLblError(Color color, String text) {
        Errors.setTextFill(color);
        Errors.setText(text);
        System.out.println(text);
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
        @FXML
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
}
      
        

