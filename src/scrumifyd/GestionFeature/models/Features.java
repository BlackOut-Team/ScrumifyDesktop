/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionFeature.models;

/**
 *
 * @author youssef
 */
public class Features {
    private int id ;
    private String name ;
    private int etat ;
    private int isDeleted;
    private int sprint_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public int getSprint_id() {
        return sprint_id;
    }

    public void setSprint_id(int sprint_id) {
        this.sprint_id = sprint_id;
    }

    public Features() {
    }

    public Features(int id, String name, int etat, int isDeleted, int sprint_id) {
        this.id = id;
        this.name = name;
        this.etat = etat;
        this.isDeleted = isDeleted;
        this.sprint_id = sprint_id;
    }

    @Override
    public String toString() {
        return "Features{" + "id=" + id + ", name=" + name + ", etat=" + etat + ", isDeleted=" + isDeleted + ", sprint_id=" + sprint_id + '}';
    }
    
  
}

