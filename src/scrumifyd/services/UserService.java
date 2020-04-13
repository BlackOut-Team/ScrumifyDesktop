/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.services;
import scrumifyd.models.User;
import scrumifyd.util.MyDbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gogo-
 */
public class UserService {

   Connection connexion;
   

    public UserService() {
       connexion=MyDbConnection.getInstance().getConnexion();
    }

    public void ajouterPersonne(User p) throws SQLException {
        String req = "INSERT INTO `user` (`username`, `email`) VALUES ( '"
                + p.getNom() + "', '" + p.getPrenom() + "') ";
        Statement stm = connexion.createStatement();
        stm.executeUpdate(req);
    }

    public void ajouterPersonne2(User p) throws SQLException {
        String req = "INSERT INTO `user` (`username`, `email`) VALUES ( ?, ?) ";
        PreparedStatement pstm = connexion.prepareStatement(req);
        pstm.setString(1, p.getNom());
        pstm.setString(2, p.getPrenom());
        pstm.executeUpdate();
    }

    public List<User> getAllPersonnes() throws SQLException {
       List<User> personnes = new ArrayList<>();
        
        String req = "select * from user";
        Statement stm = connexion.createStatement();
        ResultSet result =  stm.executeQuery(req);
        
        while(result.next()){
            User p = new User(result.getInt(1), result.getString("username"), result.getString("email"));
            personnes.add(p);
        }
        
        return personnes;
    }
    
    

}
