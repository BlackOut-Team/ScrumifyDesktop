/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.models;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.time.LocalDate;
import java.util.Date;
import javax.print.attribute.standard.DateTimeAtCreation;

/**
 *
 * @author Amira Doghri
 */
public class Project {
    private int id ; 
    private String Name ; 
    private String Description ; 
    private LocalDate created ;
    private LocalDate duedate ; 
    private int etat; 
    private int team_id ; 
    private int owner_id ; 
    private int master_id;

    public Project() {
    }

    public Project(String Name, String Description, LocalDate created, LocalDate duedate, int etat) {
        this.Name = Name;
        this.Description = Description;
        this.created = created;
        this.duedate = duedate;
        this.etat = etat;
    }

 

    public Project(int id, String Name, String Description, LocalDate created, LocalDate duedate, int etat) {
        this.id = id;
        this.Name = Name;
        this.Description = Description;
        this.created = created;
        this.duedate = duedate;
        this.etat = etat;
    }

    public Project(String Name, String Description, LocalDate duedate) {
        this.Name = Name;
        this.Description = Description;
        this.duedate = duedate;
    }

  

    public Project(String Name, String Description) {
        this.Name = Name;
        this.Description = Description;
    }

 
    

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    
   

    public int getEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

   
    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public void setMaster_id(int master_id) {
        this.master_id = master_id;
    }

    public int getTeam_id() {
        return team_id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public int getMaster_id() {
        return master_id;
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + id + ", Name=" + Name + ", Description=" + Description + ", created=" + created + ", duedate=" + duedate + ", etat=" + etat + '}';
    }
    
    
}