/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;
import scrumifyd.GestionTasks.services.members_services;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import scrumifyd.GestionTasks.models.task;
import scrumifyd.GestionTasks.services.task_services;
import scrumifyd.GestionTasks.models.media;
import scrumifyd.GestionTasks.services.media_services;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.io.File;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import scrumifyd.GestionTasks.services.media_services;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import scrumifyd.GestionTasks.models.member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class Add_taskController implements Initializable {

    @FXML
    private TextField txt_title;
    @FXML
    private TextField txt_priority;
    @FXML
    private TextArea txt_description;
    @FXML
    private Button btn_files;
    @FXML
    private Button new_task;
    @FXML
    private JFXTimePicker time_picker;
    @FXML
    private JFXDatePicker date_picker;
    
    
    
    
    String date,time,due,path,name,title,description,p;
    int priority;
    Date due_date;
    Time due_time;
    task_services task= new task_services();
    media_services media=new media_services();
    @FXML
    private ListView list_file;
    @FXML
    private MenuItem member_btn;
    @FXML
    private TableView<member> members_table;
    List<member> members=new ArrayList<>();
    @FXML
    private TableColumn<member, Integer> id;
    @FXML
    private TableColumn<member, String> name_p;
    @FXML
    private TableColumn<member, CheckBox> select;
    @FXML
    private Button check;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       update_table();
    }   

    @FXML
    private void add_files(ActionEvent event) {
        FileChooser file= new FileChooser();
        file.setInitialDirectory(new File("C:\\Users\\mahdi\\Documents\\NetBeansProjects"));
        file.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("PDF Files","*.pdf"));
        List<File> selectedFiles=file.showOpenMultipleDialog(null);
        if(selectedFiles !=null){
            for(int i=0;i<selectedFiles.size();i++){
                list_file.getItems().add(selectedFiles.get(i).getAbsolutePath());
                path=selectedFiles.get(i).getAbsolutePath();
                name=selectedFiles.get(i).getName();
            }
        }else{
            System.out.println("File not found");
        }
         System.out.println(path+"  "+name);
    }
      @FXML
    private void new_task(ActionEvent event) {
        title=txt_title.getText();
        description=txt_description.getText();
        p=txt_priority.getText();
        priority=Integer.parseInt(p);
        date=date_picker.getValue().toString();
        time=time_picker.getValue().toString();
        due=date+" "+time;
        String all=btn_check(event);
        task t =new task(priority,title,description,due,all);
        task.create(t);
        media.ajout_media(name, path);
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        try {
            Parent root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(Add_taskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        FXMLDocumentController nicc = loader.getController();
        nicc.setref(1);
    }

    @FXML
    private void handle_members(ActionEvent event) {
        
    }
    public void update_table(){
        members_services ms=new members_services();
        name_p.setCellValueFactory(new PropertyValueFactory<>("nom"));     
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        select.setCellValueFactory(new PropertyValueFactory<>("check"));
        members=ms.update_members();
        ObservableList<member> mList1 =FXCollections.observableArrayList(members);
        members_table.setItems(mList1);
    }

    @FXML
    private String btn_check(ActionEvent event) {
        String all="";
        for(member m : members_table.getItems()){
            if(m.getCheck().isSelected()){
                
                int id15=m.getId();
                String grp=Integer.toString(id15);
                all+=grp+",";
            }
        }
        return all;
    }
    
}
