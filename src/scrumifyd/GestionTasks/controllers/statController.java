/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import scrumifyd.util.MyDbConnection;

/**
 * FXML Controller class
 *
 * @author Amira Doghri
 */
public class statController implements Initializable {

    @FXML
    private StackPane contentPane;
      Connection conn = MyDbConnection.getInstance().getConnexion();
    private ObservableList data_pie;
    @FXML
    private PieChart pieChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
                contentPane.getChildren().clear();

      
        buildData();
        pieChart = new PieChart(data_pie);

        //pieChart.getData().addAll(data_pie);
        contentPane.getChildren().add(pieChart);
        // TODO
    }    
    public void buildData(){
        try {
            data_pie = FXCollections.observableArrayList();
            
            PreparedStatement pt = conn.prepareStatement("select DISTINCT status,COUNT(status) as nbr from tasks group by status");
            ResultSet rs = pt.executeQuery();
            while(rs.next()){
                //adding data on piechart data
                data_pie.add(new PieChart.Data(rs.getString("status"),rs.getInt(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(statController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
          }
 
      
}
