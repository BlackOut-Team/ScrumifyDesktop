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
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import scrumifyd.GestionTasks.models.task;
import scrumifyd.GestionTasks.services.members_services;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import scrumifyd.GestionTasks.services.task_services;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mahdi
 */
public class DetailsController implements Initializable {
    task task=new task();
    task_services ts=new task_services();
    members_services ms=new members_services();
    @FXML
    private TableView<task> table_details;
    @FXML
    private ListView<String> l_files;
    @FXML
    private ListView<String> l_members;
    @FXML
    private TableColumn<task,String> description;
    @FXML
    private TableColumn<task,Date> created;
    @FXML
    private TableColumn<task,Date> updated;
    @FXML
    private TableColumn<task,Integer> priority;
    @FXML
    private TableColumn<task,String> status;
    @FXML
    private TableColumn<task,String> title;
    @FXML
    private TableColumn<task,String> finished;
    List<task> list_details= new ArrayList<>();
    int id,priority1;
    String title_details,desc_details,finished1,status1,tit;
    Date created1,updated1;
    List<task> liste_details=new ArrayList<>();
    String  i;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    
    }  
    public void setid(int id){
        this.id=id;
        i=Integer.toString(id);
        System.out.println("id el set: "+i);
    }
    public void settext(int id){
        String t=Integer.toString(id);
    }
    void settask(List<task> liste_details) {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));     
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        created.setCellValueFactory(new PropertyValueFactory<>("created"));    
        updated.setCellValueFactory(new PropertyValueFactory<>("updated"));     
        finished.setCellValueFactory(new PropertyValueFactory<>("finished")); 
        priority.setCellValueFactory(new PropertyValueFactory<>("priority"));
        status.setCellValueFactory(new PropertyValueFactory<>("status")); 
        ObservableList<task> ls =FXCollections.observableArrayList(liste_details);
        table_details.setItems(ls);
           for (int j = 0; j < liste_details.size(); j++) {
        tit=liste_details.get(j).getTitle();
            System.out.println("ggg "+tit);
    }
       String member=ts.get_members(tit);
       if(member.equals(""))
        {
            System.out.println("pas de membres");
        }
       else
       {
           final String SEPARATEUR = ",";
 
        String mots[] = member.split(SEPARATEUR);
        List<String> names=new ArrayList<>();
        
        for (int i = 0; i < mots.length; i++) {
           names.add(ms.get_name(Integer.valueOf(mots[i])));
      }
        ObservableList<String> namesmembers =FXCollections.observableArrayList(names);
        l_members.setItems(namesmembers);
       }
    }
    
}
