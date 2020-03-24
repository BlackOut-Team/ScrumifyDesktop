/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.services;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
public class SigninController implements Initializable {

    @FXML
    private JFXButton open_signup;
    @FXML
    private Label Errors;
    @FXML
    private Pane vbox;
    @FXML
    private JFXButton btnSignin;
    @FXML
    private JFXTextField Email;
    @FXML
    private JFXPasswordField Password;
     @FXML 
    private Circle ExitButton;
    @FXML 
    private Circle MinimizeButton;
    @FXML
    private Circle resizeButton;
    
     Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane anchorPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           // TODO
        if (con == null) {
            Errors.setTextFill(Color.TOMATO);
            Errors.setText("Server Error : Check");
        } else {
            Errors.setTextFill(Color.GREEN);
            Errors.setText("Server is up : Good to go");
        }
    }    

    @FXML
    private void open_signup(MouseEvent event) {
              

         try {
              
             // Animation
               Parent root=FXMLLoader.load(getClass().getResource("/scrumifyd/GestionUsers/views/Home.fxml"));
               Scene SigninScene = open_signup.getScene();
               root.translateYProperty().set(SigninScene.getHeight()); 
               stackPane.getChildren().add(root);
               Timeline timeline = new Timeline();
               KeyValue keyValue = new KeyValue(root.translateYProperty(),0,Interpolator.EASE_OUT);
               KeyFrame keyFrame = new KeyFrame(Duration.millis(200),keyValue);
               timeline.getKeyFrames().add(keyFrame);
               timeline.play();
               timeline.setOnFinished((ActionEvent event2)->{
               stackPane.getChildren().remove(anchorPane);
                   
               
               });
               
           } catch (IOException ex) {
               Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
           }
           
    }

    @FXML
    private void signInButton(MouseEvent event) {
         if (event.getSource() == btnSignin) {
            //login here
            if (logIn().equals("Success")) {
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

    public SigninController() {
                con = MyDbConnection.getInstance().getConnexion();

    }
    //we gonna use string to check for status
    private String logIn() {
        String status = "Success";
        String email = Email.getText();
        String password = Password.getText();
        if(email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            //query
            String sql = "SELECT * FROM PERSON Where email = ? and password = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }
        
        return status;
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


