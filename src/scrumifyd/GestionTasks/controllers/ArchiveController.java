/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import scrumifyd.GestionTasks.models.task;
import scrumifyd.GestionTasks.services.task_services;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class ArchiveController implements Initializable {

    @FXML
    private TableColumn<task, String> title;
    @FXML
    private TableColumn<task, String> description;
    @FXML
    private TableColumn<task, Date> created;
    @FXML
    private TableColumn<task, Date> updated;
    @FXML
    private TableColumn<task, String> finished;
    @FXML
    private TableColumn<task, Integer> priority;
    @FXML
    private TableColumn<task, String> status;
    @FXML
    private TableView<task> table_archive;
    
    List<task> list_details= new ArrayList<>();
    int id,id1,priority1;
    String title_details,desc_details,finished1,status1;
    Date created1,updated1;
    task task=new task();
    task_services ts=new task_services();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        update_table();
    }  
     public void setid(int id15){
        this.id1=id15;
        System.out.println("id el set: "+id1);
    }
    public void update_table()
    {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));     
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        updated.setCellValueFactory(new PropertyValueFactory<>("updated"));     
        finished.setCellValueFactory(new PropertyValueFactory<>("finished"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));     
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));     
        list_details=ts.archive_list();
        ObservableList<task> ls =FXCollections.observableArrayList(list_details);
        table_archive.setItems(ls);
    }
}
