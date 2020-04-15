/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.services;
import scrumifyd.GestionTasks.models.member;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import scrumifyd.util.MyDbConnection;
import java.util.logging.Logger;
import javafx.scene.control.CheckBox;
/**
 *
 * @author mahdi
 */
public class members_services {
    int id;
    public int getid(String nom){
        
        try {
                Connection conn = MyDbConnection.getInstance().getConnexion();
                PreparedStatement pt = conn.prepareStatement("select id from members where nom='"+nom+"'");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                        id=rs.getInt("id");
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(members_services.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    public void affecter(int id){
        try {
            Connection conn = MyDbConnection.getInstance().getConnexion();
            String req = "Update tasks set members='"+id+"'";
            PreparedStatement st = conn.prepareStatement(req);
            
            st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(members_services.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<member> update ()
    {        
        List<member> members=new ArrayList<>();
        try {
                Connection conn = MyDbConnection.getInstance().getConnexion();
                PreparedStatement pt = conn.prepareStatement("select * from  person");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    members.add(new member(
                            rs.getInt("id"),rs.getString("name")));
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(members_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return members;
    }
    public List<member> update_members()
    { 
        List<member> m=new ArrayList<>();
        List<member> m2=new ArrayList<>();
        m=update();
        
        for(int i=0;i<m.size();i++){
            int id=m.get(i).getId();
            String nom=m.get(i).getNom();
            CheckBox ch=new CheckBox("");
            m2.add(new member(id,nom,ch));
        }
        return m2;
    }
    public String get_name (int id)
    {        
        String name="";
        try {
                Connection conn = MyDbConnection.getInstance().getConnexion();
                PreparedStatement pt = conn.prepareStatement("select name from person where id='"+id+"'");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    name=rs.getString("name");}
                
        } catch (SQLException ex) {
                Logger.getLogger(members_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return name;
    }
}
