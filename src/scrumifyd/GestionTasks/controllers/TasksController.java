/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;

import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionProjets.controllers.ProjectsController;
import scrumifyd.GestionTasks.models.task;
import scrumifyd.GestionTasks.services.task_services;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class TasksController implements Initializable {

    @FXML
    private StackPane contentPane;
    @FXML
    private VBox pnl_done;
    @FXML
    private VBox pnl_doing;
    @FXML
    private VBox pnl_todo;

    List<task> tasks;
    List<task> tasksd;
    List<task> taskso;
    
    task_services task = new task_services();
    @FXML
    private FontAwesomeIconView addTodoButton;
    @FXML
    private FontAwesomeIconView addDoingButton;
    @FXML
    private FontAwesomeIconView addDoneButton;
    @FXML
    private MenuItem viewStatistics;
    @FXML
    private MenuItem viewArchive;
    @FXML
    private JFXTextField searchBar;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        refreshTodo();
        refreshDoing();
        refreshDone();
    }

    public void refreshTodo() {
        try {
            pnl_todo.getChildren().clear();

            tasks = task.afficher_todo();

            if (!tasks.isEmpty()) {
                Node nodes[] = new Node[tasks.size() + 1];
                tasks.forEach((task t) -> {
                    try {
                        int i = tasks.indexOf(t);
                        i++;

                        FXMLLoader loader = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/taskItem.fxml"));
                        int dayy = t.getCreated().getDayOfMonth();
                        int monthh = t.getCreated().getMonthValue();
                        int yearr = t.getCreated().getYear();
                        int dayyd = t.getFinished().getDayOfMonth();
                        int monthhd = t.getFinished().getMonthValue();
                        int yearrd = t.getFinished().getYear();
                        nodes[i] = loader.load();

                        ItemTaskController item = loader.getController();
                        item.setName(t.getTitle());
                        item.setCreated_day(dayy);
                        item.setCreated_month(monthh);
                        item.setCreated_year(yearr);
                        item.setDeadline_day(dayyd);
                        item.setDeadline_month(monthhd);
                        item.setDeadline_year(yearrd);
                        item.setid(t.getId());

                        item.action1.setText("Move to doing");
                        item.action2.setText("Move to done");
                        item.action1.setOnAction((event) -> {
                            item.action1(event);
                            refreshDoing();
                            refreshDone();
                            refreshTodo();
                        });
                        item.action2.setOnAction((event) -> {
                            item.action2(event);
                            refreshDoing();
                            refreshDone();
                            refreshTodo();
                        });
                        EventHandler<MouseEvent> editHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.EditButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/EditT.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    EditTController sp = loader2.getController();

                                    sp.setTask(t);
                                    sp.setid(t.getId());
//                                    sp.setEtat(t.getEtat());
//                                    sp.setPriority(t.getPriority());
                                    sp.settitle(t.getTitle());
                                    sp.setdescription(t.getDescription());
                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                        EventHandler<MouseEvent> archiveHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.ArchiveButton) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive task", ButtonType.YES, ButtonType.CANCEL);
                                alert.setTitle("Archive task");
                                alert.setHeaderText("Are you sure to archive this task ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.YES) {
                                    task_services pr = new task_services();

                                    pr.archiver(t.getId());

                                    refreshTodo();
                                    refreshDoing();
                                    refreshDone();
                                } else {
                                    System.out.println("canceled");
                                    refreshTodo();
                                    refreshDoing();
                                    refreshDone();
                                }
                            }
                        };
                        EventHandler<MouseEvent> showDetailsHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.showSprintsButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/details.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    DetailsController sp = loader2.getController();

                                    sp.settask(t);

                                    sp.setid(t.getId());
                                    sp.settitle(t.getTitle());
                                    sp.setdescription(t.getDescription());
                                    sp.setEtat(t.getEtat());
                                    sp.setPriority(t.getPriority());
                                    sp.setCreated(t.getCreated());
                                    sp.setUpdated(t.getUpdated());
                                    sp.setFinished(t.getFinished());
                                    sp.setlist(t.getTitle());

                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };

                        pnl_todo.getChildren().addAll(nodes[i]);

                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                        item.showSprintsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetailsHandler);

                    } catch (IOException ex) {
                        Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            }

        } catch (SQLException ex) {
            Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshDoing() {
        try {
            pnl_doing.getChildren().clear();

            tasksd = task.afficher_doing();

            if (!tasksd.isEmpty()) {
                Node nodes[] = new Node[tasksd.size() + 1];
                tasksd.forEach((task t) -> {
                    try {
                        int i = tasksd.indexOf(t);
                        i++;
                        FXMLLoader loader = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/taskItem.fxml"));
                        int dayy = t.getCreated().getDayOfMonth();
                        int monthh = t.getCreated().getMonthValue();
                        int yearr = t.getCreated().getYear();
                        int dayyd = t.getFinished().getDayOfMonth();
                        int monthhd = t.getFinished().getMonthValue();
                        int yearrd = t.getFinished().getYear();
                        nodes[i] = loader.load();

                        ItemTaskController item = loader.getController();
                        item.setName(t.getTitle());
                        item.setCreated_day(dayy);
                        item.setCreated_month(monthh);
                        item.setCreated_year(yearr);
                        item.setDeadline_day(dayyd);
                        item.setDeadline_month(monthhd);
                        item.setDeadline_year(yearrd);
                        item.setid(t.getId());

                        item.action1.setText("Move to to do");
                        item.action2.setText("Move to done");
                        item.action1.setOnAction((event) -> {
                            item.action1(event);
                            refreshDoing();
                            refreshDone();
                            refreshTodo();
                        });
                        item.action2.setOnAction((event) -> {
                            item.action2(event);
                            refreshDoing();
                            refreshDone();
                            refreshTodo();
                        });
                        EventHandler<MouseEvent> editHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.EditButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/EditT.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    EditTController sp = loader2.getController();

                                    sp.setTask(t);
                                    sp.setid(t.getId());
//                                    sp.setEtat(t.getEtat());
//                                    sp.setPriority(t.getPriority());
                                    sp.settitle(t.getTitle());
                                    sp.setdescription(t.getDescription());
                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                        EventHandler<MouseEvent> archiveHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.ArchiveButton) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive task", ButtonType.YES, ButtonType.CANCEL);
                                alert.setTitle("Archive task");
                                alert.setHeaderText("Are you sure to archive this task ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.YES) {
                                    task_services pr = new task_services();

                                    pr.archiver(t.getId());
                                    refreshTodo();
                                    refreshDoing();
                                    refreshDone();
                                } else {
                                    System.out.println("canceled");
                                    refreshTodo();
                                    refreshDoing();
                                    refreshDone();
                                }
                            }
                        };
                        EventHandler<MouseEvent> showDetailsHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.showSprintsButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/details.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    DetailsController sp = loader2.getController();

                                    sp.settask(t);

                                    sp.setid(t.getId());
                                    sp.settitle(t.getTitle());
                                    sp.setdescription(t.getDescription());
                                    sp.setEtat(t.getEtat());
                                    sp.setPriority(t.getPriority());
                                    sp.setCreated(t.getCreated());
                                    sp.setUpdated(t.getUpdated());
                                    sp.setFinished(t.getFinished());
                                    sp.setlist(t.getTitle());

                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };

                        pnl_doing.getChildren().addAll(nodes[i]);

                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                        item.showSprintsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetailsHandler);

                    } catch (IOException ex) {
                        Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            }

        } catch (SQLException ex) {
            Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refreshDone() {
        try {
            pnl_done.getChildren().clear();

            taskso = task.afficher_done();

            if (!taskso.isEmpty()) {
                Node nodes[] = new Node[taskso.size() + 1];
                taskso.forEach((task t) -> {
                    try {
                        int i = taskso.indexOf(t);
                        i++;
                        FXMLLoader loader = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/taskItem.fxml"));
                        int dayy = t.getCreated().getDayOfMonth();
                        int monthh = t.getCreated().getMonthValue();
                        int yearr = t.getCreated().getYear();
                        int dayyd = t.getFinished().getDayOfMonth();
                        int monthhd = t.getFinished().getMonthValue();
                        int yearrd = t.getFinished().getYear();
                        nodes[i] = loader.load();

                        ItemTaskController item = loader.getController();
                        item.setName(t.getTitle());

                        item.setCreated_day(dayy);
                        item.setCreated_month(monthh);
                        item.setCreated_year(yearr);
                        item.setDeadline_day(dayyd);
                        item.setDeadline_month(monthhd);
                        item.setDeadline_year(yearrd);
                        item.setid(t.getId());
                        item.action1.setText("Move to to do");
                        item.action2.setText("Move to doing");
                        item.action1.setOnAction((event) -> {
                            item.action1(event);
                            refreshDoing();
                            refreshDone();
                            refreshTodo();
                        });
                        item.action2.setOnAction((event) -> {
                            item.action2(event);
                            refreshDoing();
                            refreshDone();
                            refreshTodo();
                        });
                        EventHandler<MouseEvent> editHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.EditButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/EditT.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    EditTController sp = loader2.getController();

                                    sp.setTask(t);
                                    sp.setid(t.getId());
//                                    sp.setEtat(t.getEtat());
//                                    sp.setPriority(t.getPriority());
                                    sp.settitle(t.getTitle());
                                    sp.setdescription(t.getDescription());
                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };
                        EventHandler<MouseEvent> archiveHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.ArchiveButton) {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Archive task", ButtonType.YES, ButtonType.CANCEL);
                                alert.setTitle("Archive task");
                                alert.setHeaderText("Are you sure to archive this task ?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.YES) {
                                    task_services pr = new task_services();

                                    pr.archiver(t.getId());

                                    refreshTodo();
                                    refreshDoing();
                                    refreshDone();
                                } else {
                                    System.out.println("canceled");
                                    refreshTodo();
                                    refreshDoing();
                                    refreshDone();
                                }
                            }
                        };
                        EventHandler<MouseEvent> showDetailsHandler = (MouseEvent e) -> {
                            if (e.getSource() == item.showSprintsButton) {
                                try {
                                    FXMLLoader loader2 = new FXMLLoader(TasksController.this.getClass().getResource("/scrumifyd/GestionTasks/views/details.fxml"));

                                    contentPane.getChildren().clear();
                                    Parent root = (Parent) loader2.load();
                                    DetailsController sp = loader2.getController();

                                    sp.settask(t);

                                    sp.setid(t.getId());
                                    sp.settitle(t.getTitle());
                                    sp.setdescription(t.getDescription());
                                    sp.setEtat(t.getEtat());
                                    sp.setPriority(t.getPriority());
                                    sp.setCreated(t.getCreated());
                                    sp.setUpdated(t.getUpdated());
                                    sp.setFinished(t.getFinished());
                                    sp.setlist(t.getTitle());

                                    contentPane.getChildren().add(root);
                                } catch (IOException ex) {
                                    Logger.getLogger(ProjectsController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        };

                        pnl_done.getChildren().addAll(nodes[i]);

                        item.EditButton.addEventHandler(MouseEvent.MOUSE_CLICKED, editHandler);
                        item.ArchiveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, archiveHandler);
                        item.showSprintsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showDetailsHandler);

                    } catch (IOException ex) {
                        Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                });
            }

        } catch (SQLException ex) {
            Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadUI(String ui) {
        contentPane.getChildren().clear();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scrumifyd/GestionTasks/views/" + ui + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
        contentPane.getChildren().add(root);
    }

    @FXML
    private void addTodoButton(MouseEvent event) {
        try {
            contentPane.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionTasks/views/add_task.fxml"));
            Parent root = (Parent) loader.load();
            Add_taskController sp = loader.getController();
            sp.setStatus("Todo");

            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void addDoingButton(MouseEvent event) {
        try {
            contentPane.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionTasks/views/add_task.fxml"));
            Parent root = (Parent) loader.load();
            Add_taskController sp = loader.getController();
            sp.setStatus("doing");

            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void addDoneButton(MouseEvent event) {
        try {
            contentPane.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/scrumifyd/GestionTasks/views/add_task.fxml"));
            Parent root = (Parent) loader.load();
            Add_taskController sp = loader.getController();
            sp.setStatus("done");

            contentPane.getChildren().add(root);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setref(int i) {
        i = 1;
        if (i == 1) {
            refreshTodo();
            refreshDoing();
            refreshDone();
        }
    }

//    public void hello_todo(){
//        liste_recherche=task.recherche_todo(id);
//        for(int i=0;i<liste_recherche.size();i++){
//            title_edit=liste_recherche.get(i).getTitle();
//            description_edit=liste_recherche.get(i).getDescription();
//            prio=liste_recherche.get(i).getPriority();
//            eta=liste_recherche.get(i).getEtat();
//        }
//    }
//    public void hello_doing(){
//        liste_recherche=task.recherche_doing(id);
//        for(int i=0;i<liste_recherche.size();i++){
//            title_edit=liste_recherche.get(i).getTitle();
//            description_edit=liste_recherche.get(i).getDescription();
//            prio=liste_recherche.get(i).getPriority();
//            eta=liste_recherche.get(i).getEtat();
//        }
//    }
//    public void hello_done(){
//        liste_recherche=task.recherche_done(id);
//        for(int i=0;i<liste_recherche.size();i++){
//            title_edit=liste_recherche.get(i).getTitle();
//            description_edit=liste_recherche.get(i).getDescription();
//            prio=liste_recherche.get(i).getPriority();
//            eta=liste_recherche.get(i).getEtat();
//        }
//    }
//    public void hello1(){
//        liste_details=task.update_details(id);
//        for(int i=0;i<liste_details.size();i++){
//            id1=id;
//            title_details=liste_details.get(i).getTitle();
//            desc_details=liste_details.get(i).getDescription();
//            priority=liste_details.get(i).getPriority();
//            created=liste_details.get(i).getCreated();
//            updated=liste_details.get(i).getUpdated();
//            finished=liste_details.get(i).getFinished();
//        }
//    }
//
//    @FXML
//    private void handle_delete(ActionEvent event) {
//        task ta=todo.getSelectionModel().getSelectedItem();
//        id=ta.getId();
//        task.delete(id);
//        refresh();
//    }
//
//    @FXML
//    private void handle_deletedoing(ActionEvent event) {
//        task ta=doing.getSelectionModel().getSelectedItem();
//        id=ta.getId();
//        task.delete(id);
//        refresh();
//    }
//
//    @FXML
//    private void handle_deletedone(ActionEvent event) {
//        task ta=done.getSelectionModel().getSelectedItem();
//        id=ta.getId();
//        task.delete(id);
//        refresh();
//    }
//   /* @FXML
//    private void fk() {
//        todo.setVisible(false);
//        doing.setVisible(false);
//        done.setVisible(false);
//        AnchorPane todo_anchor=new AnchorPane();
//        
//        listtodo=task.update_todo();
//        nodet=nodes();
//        JFXNodesList nodeList=new JFXNodesList();
//        for (int i = 0; i < listtodo.size(); i++) 
//        {
//        AnchorPane items=new AnchorPane();
//        Label title=new Label("title: "+listtodo.get(i).getTitle());
//        Label priority=new Label("priority: "+listtodo.get(i).getPriority());
//        Label date=new Label("date: "+listtodo.get(i).getCreated());
//        nodeList.addAnimatedNode(title);
//        nodeList.addAnimatedNode(priority);
//        nodeList.addAnimatedNode(date);  
//        items.getChildren().add(nodeList);
//        
//        }
//        parent.getChildren().add(todo_anchor);
//        }
//    public List<String> nodes()
//    {
//        for (int i = 0; i < listtodo.size(); i++) 
//        {
//        String name = "nodelist"+i ;
//        nodet.add(name);
//        }
//        return nodet;
//    }*/
//
//    @FXML
//    private void add_open(MouseEvent event) {
//    }
//
//    @FXML
//    private void handlesearch(MouseEvent event) {
//   
//        try {
//            List<task> tasket=new ArrayList<>();
//            tasket= task.afficher_todo();
//            ObservableList<task> dataList= FXCollections.observableArrayList(tasket);
//            todo.setItems(dataList);
//            FilteredList<task> filteredData = new FilteredList<>(dataList, b -> true);
//            hethahowa.textProperty().addListener((observable, oldValue, newValue) -> {
//                filteredData.setPredicate(taskn -> {
//                    if (newValue == null || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String lowerCaseFilter = newValue.toLowerCase();
//                    if (taskn.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
//                        return true;
//                    }
//                    else
//                    {
//                        return false;
//                        
//                    }
//                });
//            });
//            SortedList<task> sortedData = new SortedList<>(filteredData);
//            sortedData.comparatorProperty().bind(todo.comparatorProperty());
//            todo.setItems(sortedData);
//        } catch (SQLException ex) {
//            Logger.getLogger(TasksController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    @FXML
    private void viewStatistics(ActionEvent event) {
        loadUI("statistics");

    }

    @FXML
    private void viewStatistics(Event event) {
        loadUI("statistics");

    }

    @FXML
    private void viewArchive(ActionEvent event) {
        loadUI("archive");
    }

    @FXML
    private void viewArchive(Event event) {
        loadUI("archive");

    }

   
}
