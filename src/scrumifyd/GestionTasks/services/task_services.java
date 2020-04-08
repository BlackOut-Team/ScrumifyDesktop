/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.services;

import scrumifyd.util.MyDbConnection;
import java.awt.AWTException;
import java.awt.TrayIcon;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import scrumifyd.GestionTasks.models.task;
import doryan.windowsnotificationapi.fr.Notification;
import java.time.LocalDateTime;

/**
 *
 * @author mahdi
 */
public class task_services {
        Connection conn = MyDbConnection.getInstance().getConnexion();
   
    
    public void create(task ta) {
               
        try {
            String req = "INSERT INTO tasks (title,priority,description,finished,members) VALUES ('"+ta.getTitle()+"','"+ta.getPriority()+"','"+ta.getDescription()+"','"+ta.getFinished()+"','"+ta.getMembers()+"')";
            
            PreparedStatement st = conn.prepareStatement(req);
            //st.setString(1, e.getNom());
            //st.setObject(2, e.getDate());
            //st.setObject(3, e.getDuree());
            st.executeUpdate();
            try {
                Notification.sendNotification("new task", "new task created",TrayIcon.MessageType.INFO);
            } catch (AWTException | MalformedURLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
            }
            PreparedStatement pt = conn.prepareStatement("select id from tasks where etat='0' ORDER BY id DESC LIMIT 0, 1");
            ResultSet rs = pt.executeQuery();
            while (rs.next()) {            
                ta.setId(rs.getInt(1));
            }
            
           
        } catch (SQLException ex) {
            Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }  
    public List<task> update_todo ()
    {        
                List<task> task_todo=new ArrayList<>();
        try {
                PreparedStatement pt = conn.prepareStatement("select id,title from tasks where status='todo' and etat='0' order by priority DESC");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_todo.add(new task(
                            rs.getString("title"),rs.getInt("id")));
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_todo;
    }
    public List<task> update_doing ()
    {        
                List<task> task_doing=new ArrayList<>();
        try {
                PreparedStatement pt = conn.prepareStatement("select title,id from tasks where status='doing' and etat='0' order by priority DESC");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_doing.add(new task(rs.getString("title"),rs.getInt("id")));
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_doing;
    }
     public List<task> update_done()
    {        
                List<task> task_done=new ArrayList<>();
        try {
                PreparedStatement pt = conn.prepareStatement("select title,id from tasks where status='done' and etat='0' order by priority DESC");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_done.add(new task(rs.getString("title"),rs.getInt("id")));
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_done;
    }
    public void modifier(String title,int priority,int etat,String description,int id) {
               
        try {
            String req = "Update tasks set title='"+title+"',priority='"+priority+"',etat='"+etat+"',description='"+description+"' where id='"+id+"'";
            
            PreparedStatement st = conn.prepareStatement(req);
            //st.setString(1, e.getNom());
            //st.setObject(2, e.getDate());
            //st.setObject(3, e.getDuree());
            st.executeUpdate();
            try {
                Notification.sendNotification("task", "task modified",TrayIcon.MessageType.INFO);
            } catch (AWTException | MalformedURLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
                    
    }
    public List<task> recherche_todo(int id)
    {        
                List<task> task_todo=new ArrayList<>();
        try {
                PreparedStatement pt = conn.prepareStatement("select priority,etat,title,description from tasks where status='todo' and id='"+id+"' and etat='0'");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_todo.add(new task(
                            rs.getInt("priority"),
                            rs.getInt("etat"),
                            rs.getString("title"),
                            rs.getString("description")));
                }
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_todo;
    }
     public List<task> afficher_todo()
    {        
                List<task> task_todo=new ArrayList<>();
        try {
                PreparedStatement pt = conn.prepareStatement("select * from tasks where status='todo' ");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_todo.add(new task(
                            rs.getInt("priority"),
                            rs.getInt("etat"),
                            rs.getString("title"),
                            rs.getString("description")));
                }
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_todo;
    }
    public void delete(int id) {
        
            try {
                String req = "DELETE FROM `tasks` WHERE id = ? ";
                PreparedStatement st = conn.prepareStatement(req);
                st.setInt(1,id);
                //  st.setString(2, e.getNomAnnonce());

                st.executeUpdate();
                try {
                    Notification.sendNotification("task", "task deleted",TrayIcon.MessageType.INFO);
                } catch (AWTException | MalformedURLException ex) {
                    Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
    
    public void move(int id) {
        
                String status="doing";    
        try {
                String req = "UPDATE tasks set status='"+status+"' where id ='"+id+"'";
                PreparedStatement st = conn.prepareStatement(req);
                st.executeUpdate();
                    try {
                        Notification.sendNotification("task", "task moved to doing",TrayIcon.MessageType.INFO);
                    } catch (AWTException | MalformedURLException ex) {
                        Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
                    }
            } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    public void move_to_done(int id) {
        
                String status="done";    
        try {
                String req = "UPDATE tasks set status='"+status+"' where id ='"+id+"'";
                PreparedStatement st = conn.prepareStatement(req);
                st.executeUpdate();
                    try {
                        Notification.sendNotification("task", "task moved to done",TrayIcon.MessageType.INFO);
                    } catch (AWTException | MalformedURLException ex) {
                        Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
                    }
            } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    public List<task> update_details (int id)
    {        
                List<task> task_details=new ArrayList<>();
        try {
            LocalDateTime now = LocalDateTime.now();  
                PreparedStatement pt = conn.prepareStatement("select * from tasks where id='"+id+"'");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_details.add(new task(
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getDate("created"),
                            rs.getDate("updated"),
                            rs.getString("finished"),
                            rs.getInt("priority"),
                            rs.getString("status")
                    ));
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_details;
    }
    public List<task> recherche_doing(int id)
    {        
                List<task> task_todo=new ArrayList<>();
        try {
                PreparedStatement pt = conn.prepareStatement("select priority,etat,title,description from tasks where status='doing' and id='"+id+"' and etat='0'");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_todo.add(new task(
                            rs.getInt("priority"),
                            rs.getInt("etat"),
                            rs.getString("title"),
                            rs.getString("description")));
                }
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_todo;
    }
    public List<task> recherche_done(int id)
    {        
                List<task> task_todo=new ArrayList<>();
        try {
                PreparedStatement pt = conn.prepareStatement("select priority,etat,title,description from tasks where status='done' and id='"+id+"' and etat='0'");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_todo.add(new task(
                            rs.getInt("priority"),
                            rs.getInt("etat"),
                            rs.getString("title"),
                            rs.getString("description")));
                }
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_todo;
    }
    public void archiver(int id) {
           int i=1;
        try {
                String req = "UPDATE tasks set etat='"+i+"' where id ='"+id+"'";
                PreparedStatement st = conn.prepareStatement(req);
                st.executeUpdate();
               try {
                   Notification.sendNotification("task", "task is in the archive",TrayIcon.MessageType.INFO);
               } catch (AWTException | MalformedURLException ex) {
                   Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
               }
            } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    public List<task> archive_list ()
    {        
                List<task> task_details=new ArrayList<>();
        try {
            LocalDateTime now = LocalDateTime.now();  
                PreparedStatement pt = conn.prepareStatement("select * from tasks where etat='"+1+"' order by priority DESC");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                    task_details.add(new task(
                            rs.getString("title"),
                            rs.getInt("id"),
                            rs.getString("description"),
                            rs.getDate("created"),
                            rs.getDate("updated"),
                            rs.getString("finished"),
                            rs.getInt("priority"),
                            rs.getString("status")
                    ));
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return task_details;
    }
    public String get_members (String title)
    {        
       String member="";
        try {
            LocalDateTime now = LocalDateTime.now();  
                PreparedStatement pt = conn.prepareStatement("select members from tasks where Title='"+title+"'");
                ResultSet rs = pt.executeQuery();
                while (rs.next()) {
                            member=rs.getString("members");
                    
                }
                
        } catch (SQLException ex) {
                Logger.getLogger(task_services.class.getName()).log(Level.SEVERE, null, ex);
        }
            return member;
    }
}

