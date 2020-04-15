/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.models;

/**
 *
 * @author hp
 */
public class Activity {
     int id;
    String action;
    int viewed;
   

    public Activity(int id, String action, int viewed) {
        this.id = id;
        this.action = action;
        this.viewed = viewed;
    }

    public Activity(String action, int viewed) {
        this.action = action;
        this.viewed = viewed;
    }

    public int getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public int getViewed() {
        return viewed;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + id + ", action=" + action + ", viewed=" + viewed + '}';
    }
    
}


