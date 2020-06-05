/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionProjets.controllers.EditSprintController;
import scrumifyd.GestionProjets.controllers.ProjectsController;
import scrumifyd.GestionProjets.models.Project;
import scrumifyd.GestionProjets.services.InterfaceProjet;
import scrumifyd.GestionProjets.services.ProjectService;
import scrumifyd.GestionProjets.services.UserSession;
import scrumifyd.GestionUsers.models.User;
import scrumifyd.GestionUsers.services.UserCrud;
import scrumifyd.ScrumifyD;
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
    @FXML
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
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
        edit.setVisible(false);

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

    @FXML
    private void openwebcam(MouseEvent event) {
    }

    @FXML
    private void openill(MouseEvent event) {
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

}
