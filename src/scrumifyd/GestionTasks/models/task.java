/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author mahdi
 */
public class task {
    private int id,priority,etat;
    private String title,status;
    private String description;
    private LocalDateTime upd;
    private LocalDate created,updated,finished;
    //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    Date date = new Date();  
     String members;

    public task() {
    }

    public task(int etat) {
        this.etat = etat;
    }

    public task(int priority, int etat, String title, String status, String description, LocalDate created) {
        this.priority = priority;
        this.etat = etat;
        this.title = title;
        this.status = status;
        this.description = description;
        this.created = created;
    }
                            
    public task(String title, String description, LocalDate created, LocalDate updated, LocalDate finished,int priority,String status) {
        
        this.title = title;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.finished = finished;
        this.priority = priority;
        this.status=status;
    }
public task(String title,int id, String description, LocalDate created, LocalDate updated, LocalDate finished,int priority,int etat ,String status) {
        
        this.title = title;
        this.id=id;
        this.description = description;
        this.created = created;
        this.updated = updated;
        this.finished = finished;
        this.priority = priority;
        this.etat = etat;
        this.status=status;
    }
    public task(String title,int id) {
        this.title = title;
        this.id = id;
    }

    public task(int priority, int etat, String title, String description) {
        this.priority = priority;
        this.etat = etat;
        this.title = title;
        this.description = description;
    }

    public task(int priority, String title, String description, LocalDate finished,String members) {
        
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.finished = finished;
        this.members = members;
    }

    public task(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }

    public int getEtat() {
        return etat;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreated() {
        return created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public LocalDate getFinished() {
        return finished;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public void setFinished(LocalDate finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "task{" + "id=" + id + ", priority=" + priority + ", etat=" + etat + ", title=" + title + ", status=" + status + ", description=" + description + ", upd=" + upd + ", created=" + created + ", updated=" + updated + ", finished=" + finished + ", date=" + date + ", members=" + members + '}';
    }

 
    
}
