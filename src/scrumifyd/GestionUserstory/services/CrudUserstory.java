/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUserstory.services;

import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scrumifyd.GestionUserstory.services.CrudUserstory;
import scrumifyd.GestionUserstory.models.UserStory;
import scrumifyd.util.MyDbConnection;

/**
 *
 * @author youssef
 */
public class CrudUserstory {
    Connection C=MyDbConnection.getInstance().getConnexion();
    public void ajouterUserStory(UserStory O){
        try {  
            Statement st=C.createStatement();
           String req="insert into userstory (`description`,`priority`,`story_point`,`etat`,`feature_id`,`isDeleted`) values('"+O.getDescription()+"',"+O.getPriority()+","+O.getStory_point()+","+O.getEtat()+","+O.getFeature_id()+","+O.getIsDeleted()+")";
                      st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(CrudUserstory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
  
      public ObservableList<UserStory> afficherUserStory(int id){
              ObservableList<UserStory> features = FXCollections.observableArrayList();
        try {
      
            Statement st=C.createStatement();
            String req="select* from userstory where feature_id ="+id+"";
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                if(rs.getInt(7)==0)
                features.add(new UserStory(rs.getInt(1),rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(2), rs.getInt(7)));   
}
        } catch (SQLException ex) {
            Logger.getLogger(CrudUserstory.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         return features;
    }
      public void modifierUserStory(int id,UserStory o){
        try {
            
            PreparedStatement pt=C.prepareStatement("UPDATE `userstory` SET `description`=?,`priority`=?,`story_point`=?,`etat`=? WHERE id=?");
            pt.setString(1,o.getDescription());
            pt.setInt(2, o.getPriority());
            pt.setInt(3,o.getStory_point());
            pt.setInt(4, o.getEtat());
            pt.setInt(5, id);
     
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CrudUserstory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void supprimerUserStory(int id){
        try {
            
            PreparedStatement pt=C.prepareStatement("UPDATE `userstory` SET `isDeleted`=1 WHERE id=?");

            pt.setInt(1, id);
     
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CrudUserstory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
