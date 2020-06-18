/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.controllers;

import com.github.sarxos.webcam.Webcam;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionProjets.controllers.ProjectsController;
import scrumifyd.GestionProjets.services.UserSession;
import scrumifyd.GestionUsers.models.User;
import scrumifyd.GestionUsers.services.UserCrud;
import scrumifyd.util.BCrypt;

/**
 * FXML Controller class
 *
 * @author AmiraDoghri
 */
public class editProfileController implements Initializable {
    
    @FXML
    private StackPane contentPane;
    @FXML
    public ImageView avatar;
    @FXML
    public JFXTextField name;
    @FXML
    public JFXTextField lastname;
    @FXML
    public JFXTextField username;
    @FXML
    public JFXTextField email;
    private JFXButton edit;
    @FXML
    private JFXButton reset;
    @FXML
    private FontAwesomeIconView picture;
    @FXML
    private FontAwesomeIconView camera;
    private FontAwesomeIconView draw_av;
    
    public int user_id;
    @FXML
    private JFXTextField current;
    @FXML
    private JFXTextField new1;
    @FXML
    private JFXTextField new2;
    @FXML
    private JFXButton reset1;
    
    UserCrud uu = new UserCrud();
    @FXML
    private FontAwesomeIconView save;
    @FXML
    private FontAwesomeIconView back;
    @FXML
    private Label Errors;
    @FXML
    private Pane ch;
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
    private JFXButton av;
    @FXML
    private Pane a5;
    @FXML
    private Pane a2;
    @FXML
    private Pane a4;
    @FXML
    private Pane a6;
    @FXML
    private Pane a3;
    @FXML
    private Pane a1;
    String c;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        ch.setVisible(false);
        current.setVisible(false);
        new1.setVisible(false);
        new2.setVisible(false);
        reset1.setVisible(false);
        back.setVisible(false);
    }
    
    @FXML
    private void reset(ActionEvent event) {
        //hide first form
        name.setVisible(false);
        lastname.setVisible(false);
        username.setVisible(false);
        email.setVisible(false);
        avatar.setVisible(false);
        picture.setVisible(false);
        camera.setVisible(false);
        draw_av.setVisible(false);
        reset.setVisible(false);
        //edit.setVisible(false);

        //show reset form 
        current.setVisible(true);
        new1.setVisible(true);
        new2.setVisible(true);
        reset1.setVisible(true);
        back.setVisible(true);
    }
    
    @FXML
    private void openchooser(MouseEvent event) {
        File dest = null;
        Stage stage = null;
        final FileChooser fc = new FileChooser();
        File file = fc.showOpenDialog(stage);
        int i = file.hashCode();
        
        if (file != null) {
            System.out.println(file);
            
            dest = new File("C:\\wamp64\\www\\Scrumify\\web\\uploads\\images\\av" + i + file.getName().substring(file.getName().length() - 4));
            try {
                FileUtils.copyFile(file, dest);
                FileUtils.copyFileToDirectory(dest, new File("C:\\Users\\Amira Doghri\\Documents\\3A-2S\\JavaFX\\ScrumifyD\\src\\scrumifyd\\uploads\\images"));
                
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(dest.getAbsoluteFile());
            System.out.println(dest.getName());
            if (uu.updateAv(dest.getName(), user_id)) {
                System.out.println("done1");
                
                avatar.setImage(new Image("/scrumifyd/uploads/images/" + dest.getName()));
                System.out.println("done");
            }
        }
    }

//    @FXML
//    private void openwebcam(MouseEvent event) {
//        //  Webcam.setDriver(new OpenImajDriver());
//
//        Webcam buildin = Webcam.getDefault(); // build-in laptop camera
//        buildin.open();
//        //ImageIO.write(buildin.getImage(), "PNG", new File("test.png"));
//
//        // BufferedImage image1 = buildin.getImage();
//        //System.out.println(image1);
//        // BufferedImage image2 = usb.getImage();
//// do with image1 and image2 whatever you want
//    }
    @FXML
    private void openill(MouseEvent event) {
        ch.setVisible(true);
    }
    
    @FXML
    private void resetdone(ActionEvent event) throws SQLException {
        if (event.getSource() == reset1) {
            
            if (BCrypt.checkpw(current.getText(), uu.getP(user_id))) {
                System.out.println("done");
                if (new1.getText().equals(new2.getText())) {
                    System.out.println("conforme");
                } else {
                    System.out.println("non conforme");
                }
                
                if (uu.resetP(user_id, new1.getText())) {
                    System.out.println("succes");
                } else {
                    System.out.println("error");
                }
            } else {
                System.err.println("error bcrypt");
            }
        }
    }
    
    @FXML
    private void deleteAcc(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete account", ButtonType.YES, ButtonType.CANCEL);
        alert.setTitle("Delete account");
        alert.setHeaderText("Are you sure to delete this account ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            
            if (uu.deleteAcc(user_id)) {
                try {
                    System.out.println("done");
                    alert.hide();
                    //logout
                    UserSession sess = UserSession.getInstace(user_id);
                    sess.cleanUserSession();
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/scrumifyd/GestionUsers/views/signin.fxml")));
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(editProfileController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            } else {
                System.out.println("error");
                alert.hide();
            }
            System.out.println("Ok pressed");
        } else {
            System.out.println("canceled");
        }
    }
    
    @FXML
    private void back(MouseEvent event) {
        try {
            contentPane.getChildren().clear();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionUsers/views/Editprofile.fxml"));
            Parent root = (Parent) loader.load();
            editProfileController sp = loader.getController();
            sp.user_id = user_id;
            User u = new User();
            UserCrud U = new UserCrud();
            u = U.getUser(user_id);
            sp.name.setText(u.getName());
            sp.lastname.setText(u.getLastname());
            sp.username.setText(u.getUsername());
            sp.email.setText(u.getEmail());
            String ava = u.getImage();
            sp.avatar.setImage(new Image("/scrumifyd/uploads/images/" + ava));
            
            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(editProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void setLblError(Color color, String text) {
        Errors.setTextFill(color);
        Errors.setText(text);
        System.out.println(text);
    }
    
    @FXML
    private void save(MouseEvent event) {

        // TODO
        if (name.getText().isEmpty() || lastname.getText().isEmpty() || username.getText().isEmpty() || email.getText().isEmpty()) {
            setLblError(Color.TOMATO, "Empty/wrong credentials");
            
        } else if (!uu.validateEmailAddress(email.getText())) {
            setLblError(Color.TOMATO, "Invalid Email");
            
        } else {
            
            User u = new User();
            u.setId(user_id);
            u.setName(name.getText());
            u.setUsername(username.getText());
            u.setLastname(lastname.getText());
            
            u.setEmail(email.getText());
            if (uu.updateProfile(u)) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Update profile", ButtonType.OK);
                alert.setTitle("Update profile");
                alert.setHeaderText("Profile successfully updated");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    try {
                        alert.hide();
                        
                        contentPane.getChildren().clear();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionProjets/views/Projects.fxml"));
                        Parent root = (Parent) loader.load();
                        SigninController s = new SigninController();
                        user_id = s.user.getUserId();
                        String ava = s.user.getAvatar(user_id);
                        this.username.setText("" + s.user.getUsername(user_id));
                        this.avatar.setImage(new Image("/scrumifyd/uploads/images/" + ava));
                        ProjectsController sp = loader.getController();
                        
                        sp.setUserId(user_id);
                        
                        contentPane.getChildren().add(root);
                    } catch (IOException ex) {
                        Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    @FXML
    private void select(MouseEvent event) {
        if (event.getSource() == av11) {
            a1.setStyle("-fx-border-color:black");
            a2.setStyle("-fx-border-style:none");
            a3.setStyle("-fx-border-style:none");
            a4.setStyle("-fx-border-style:none");
            a5.setStyle("-fx-border-style:none");
            a6.setStyle("-fx-border-style:none");
            
            c = "person3";
            
        }
        if (event.getSource() == av2) {
            a2.setStyle("-fx-border-color:black");
            a1.setStyle("-fx-border-style:none");
            a3.setStyle("-fx-border-style:none");
            a4.setStyle("-fx-border-style:none");
            a5.setStyle("-fx-border-style:none");
            a6.setStyle("-fx-border-style:none");
            c = "person2";
        }
        if (event.getSource() == av3) {
            a3.setStyle("-fx-border-color:black");
            a2.setStyle("-fx-border-style:none");
            a1.setStyle("-fx-border-style:none");
            a4.setStyle("-fx-border-style:none");
            a5.setStyle("-fx-border-style:none");
            a6.setStyle("-fx-border-style:none");
            c = "person4";
        }
        if (event.getSource() == av4) {
            a4.setStyle("-fx-border-color:black");
            a2.setStyle("-fx-border-style:none");
            a3.setStyle("-fx-border-style:none");
            a1.setStyle("-fx-border-style:none");
            a5.setStyle("-fx-border-style:none");
            a6.setStyle("-fx-border-style:none");
            c = "person5";
        }
        if (event.getSource() == av5) {
            a5.setStyle("-fx-border-color:black");
            a2.setStyle("-fx-border-style:none");
            a3.setStyle("-fx-border-style:none");
            a4.setStyle("-fx-border-style:none");
            a1.setStyle("-fx-border-style:none");
            a6.setStyle("-fx-border-style:none");
            c = "person6";
        }
        if (event.getSource() == av6) {
            a6.setStyle("-fx-border-color:black");
            a2.setStyle("-fx-border-style:none");
            a3.setStyle("-fx-border-style:none");
            a4.setStyle("-fx-border-style:none");
            a5.setStyle("-fx-border-style:none");
            a1.setStyle("-fx-border-style:none");
            c = "person1";
        }
        
    }
    
    public void add(String img) {
        File dest = null;
        String ava = null;
        System.out.println(img);

        
        File file = new File("C:\\Users\\Amira Doghri\\Documents\\3A-2S\\JavaFX\\ScrumifyD\\src\\scrumifyd\\images\\" + img + ".png");
        int N=0;
        Random random = new Random();
        int io= random.nextInt(2367+N);
        N++;
        int i = file.hashCode()+io;
        
        if (file != null) {
           
                System.out.println(file);
                
                dest = new File("C:\\wamp64\\www\\Scrumify\\web\\uploads\\images\\av" + i + ".png");
                try {
                    FileUtils.copyFile(file, dest);
                    FileUtils.copyFileToDirectory(dest, new File("C:\\Users\\Amira Doghri\\Documents\\3A-2S\\JavaFX\\ScrumifyD\\src\\scrumifyd\\uploads\\images"));
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("try again");
                }
                
                System.out.println(dest.getAbsoluteFile());
                UserCrud U = new UserCrud();
                System.out.println(dest.getName());
                U.updateAv(dest.getName(), user_id);
                System.out.println("yuyuyuyu:"+U.getUser(user_id).getImage());
                //this.avatar.setImage(new Image("/scrumifyd/uploads/images/" + U.getUser(user_id).getImage()));
//                contentPane.getChildren().clear();
//                
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionUsers/views/Editprofile.fxml"));
//                Parent root = (Parent) loader.load();
//                editProfileController sp = loader.getController();
//                sp.user_id = user_id;
//                System.out.println(user_id);
//                //User u = new User();
//                User u = U.getUser(user_id);
//                sp.name.setText(u.getName());
//                sp.lastname.setText(u.getLastname());
//                sp.username.setText(u.getUsername());
//                sp.email.setText(u.getEmail());
//                String ava1 = u.getImage();
//                System.out.println(ava1);
//                sp.avatar.setImage(new Image("/scrumifyd/uploads/images/" + ava1));
//                
//                contentPane.getChildren().add(root);
//          
       };
      
    }
    
    @FXML
    private void done(ActionEvent event) {
        add(c);
        ch.setVisible(false);        

    }
    
    @FXML
    private void openfile(ActionEvent event) {
    }
    
}
