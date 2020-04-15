/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.util;

import com.jfoenix.controls.JFXButton;
import doryan.windowsnotificationapi.fr.Notification;
import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.TrayIcon;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author Amira Doghri
 */
public class ScrumifyUtil {


    public static final String ICON_IMAGE_LOC = "/resources/icon.png";
    public static final String MAIL_CONTENT_LOC = "/resources/mail_content.html";
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");



    
  

    public static void initPDFExprot(StackPane rootPane, Node contentPane, Stage stage, List<List> data) throws AWTException, MalformedURLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as PDF");
        FileChooser.ExtensionFilter extFilter
                = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
        fileChooser.getExtensionFilters().add(extFilter);
        File saveLoc = fileChooser.showSaveDialog(stage);
        ListToPDF ltp = new ListToPDF();
        boolean flag = ltp.doPrintToPdf(data, saveLoc, ListToPDF.Orientation.LANDSCAPE);
        JFXButton okayBtn = new JFXButton("Okay");
        JFXButton openBtn = new JFXButton("View File");
        openBtn.setOnAction((ActionEvent event1) -> {
            try {
                Desktop.getDesktop().open(saveLoc);
            } catch (Exception exp) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("load pdf");
                alert.setHeaderText("Results:");
                alert.setContentText("Error loading pdf!");
                alert.showAndWait();
            }
        });
        if (flag) {
               Notification.sendNotification("Scrumify App", "PDF file generated !",TrayIcon.MessageType.INFO );
        }
    }

   
    public static boolean validateEmailAddress(String emailID) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(emailID).matches();
    }

    public static void openFileWithDesktop(File file) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(ScrumifyUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}


