/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTeams.controllers;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import scrumifyd.GestionProjets.controllers.DashboardController;
import scrumifyd.GestionTeams.models.Team;
import scrumifyd.GestionTeams.services.TeamService;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import scrumifyd.util.MyDbConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
/**
 * FXML Controller class
 *
 * @author Iheb
 */
public class teamsController implements Initializable {
//Connection cnx = MyDbConnection.getConnexion();
   Connection  connexion = MyDbConnection.getInstance().getConnexion();
    @FXML
    private AnchorPane contentPane;
    
    @FXML
    private TableView<Team>  TeamTable;
    
    private TableColumn<Team,Integer> teamId;
  
    ObservableList<Team> oblist = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Team, String> teamName;
    @FXML
    private TableColumn<?, ?> created;
    @FXML
    private TableColumn<?, ?> updated;
    @FXML
    private JFXButton AffButton;
    @FXML
    private TextField updateTeamName;
    @FXML
    private Button updateOrderButton;
    @FXML
    private JFXButton ArchButton;

    @FXML
    private JFXTextField lal;
    
    
    public void populate() {
        String sql = "select * from Team where ind=0";
        Connection con = MyDbConnection.getInstance().getConnexion();
        oblist.clear();
        try {
            ResultSet rs = con.createStatement().executeQuery(sql);
            while (rs.next()) {
                 Date create = rs.getDate("created");
                Date update = rs.getDate("updated");
                LocalDate datec = Instant.ofEpochMilli(create.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(update.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                
                oblist.add(new Team(rs.getInt("id"), rs.getString("Name"), rs.getInt("etat"),rs.getInt("ind"),datec,dated));
            }
        } catch (SQLException ex) {
            System.out.println("scrumifyd.GestionTeams.controllers.teamsController.populate()");
        }
      //  EmployerTable.setItems(oblist);
    }

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        
        TeamTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        List<Team> teamList = new ArrayList<>();
        TeamService teamService = TeamService.getInstance();
        teamList = TeamService.getAllTeams();
         oblist.clear();
        oblist.addAll(teamList);
       
           if (!teamList.isEmpty()) {
                populate();
       
        teamName.setCellValueFactory(new PropertyValueFactory<>("name"));
         created.setCellValueFactory(new PropertyValueFactory<>("created"));
          updated.setCellValueFactory(new PropertyValueFactory<>("updated"));
       
           TeamTable.setItems(oblist);
           }else
           {System.out.println("scrumifyd.GestionTeams.controllers.teamsController.initialize()");}
       
    }
        
    public void refreshTableData() {
        List<Team> tList = new ArrayList<>();
       TeamService orderService = TeamService.getInstance();
    tList = TeamService.getAllTeams();
                   

        oblist.clear();
        oblist.addAll(tList);
       TeamTable.setItems(oblist);
      
    }
    
    @FXML
    private void openAdd(MouseEvent event) {
        loadUI("GestionTeams", "Addteam");

    }
    
     
   
    
      public void loadUI (String module ,String ui){
         contentPane.getChildren().clear();
         Parent root=null;
       try{
           root = FXMLLoader.load(getClass().getResource("/scrumifyd/"+module+"/views/"+ui+".fxml"));
       } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        contentPane.getChildren().add(root);
   }

    
 //********************* U **************************//
   
    @FXML
    public void clickOneTeamAction() {
         TeamService teamService = TeamService.getInstance();
        Team t = (Team)TeamTable.getSelectionModel().getSelectedItem();
        
        if(t != null)
        {
            if (!teamService.isScrumMatser(t.getId())) {
                this.AffButton.disableProperty().setValue(true);
                this.updateOrderButton.disableProperty().setValue(true);
                this.ArchButton.disableProperty().setValue(true);
            } else {
                 this.AffButton.disableProperty().setValue(false);
                this.updateOrderButton.disableProperty().setValue(false);
                 this.ArchButton.disableProperty().setValue(false);
                
            }
            fillUpdateForm(t);
        }
    }
    
    public void fillUpdateForm(Team t)
    {
      updateTeamName.setText(t.getName());
    
  
    }
    @FXML
    public void updateTeamAction() {
        Team selection = TeamTable.getSelectionModel().getSelectedItem();
    
        if (selection != null)
        {    
            Team t = new Team();
            t.setId(selection.getId());
            String name = updateTeamName.getText();
                    
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir modifier le team: "+t.getId()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            if(a.getResult()==ButtonType.YES){
                if(name!=null )
                {
                    t.setName(name);
                   
                    TeamService teamService =TeamService.getInstance();
                    teamService.updateTeam(t);
                    a.close();
                }
                else
                {
                    Alert inputAlert = new Alert(Alert.AlertType.ERROR,"Le format de données saisi est incorrect.",ButtonType.OK);
                }
            }else{
            a.close();
            }
        }
        else
        {   
            updateTeamName.setText("");
            
            Alert a=new Alert(Alert.AlertType.WARNING,"Aucune séléction !",ButtonType.CLOSE); 
            a.showAndWait();
        }
        refreshTableData();
    }
    @FXML
    public void ArchiverTeamAction() {
        Team selection = TeamTable.getSelectionModel().getSelectedItem();
    
        if (selection != null)
        {    
            Team t = new Team();
            t.setId(selection.getId());
           
                    
            Alert a=new Alert(Alert.AlertType.CONFIRMATION,"Êtes-vous sûr(e) de vouloir archiver le team: "+t.getId()+" de la base de données ?",ButtonType.YES,ButtonType.NO);
            a.showAndWait();
            if(a.getResult()==ButtonType.YES){
               
                   
                   
                    TeamService teamService =TeamService.getInstance();
                    teamService.archiveTeam(t);
                    a.close();
                    JOptionPane.showMessageDialog(
                    null, " archivage fait avec succés ");
           
                
             
            }else{
            a.close();
            }
        }
        else
        {   
         
            
            Alert a=new Alert(Alert.AlertType.WARNING,"Aucune séléction !",ButtonType.CLOSE); 
            a.showAndWait();
        }
        refreshTableData();
    }

    

    @FXML
    private void selectTeamAndOpenAddModal(MouseEvent event) {
        TeamService teamService = TeamService.getInstance();
        Team t = (Team) TeamTable.getSelectionModel().getSelectedItem();
         if (t != null) {
             System.out.print(t.toString());
            teamService.selectTeam(t);
            loadUI("GestionTeams", "affectUser");
         }
    }
    
    @FXML
        private void Rechercher() {

        lal.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                TeamService rs = new TeamService();
                ObservableList obeListe = FXCollections.observableList(rs.rechercherNomEt(newValue));
                TeamTable.setItems(obeListe);
            } catch (SQLException ex) {
                Logger.getLogger(teamsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
       

        });
          refreshTableData();
    }

}
