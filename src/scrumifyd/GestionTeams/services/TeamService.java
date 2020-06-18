/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTeams.services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import scrumifyd.GestionTeams.models.Team;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.mail.*;  
import javax.mail.internet.*;  
import javax.swing.JOptionPane;
import scrumifyd.GestionTeams.models.Affiche;
import scrumifyd.util.MyDbConnection;

/**
 *
 * @author Iheb
 */
public  class TeamService {
        static Connection connexion;
    private Statement stmt = null;
  private static TeamService orderServiceInstance;
  private Team sellectedTeam = new Team();
    public TeamService() {
                connexion = MyDbConnection.getInstance().getConnexion();

    }
     public static TeamService getInstance() {   //Singleton Design Pattern
        if (orderServiceInstance==null)
        {
            orderServiceInstance = new TeamService();
        }
        return orderServiceInstance ;  
//        return new ShopService();
    }

    public static List<Team> getAllTeams()  {
       List<Team> Teams = new ArrayList<>();
        try {

            String req = "SELECT * FROM `team`  where ind=0";
            Statement stm = connexion.createStatement();
            ResultSet result = stm.executeQuery(req);

            while (result.next()) {
                Date create = result.getDate("created");
                Date update = result.getDate("updated");
                LocalDate datec = Instant.ofEpochMilli(create.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(update.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                Team t = new Team( result.getString("name"), result.getInt("etat"),result.getInt("etat"),datec, dated);
                Teams.add(t);
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeamService.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        return Teams;
        


    }
    

 

    public boolean addTeam(Team t ) throws SQLException {
try {
            boolean isInserted = false;
            PreparedStatement statement = connexion.prepareStatement(
                    "INSERT INTO TEAM (name ,created ,updated ,etat,ind) values (?,?,?,?,?)" ,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, t.getName());
            statement.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
            statement.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
            statement.setInt(4, 1);
            statement.setInt(5, 0);
           isInserted =  statement.executeUpdate() > 0;
            ResultSet tableKeys = statement.getGeneratedKeys();
            if (tableKeys.next()) {
                 int teamId = tableKeys.getInt(1);
                if (addScrumMaster(teamId)) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(TeamService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;   
    }
    
    public boolean archiveTeam(Team team) {
       System.out.println(team);
        String query = "UPDATE TEAM SET ind=1 WHERE id=?";
        try {
            PreparedStatement pst = connexion.prepareStatement(query);
            pst.setInt(1, team.getId());
         //   pst.setInt(1, team.getInd());
          
            pst.executeUpdate();
            System.out.println("archivage successful !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }

  
    public boolean updateTeam(Team team) {
 
        System.out.println(team);
        String query = "UPDATE TEAM SET NAME=? WHERE id=?";
        try {
            PreparedStatement pst = connexion.prepareStatement(query);
            pst.setInt(2, team.getId());
            pst.setString(1, team.getName());
       
            pst.executeUpdate();
            System.out.println("Update successful !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return true;
        }       
        return false;
    }    
      
   
    

   
    public Team selectTeam(Team team) {
        this.sellectedTeam = team;
        return this.sellectedTeam;
    }
    public Team getSelectedTeam() {
        return this.sellectedTeam;
    }
  
    private int isUserExist(String userEmail) {
        int userId  = -1;
        try {
           
            String req = "SELECT * FROM user where email = ?";
            PreparedStatement prpStm = connexion.prepareStatement(req);
             prpStm.setString(1, userEmail);
            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                userId = result.getInt("id");
                return userId;
        }
            System.err.println("result" + result);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return userId;
    }
    
    public int isUserExistInTeam( String usemarEmail) {
        int userId = isUserExist(usemarEmail);
        
        if (userId == -1) {
            
            return 0;
        
        }
        try {
           
            String req = "SELECT * FROM team_user where user_id = ? and team_id = ?";
            PreparedStatement prpStm = connexion.prepareStatement(req);
             prpStm.setInt(1, userId);
              prpStm.setInt(2,this.getSelectedTeam().getId());

            ResultSet result = prpStm.executeQuery();
            if (result.next()) {
                userId = result.getInt("id");
                return userId;
        }
            return -1;
           
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }
    
    public static boolean validateEmailAddress(String emailID) { 
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"; 
        Pattern pattern = Pattern.compile(regex); 
        return pattern.matcher(emailID).matches(); }
    
    
    private boolean addScrumMaster(int teamId) {
        // inject User Service
        // user = getCurrentUser()
        try {
            PreparedStatement statement = connexion.prepareStatement(
                    "INSERT INTO `team_user`(`team_id`, `user_id`, `role`) VALUES (?,? , ?)");
            statement.setInt(1, teamId);
            statement.setInt(2, 3);
            statement.setInt(3, 1);
             return statement.executeUpdate() > 0;
        }  catch (SQLException ex) {
            Logger.getLogger(TeamService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
    public void addMember(String email , int roleId) {
            TeamService teamService = TeamService.getInstance();
                        Team team = new Team();
                        
                        if (teamService.getSelectedTeam() != null) {
            team = teamService.getSelectedTeam();
            
        }
          if (this.validateEmailAddress(email)==true)    
          {
        if (this.isUserExist(email)!= -1) {
            try {
                     PreparedStatement statement = connexion.prepareStatement(
                    "INSERT INTO `team_user`(`team_id`, `user_id` ,`role` ) VALUES (?,?,?)");
            statement.setInt(1, team.getId());
            statement.setInt(2, this.isUserExist(email));
            statement.setInt(3, roleId);
             statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(TeamService.class.getName()).log(Level.SEVERE, null, ex);
        }   
               sendEmailSucc(email);
        }
        else
        {
     sendEmailFaild(email);
            JOptionPane.showMessageDialog(
                    null, " Utilisateur n'existe pas ");
          }
            
    }
          else
          {
               JOptionPane.showMessageDialog(
                    null, " Email invalide verfier ");
          }
    }
    
    
    
    
public ArrayList<Team> rechercherNomEt(String rech) throws SQLException {

        ArrayList<Team> off = new ArrayList<>();
           Team e = null;
        String req = "SELECT `id` ,`name`,`created`,`updated` FROM team where name Like '%"+rech+"%' ";  
         
 PreparedStatement prpStm = connexion.prepareStatement(req);
           
            ResultSet rst = prpStm.executeQuery();

        while (rst.next()) {
                              e = new Team();
               
              
              
                e.setName(rst.getString("name"));
               
              
              
                Date create = rst.getDate("created");
                Date update = rst.getDate("updated");
                LocalDate datec = Instant.ofEpochMilli(create.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dated = Instant.ofEpochMilli(update.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();                

                
              e.setCreated(datec);
              e.setUpdated(dated);
  

            off.add(e);
        }
        return off;
    }     
    
public boolean isScrumMatser(int teamId) {
    try {

            String req = "SELECT * FROM `team_user` WHERE `team_id` = ? and `role` = 1 and `user_id` = 3";
            PreparedStatement stm = connexion.prepareStatement(req);
            stm.setInt(1, teamId);
            ResultSet result = stm.executeQuery();
            if (result.next()) {
            
              return true;
            }
          
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(TeamService.class.getName()).log(Level.SEVERE, null, ex);
        }
    return false;
}

 public boolean isProductOwnerExist(int team_id) {
       
       try {
           String req = "SELECT * FROM team_user where team_id = ? and role = 3";
           PreparedStatement prpStm = connexion.prepareStatement(req);
           prpStm.setInt(1, team_id);
           ResultSet result =  prpStm.executeQuery();
          
          
           return result.next();
     } catch (Exception e) {
           System.out.println("scrumifyd.GestionTeams.services.TeamService.isProductOwnerExist()" + e);
           return false;
     }  
  
 }
 
 public static ArrayList<Affiche> geeamUsers (int teamId){
            ArrayList<Affiche> aff = new ArrayList<Affiche>();
             try {
           String req = "SELECT user.username , user.email , role FROM `team_user` INNER join `user` ON team_user.user_id = user.id WHERE `team_user`.`team_id` = ?";
           PreparedStatement prpStm = connexion.prepareStatement(req);
           prpStm.setInt(1, teamId);
           ResultSet result =  prpStm.executeQuery();
                 while (result.next()) {
                    
                     
                     Affiche f = new Affiche(result.getString("email"), result.getString("username"), getRoleNamme(result.getInt("role")));
                aff.add(f);
                 }
                 return aff;
             } catch (Exception e) {
           System.out.println("scrumifyd.GestionTeams.services.TeamService.isProductOwnerExist()" + e);
           return null;
     }  
             
         }
 
 public static String getRoleNamme(int code) {
     if(code == 1) {
         return "Scrum Master";
     }
     if (code == 2) {
         return "Developer";
     }
     if(code == 3) {
         return "Product Owner";
     }
     return "";
 }
 

    private void sendEmailFaild(String email) {
        
        
       
        //******************/
                
                 try{
        
        String to=email;//change accordingly  destinateur
  final String user="scrumify.application@gmail.com";//change accordingly  eky yebath
  final String password="Blackout2020";//change accordingly  
  //1) get the session object     
 
  Properties properties = System.getProperties();  
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false"); 
  
  Session session = Session.getDefaultInstance(properties,  
   new javax.mail.Authenticator() {  
   protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication(user,password);  
   }  
  });  
     
  //2) compose message     
  try{  
    MimeMessage message = new MimeMessage(session);  
    message.setFrom(new InternetAddress(user));  
    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
    message.setSubject("Affectation au team");  
      
    //3) create MimeBodyPart object and set your message text     
    BodyPart messageBodyPart1 = new MimeBodyPart();  
    messageBodyPart1.setText("Bonjour, \n Vous êtes affacter au team ....... \n mais malheureusement vous êtes pas inscrit dans notre application \n Pour plu de detailles voulez vous contacter notre adminstarteur via email suivant : iheb.rekik@esprit.tn \n cordilement ");
  

    //5) create Multipart object and add MimeBodyPart objects to this object      
    Multipart multipart = new MimeMultipart();  
    multipart.addBodyPart(messageBodyPart1);  
  
  
    //6) set the multiplart object to the message object  
    message.setContent(multipart );  
     
    //7) send message  
    Transport.send(message);  
   
 
   }catch (MessagingException ex) {ex.printStackTrace();}  
               }catch (Exception e) {

          
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }//******************/
    }

    private void sendEmailSucc(String email) {
       //******************/
                
                 try{
        
        String to=email;//change accordingly  destinateur
  final String user="scrumify.application@gmail.com";//change accordingly  eky yebath
  final String password="Blackout2020";//change accordingly  
  //1) get the session object     
 
  Properties properties = System.getProperties();  
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");

        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", "false"); 
  
  Session session = Session.getDefaultInstance(properties,  
   new javax.mail.Authenticator() {  
   protected PasswordAuthentication getPasswordAuthentication() {  
   return new PasswordAuthentication(user,password);  
   }  
  });  
     
  //2) compose message     
  try{  
    MimeMessage message = new MimeMessage(session);  
    message.setFrom(new InternetAddress(user));  
    message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
    message.setSubject("Affectation au team");  
      
    //3) create MimeBodyPart object and set your message text     
    BodyPart messageBodyPart1 = new MimeBodyPart();  
    messageBodyPart1.setText("Bonjour, vous êtes affecter au team ..... ");  
  

    //5) create Multipart object and add MimeBodyPart objects to this object      
    Multipart multipart = new MimeMultipart();  
    multipart.addBodyPart(messageBodyPart1);  
  
  
    //6) set the multiplart object to the message object  
    message.setContent(multipart );  
     
    //7) send message  
    Transport.send(message);  
   
  JOptionPane.showMessageDialog(
                    null, " mail envoyé avec succés ");
   }catch (MessagingException ex) {ex.printStackTrace();}  
               }catch (Exception e) {

          
            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }//******************/
    }
         
 }
 

