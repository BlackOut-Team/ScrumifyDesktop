/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.controllers;


import scrumifyd.util.MyDbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.scene.Scene; 
import javafx.scene.chart.PieChart; 
import javafx.stage.Stage; 
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList;
   
public class pie_chart_1  { 
    
    Connection conn = MyDbConnection.getInstance().getConnexion();
   private ObservableList data_pie;
   
    // launch the application 
   
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
            Logger.getLogger(pie_chart_1.class.getName()).log(Level.SEVERE, null, ex);
        }
         
          }
 
      
    public void start(Stage stage) 
    { 
         
        // set title for the stage 
        //PIE CHART
        PieChart pieChart = new PieChart();
        buildData();
        pieChart.getData().addAll(data_pie);

        //Main Scene
        Scene scene = new Scene(pieChart);        

        stage.setScene(scene);
        stage.show();
    } 
   
   
    // Main Method 
    public static void main(String args[]) 
    { 
           
        // launch the application 
        launch(args); 
    } 
}
