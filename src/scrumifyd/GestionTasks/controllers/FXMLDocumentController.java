/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import scrumifyd.GestionTasks.models.task;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TableColumn;
import scrumifyd.GestionTasks.services.task_services;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import javafx.scene.control.MenuItem;
import scrumifyd.GestionTasks.controllers.Menu_editController;
import java.util.Date;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author mahdi
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private TableView<task> todo;
    @FXML
    private TableView<task> doing;
    @FXML
    private TableView<task> done;
    @FXML
    private ImageView btn_add;
    List<task> listtodo= new ArrayList<>();
    List<task> listdoing= new ArrayList<>();
    List<task> listdone= new ArrayList<>();
    task_services task=new task_services();
    @FXML
    private TableColumn<task,String> title1;
    @FXML
    private TableColumn<task, String> title2;
    @FXML
    private TableColumn<task, String> title3;
    @FXML
    private MenuItem menu_edit;
    @FXML
    TableColumn<task, Integer> col_id;
    int id,eta,prio,id1,priority;
    String title_edit,description_edit,title_details,desc_details,finished;
    Date created,updated;
    @FXML
    private TextField hethahowa;
    
    List<task> liste_recherche=new ArrayList<>();
    List<task> liste_details=new ArrayList<task>();
    @FXML
    private MenuItem delete_btn;
    @FXML
    private MenuItem move_btn;
    @FXML
    private MenuItem details_btn;
    @FXML
    private MenuItem move_done;
    @FXML
    private MenuItem edit_doing;
    @FXML
    private MenuItem delete_doing;
    @FXML
    private MenuItem showdoing;
    @FXML
    private Button btn_archive;
    @FXML
    private AnchorPane parent;
    
        pie_chart_1 pdz=new pie_chart_1();
        List <String> nodet = new ArrayList<>();
    @FXML
    private Pane search;
    @FXML
    private JFXButton btn_stat;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refresh();
    }  
    

    @FXML
    private void add_task(MouseEvent event) {
    }
    
    public void setref(int i){
        i=1;
        if(i==1){
            refresh();
        }
    }

    public void update_tables()
    {   
        //todo_table
        title1.setCellValueFactory(new PropertyValueFactory<>("title"));     
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        listtodo=task.update_todo();
        ObservableList<task> todo_list =FXCollections.observableArrayList(listtodo);
        todo.setItems(todo_list);
        
        //doing_table
        title2.setCellValueFactory(new PropertyValueFactory<>("title"));
        listdoing=task.update_doing();
        ObservableList<task> doing_list =FXCollections.observableArrayList(listdoing);
        doing.setItems(doing_list);
        
        //done table
        title3.setCellValueFactory(new PropertyValueFactory<>("title"));
        listdone=task.update_done();
        ObservableList<task> done_list =FXCollections.observableArrayList(listdone);
        done.setItems(done_list);
    }
    public void refresh(){
    todo.getItems().clear();
    update_tables();
}
    
    @FXML
    private void handleRequest_edit(ActionEvent event) {
        
         task ta=todo.getSelectionModel().getSelectedItem();
         id=ta.getId();
         hethahowa.setText(Integer.toString(id));
         System.out.println(id);
         hello_todo();
        FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("menu_edit.fxml"));
            
               
        try {
             Parent root = loader.load();
             Menu_editController nicc = loader.getController();
             nicc.setid(id);
             nicc.settitle(title_edit);
             nicc.setdescription(description_edit);
             nicc.setetat(eta);
             nicc.setprio(prio);
            // hethahowa.getScene().setRoot(root);
             Stage s=new Stage();
             s.setScene(new Scene(root));
             s.show();
        } catch (IOException ex) {
               System.out.println(ex.getMessage());
        }
    }
    public void hello_todo(){
        liste_recherche=task.recherche_todo(id);
        for(int i=0;i<liste_recherche.size();i++){
            title_edit=liste_recherche.get(i).getTitle();
            description_edit=liste_recherche.get(i).getDescription();
            prio=liste_recherche.get(i).getPriority();
            eta=liste_recherche.get(i).getEtat();
        }
    }
    public void hello_doing(){
        liste_recherche=task.recherche_doing(id);
        for(int i=0;i<liste_recherche.size();i++){
            title_edit=liste_recherche.get(i).getTitle();
            description_edit=liste_recherche.get(i).getDescription();
            prio=liste_recherche.get(i).getPriority();
            eta=liste_recherche.get(i).getEtat();
        }
    }
    public void hello_done(){
        liste_recherche=task.recherche_done(id);
        for(int i=0;i<liste_recherche.size();i++){
            title_edit=liste_recherche.get(i).getTitle();
            description_edit=liste_recherche.get(i).getDescription();
            prio=liste_recherche.get(i).getPriority();
            eta=liste_recherche.get(i).getEtat();
        }
    }
    public void hello1(){
        liste_details=task.update_details(id);
        for(int i=0;i<liste_details.size();i++){
            id1=id;
            title_details=liste_details.get(i).getTitle();
            desc_details=liste_details.get(i).getDescription();
            priority=liste_details.get(i).getPriority();
            created=liste_details.get(i).getCreated();
            updated=liste_details.get(i).getUpdated();
            finished=liste_details.get(i).getFinished();
        }
    }

    @FXML
    private void handle_delete(ActionEvent event) {
        task ta=todo.getSelectionModel().getSelectedItem();
        id=ta.getId();
        task.delete(id);
        refresh();
    }

    @FXML
    private void handle_move(ActionEvent event) {
        task t=new task();
        id=todo.getSelectionModel().getSelectedItem().getId();
        task.move(id);
        refresh();
    }

    @FXML
    private void handle_details(ActionEvent event) {
        
         task ta=todo.getSelectionModel().getSelectedItem();
         id=ta.getId();
         String i=Integer.toString(id);
         System.out.println("id el 1: "+id);
         hello1();
        FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("details.fxml"));
            
               
        try {
             Parent root = loader.load();
             DetailsController nicc = loader.getController();
             nicc.setid(id);
             nicc.settask(liste_details);
            // hethahowa.getScene().setRoot(root);
             Stage s=new Stage();
             s.setScene(new Scene(root));
             s.show();
        } catch (IOException ex) {
               System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handle_movedone(ActionEvent event) {
        task t=new task();
        id=doing.getSelectionModel().getSelectedItem().getId();
        task.move_to_done(id);
        refresh();
    }

    @FXML
    private void handle_editdoing(ActionEvent event) {
        task ta=doing.getSelectionModel().getSelectedItem();
        id=ta.getId();
        hello_doing();
        FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("menu_edit.fxml"));
            
               
        try {
             Parent root = loader.load();
             Menu_editController nicc = loader.getController();
             nicc.setid(id);
             nicc.settitle(title_edit);
             nicc.setdescription(description_edit);
             nicc.setetat(eta);
             nicc.setprio(prio);
            // hethahowa.getScene().setRoot(root);
             Stage s=new Stage();
             s.setScene(new Scene(root));
             s.show();
        } catch (IOException ex) {
               System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handle_deletedoing(ActionEvent event) {
        task ta=doing.getSelectionModel().getSelectedItem();
        id=ta.getId();
        task.delete(id);
        refresh();
    }

    @FXML
    private void handle_showdoing(ActionEvent event) {
        task ta=doing.getSelectionModel().getSelectedItem();
         id=ta.getId();
         System.out.println("id el 1: "+id);
         hello_doing();
        FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("details.fxml"));
            
               
        try {
             Parent root = loader.load();
             DetailsController nicc = loader.getController();
             nicc.setid(id);
            // hethahowa.getScene().setRoot(root);
             Stage s=new Stage();
             s.setScene(new Scene(root));
             s.show();
        } catch (IOException ex) {
               System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handle_detailsdone(ActionEvent event) {
        task ta=done.getSelectionModel().getSelectedItem();
         id=ta.getId();
         System.out.println("id el 1: "+id);
         hello_done();
        FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("details.fxml"));
            
               
        try {
             Parent root = loader.load();
             DetailsController nicc = loader.getController();
             nicc.setid(id);
            // hethahowa.getScene().setRoot(root);
             Stage s=new Stage();
             s.setScene(new Scene(root));
             s.show();
        } catch (IOException ex) {
               System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void handle_deletedone(ActionEvent event) {
        task ta=done.getSelectionModel().getSelectedItem();
        id=ta.getId();
        task.delete(id);
        refresh();
    }
    @FXML
    private void handle_archive_table(ActionEvent event) {
        task ta=done.getSelectionModel().getSelectedItem();
        id=ta.getId();
        task.archiver(id);
        refresh();
    }
   /* @FXML
    private void fk() {
        todo.setVisible(false);
        doing.setVisible(false);
        done.setVisible(false);
        AnchorPane todo_anchor=new AnchorPane();
        
        listtodo=task.update_todo();
        nodet=nodes();
        JFXNodesList nodeList=new JFXNodesList();
        for (int i = 0; i < listtodo.size(); i++) 
        {
        AnchorPane items=new AnchorPane();
        Label title=new Label("title: "+listtodo.get(i).getTitle());
        Label priority=new Label("priority: "+listtodo.get(i).getPriority());
        Label date=new Label("date: "+listtodo.get(i).getCreated());
        nodeList.addAnimatedNode(title);
        nodeList.addAnimatedNode(priority);
        nodeList.addAnimatedNode(date);  
        items.getChildren().add(nodeList);
        
        }
        parent.getChildren().add(todo_anchor);
        }
    public List<String> nodes()
    {
        for (int i = 0; i < listtodo.size(); i++) 
        {
        String name = "nodelist"+i ;
        nodet.add(name);
        }
        return nodet;
    }*/

    @FXML
    private void add_open(MouseEvent event) {
    }

    @FXML
    private void add_open1(ActionEvent event) {
        try {
            FXMLLoader fxml= new FXMLLoader(getClass().getResource("add_task.fxml"));
            Parent root= (Parent) fxml.load();
            Stage s=new Stage();
            s.setTitle("add new task");
            s.setScene(new Scene(root));
            s.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handlesearch(MouseEvent event) {
       title1.setCellValueFactory(new PropertyValueFactory<>("title"));     
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        List<task> tasket=new ArrayList<>();
         tasket= task.afficher_todo();
            ObservableList<task> dataList= FXCollections.observableArrayList(tasket);
            todo.setItems(dataList);
            FilteredList<task> filteredData = new FilteredList<>(dataList, b -> true);
		hethahowa.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(taskn -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				if (taskn.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true;
				}
				     else  
                        {
                            return false;
                            
                        }
			});
		});
                SortedList<task> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(todo.comparatorProperty());
		todo.setItems(sortedData);
    }

   

    @FXML
    private void btn_archive(ActionEvent event) {
         FXMLLoader loader = new FXMLLoader
                        (getClass()
                         .getResource("archive.fxml"));
            
               
        try {
             Parent root = loader.load();
             ArchiveController nicc = loader.getController();
            // hethahowa.getScene().setRoot(root);
             Stage s=new Stage();
             s.setScene(new Scene(root));
             s.show();
        } catch (IOException ex) {
               System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void btn_stat(ActionEvent event) {
        Stage s= new Stage();
        pdz.start(s);
    }
        
    }



