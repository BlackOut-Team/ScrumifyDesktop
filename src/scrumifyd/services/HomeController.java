/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.services;

import com.jfoenix.controls.JFXButton;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import scrumifyd.util.MyDbConnection;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class HomeController implements Initializable {

    private Parent fxml ; 
    
   
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


    /// Declarations 
    Connection con = null;
    PreparedStatement preparedStatement = null;
    String resultSet = null;
    
    

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

       if (event.getSource() == open_signin) {
          
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    //stage.setMaximized(true);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/scrumifyd/views/signin.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
       }
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
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/scrumifyd/views/Projects.fxml")));
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
    
  

}
      
        

