/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.apache.commons.io.FileUtils;
import scrumifyd.GestionUsers.services.UserCrud;
import scrumifyd.ScrumifyD;
import scrumifyd.util.BCrypt;
import scrumifyd.util.MyDbConnection;
import tray.animations.AnimationType;
import tray.notification.TrayNotification;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class HomeController implements Initializable {

    UserCrud us = new UserCrud();

    private Desktop desktop = Desktop.getDesktop();
    final FileChooser fc = new FileChooser();
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

    Stage stage;

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
    @FXML
    private JFXTextField Username;
    @FXML
    public JFXTextField Avatar;
    @FXML
    public JFXButton av;

    FileInputStream fin;
    int len;
    File dest = null;
    @FXML
    public JFXButton av1;
    @FXML
    private ImageView img;
    @FXML
    private Text s;
    @FXML
    private Text txt;
    @FXML
    private Pane vbox1;
    @FXML
    private JFXButton choose;
    @FXML
    private ImageView av11;
    @FXML
    private ImageView av2;
    @FXML
    private ImageView av3;
    @FXML
    private ImageView av4;
    @FXML
    private ImageView av5;
    @FXML
    private ImageView av6;
    @FXML
    private Pane ch;

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
//        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)", "*.JPG,*.PNG");
//        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)", "*.PNG");
//        fc.getExtensionFilters().addAll(ext1, ext2);

ch.setVisible(false);
    }

    @FXML
    public void open_signin(MouseEvent event) {

        try {
            //if (event.getSource() == open_signin) {

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
            Parent root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionUsers/views/signin.fxml"));
            Scene SignupScene = open_signin.getScene();
            root.translateXProperty().set(SignupScene.getHeight());
            stackPane.getChildren().add(root);
            Timeline timeline = new Timeline();
            KeyValue keyValue = new KeyValue(root.translateXProperty(), 0, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.millis(200), keyValue);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
//            timeline.setOnFinished((ActionEvent event2) -> {
//
//                stackPane.getChildren().remove(anchorPane);
////                Scene scene = new Scene(root);
////                stage.setScene(scene);
//
//            });

        } catch (IOException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (SignUp() > 0) {

            open_signin(event);

        }

    }

    public HomeController() {
        con = MyDbConnection.getInstance().getConnexion();

    }

    private int SignUp() {
        int result = 0;
        String username = Username.getText();
        String name = Name.getText();
        String lastname = Lastname.getText();
        String email = Email.getText();
        String password = Password.getText();
        String password1 = Password1.getText();
        String pass = BCrypt.hashpw(password, BCrypt.gensalt(13));
        String conf = us.generateNewToken();
        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastname.isEmpty() || username.isEmpty() || Avatar.getText().isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");

        } else if (!password.equals(password1)) {
            setLblError(Color.TOMATO, "Passwords not identical");
            result = 0;
        } else if (!us.validateEmailAddress(email)) {
            setLblError(Color.TOMATO, "Invalid Email");
            result = 0;
        } else if (us.isUserExist(username, email)) {
            setLblError(Color.TOMATO, "An account with this email/username already exists , try logging in .");
            result = 0;
        } else {
            //query

            String sql = "INSERT INTO USER (name, lastname,username, username_canonical,email ,email_canonical, password,enabled,roles,image,confirmation_token) values ('" + name + "','" + lastname + "','" + username + "','" + username + "','" + email + "','" + email + "','" + pass + "',0,'','" + dest.getName() + "'  , '" + conf + "')";
            try {

                preparedStatement = con.prepareStatement(sql);
                // preparedStatement.setAsciiStream(1, fin);
                result = preparedStatement.executeUpdate();
                if (result > 0) {
                    setLblError(Color.GREEN, "Registration Successful..Redirecting..");
                    sendEmailSucc(email);
                    TrayNotification tray = new TrayNotification();
                    AnimationType type = AnimationType.SLIDE;

                    tray.setAnimationType(type);
                    tray.setRectangleFill(Color.valueOf("#16cabd"));
                    tray.setTitle("Scrumify App");
                    tray.setMessage("You are  one step away  ! Check your email to activate your account .");
                    Image img = new Image(ScrumifyD.class.getResourceAsStream("/scrumifyd/images/scrumify.png"));
                    tray.setImage(img);
                    //tray.setNotificationType(NotificationType.SUCCESS);
                    tray.showAndDismiss(Duration.millis(3000));

                } else {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    result = 0;

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
        if (event.getSource() == ExitButton) {
            // get a handle to the stage
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // do what you have to do
            stage.close();
        }
    }

    @FXML
    private void MinimizeButton(MouseEvent event) {
        if (event.getSource() == MinimizeButton) {
            // get a handle to the stage
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // do what you have to do
            stage.setIconified(true);
        }
    }

    @FXML
    void resizeButton(MouseEvent event) {
        if (event.getSource() == resizeButton) {
            // get a handle to the stage
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            // do what you have to do
            stage.setMaxWidth(1920);
            stage.setMaxHeight(1080);
        }
    }

    @FXML
    private void openfile(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
        ch.setVisible(false);

        Avatar.clear();
        File file = fc.showOpenDialog(stage);
        int i = file.hashCode();

        if (file != null) {
            System.out.println(file);
//            fin = new FileInputStream(file);
//            len = (int) file.length();
            dest = new File("C:\\wamp64\\www\\Scrumify\\web\\uploads\\images\\av" + i + file.getName().substring(file.getName().length() - 4));
            try {
                FileUtils.copyFile(file, dest);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(dest.getAbsoluteFile());
            printLog(Avatar, file);

        }

    }

    private void printLog(TextField Avatar, File file) {
        if (file == null) {
            return;
        }

        Avatar.appendText(file.getName() + "\n");

    }

    private void sendEmailSucc(String email) {

        try {

            String to = Email.getText();//change accordingly  destinateur
            final String user = "scrumify.application@gmail.com";//change accordingly  eky yebath
            final String password = "Blackout2020";//change accordingly  
            //1) get the session object     

            Properties properties = System.getProperties();
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.host", "smtp.gmail.com");
            properties.put("mail.smtp.auth", "true");
            properties.put("mail.smtp.port", "587");

            properties.put("mail.smtp.starttls.enable", "true");
            properties.put("mail.smtp.socketFactory.port", "465");
            properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            properties.put("mail.smtp.socketFactory.fallback", "false");

            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, password);
                }
            });

            //2) compose message     
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject("Activate new Account");

                //3) create MimeBodyPart object and set your message text     
                BodyPart messageBodyPart1 = new MimeBodyPart();
                InternetHeaders headers = new InternetHeaders();
                headers.addHeader("Content-type", "text/html; charset=UTF-8");

                String C = us.getConf(to);
                String html = "Test\n" + "Activate now" + "\n<a href='http://localhost/scrumify/web/app_dev.php/register/confirm/" + C + "'>Activate now</a>";
                MimeBodyPart body = new MimeBodyPart(headers, html.getBytes("UTF-8"));
                //messageBodyPart1.setHeader("Scrmify ", "Activate account !");
                // messageBodyPart1.setText("Activate now");
                //5) create Multipart object and add MimeBodyPart objects to this object      
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(body);
                //6) set the multiplart object to the message object  
                message.setContent(multipart);

                //7) send message  
                Transport.send(message);

            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {

            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void choose(ActionEvent event) {

          ch.setVisible(true);
    }

    @FXML
    private void select(MouseEvent event) {
          if (event.getSource() == av11 ){
              add("person3");
          }
            if (event.getSource() == av2 ){
              add("person2");
          }
              if (event.getSource() == av3 ){
              add("person4");
          }
                if (event.getSource() == av4 ){
              add("person5");
          }
                  if (event.getSource() == av5 ){
              add("person6");
          }
                    if (event.getSource() == av6 ){
              add("person1");
          }
          
            


        }
public void add(String img){
    System.out.println(img);
              Avatar.clear();

     File file = new File("C:\\Users\\Amira Doghri\\Documents\\3A-2S\\JavaFX\\ScrumifyD\\src\\scrumifyd\\images\\"+img+".png");

        int i = file.hashCode();

        if (file != null) {
            System.out.println(file);

            dest = new File("C:\\wamp64\\www\\Scrumify\\web\\uploads\\images\\av" + i + ".png");
            try {
                FileUtils.copyFile(file, dest);
                FileUtils.copyFileToDirectory(dest,new File("C:\\Users\\Amira Doghri\\Documents\\3A-2S\\JavaFX\\ScrumifyD\\src\\scrumifyd\\uploads\\images") );
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            System.out.println(dest.getAbsoluteFile());
            printLog(Avatar, file);
           
            };
                
}
    @FXML
    private void done(ActionEvent event) {
        ch.setVisible(false);
    }

    
    
}
