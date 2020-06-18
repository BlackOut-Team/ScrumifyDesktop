/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionFeature.services;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import scrumifyd.GestionFeature.models.Features;
import scrumifyd.GestionUserstory.models.UserStory;
import scrumifyd.util.MyDbConnection;

        
/**
 *
 * @author youssef
 */
public class CrudFeatures {
Connection C=MyDbConnection.getInstance().getConnexion();


  public void ajouterFeature(Features O){
        try {  
            Statement st=C.createStatement();
           String req="insert into feature (`id`, `name`, `etat`,`isDeleted`) values("+O.getId()+",'"+O.getName()+"',"+O.getEtat()+","+O.getIsDeleted()+")" ;
                      st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(CrudFeatures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
  
      public ObservableList<Features> afficherFeature(){
              ObservableList<Features> features = FXCollections.observableArrayList();
        try {
      
            Statement st=C.createStatement();
            String req="select* from feature";
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                if(rs.getInt(4)==0)
                features.add(new Features(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));   
}
        } catch (SQLException ex) {
            Logger.getLogger(CrudFeatures.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         return features;
    }
      public void modifierFeature(Features o){
        try {
            
            PreparedStatement pt=C.prepareStatement("UPDATE `feature` SET `name`=?,`etat`=? WHERE id=?");
            pt.setString(1,o.getName());
            pt.setInt(2, o.getEtat());
            pt.setInt(3, o.getId());
     
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CrudFeatures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public void supprimerFeature(int id){
        try {
            
            PreparedStatement pt=C.prepareStatement("UPDATE `feature` SET `isDeleted`=1 WHERE id=?");

            pt.setInt(1, id);
     
            pt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CrudFeatures.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
        
         public List<UserStory> UserStorys(Features F){
                 List<UserStory> list= new ArrayList<UserStory>();
        try {
      
            Statement st=C.createStatement();
            String req="select* from userstory where feature_id="+F.getId()+"";
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                if(rs.getInt(7)==0)
                list.add(new UserStory(rs.getInt(1),rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7)));   
}
        } catch (SQLException ex) {
            Logger.getLogger(CrudFeatures.class.getName()).log(Level.SEVERE, null, ex);
        }
       
         return list;
         }
         
         public ObservableList<Features> afficherPdf(){
             
             ObservableList<Features> features = FXCollections.observableArrayList();
            
                 
                 try {
                     
                     String file_name = "C:\\Users\\Amine\\Desktop/feature.pdf";
                      Document document = new Document();
       
        
            PdfWriter.getInstance(document, new FileOutputStream("feature.pdf"));
            
            document.open();
            
          Statement st=C.createStatement();
            String req="select* from feature";
            ResultSet rs = st.executeQuery(req);
            while(rs.next()){
                
                PdfPTable table = new PdfPTable(3);
                PdfPCell c1 = new PdfPCell(new Phrase("Id"));
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase("Name"));
                table.addCell(c1);
                
                c1 = new PdfPCell(new Phrase("Etat"));
                table.addCell(c1);
                
                Paragraph para = new Paragraph(rs.getInt(1),rs.getString(2));
                document.add(para);
                document.add(para);
                document.add(new Paragraph("test"));

                table.addCell(para);
                
                
                
            
                        }
            
            
            
            document.close();
            
            System.out.println("finished");
      
            
         } 
            
            catch (Exception e) {
            
            System.err.println("e");
        
        }
        
         
       
             return features;
}
         
}
