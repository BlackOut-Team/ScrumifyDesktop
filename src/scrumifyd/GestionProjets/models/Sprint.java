/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.models;

import java.time.LocalDate;

/**
 *
 * @author Amira Doghri
 */
public class Sprint {
    int id;
    String name;
    String Description;
    LocalDate start;
    LocalDate duedate;
    int etat;
    int project_id;

    public Sprint() {
    }

    public Sprint(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Sprint(int id, String name, String Description, LocalDate start, LocalDate duedate, int etat) {
        this.id = id;
        this.name = name;
        this.Description = Description;
        this.start = start;
        this.duedate = duedate;
        this.etat = etat;
    }

    public Sprint(String name, String Description, LocalDate start, LocalDate duedate, int etat) {
        this.name = name;
        this.Description = Description;
        this.start = start;
        this.duedate = duedate;
        this.etat = etat;
    }

    public Sprint(int etat) {
        this.etat = etat;
    }

    public Sprint(int etat, int project_id) {
        this.etat = etat;
        this.project_id = project_id;
    }
   
    public Sprint(String name, String Description, LocalDate start, LocalDate duedate, int etat, int project_id) {
        this.name = name;
        this.Description = Description;
        this.start = start;
        this.duedate = duedate;
        this.etat = etat;
        this.project_id = project_id;
    }

    public Sprint(String name, String Description, LocalDate start, LocalDate duedate) {
        this.name = name;
        this.Description = Description;
        this.start = start;
        this.duedate = duedate;
    }

    @Override
    public String toString() {
        return "Sprint{" + "id=" + id + ", name=" + name + ", Description=" + Description + ", start=" + start + ", duedate=" + duedate + ", etat=" + etat + ", project_id=" + project_id + '}';
    }

   
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return Description;
    }

    public LocalDate getCreated() {
        return start;
    }

    public LocalDate getDuedate() {
        return duedate;
    }

    public int getEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String Dscription) {
        this.Description = Dscription;
    }

    public void setCreated(LocalDate created) {
        this.start = created;
    }

    public void setDuedate(LocalDate duedate) {
        this.duedate = duedate;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

  
 

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

 

    public int getProject_id() {
        return project_id;
    }
    
    

    
    
    
}
