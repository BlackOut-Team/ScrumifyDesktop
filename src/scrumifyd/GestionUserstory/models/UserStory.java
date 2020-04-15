/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUserstory.models;

/**
 *
 * @author youssef
 */
public class UserStory {
    private int id;
    private String description;
    private int priority;
    private int story_point;
    private int etat;
    private int feature_id;
    private int isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStory_point() {
        return story_point;
    }

    public void setStory_point(int story_point) {
        this.story_point = story_point;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(int feature_id) {
        this.feature_id = feature_id;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public UserStory() {
    }

    public UserStory(int id, String description, int priority, int story_point, int etat, int feature_id, int isDeleted) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.story_point = story_point;
        this.etat = etat;
        this.feature_id = feature_id;
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "UserStory{" + "id=" + id + ", description=" + description + ", priority=" + priority + ", story_point=" + story_point + ", etat=" + etat + ", feature_id=" + feature_id + ", isDeleted=" + isDeleted + '}';
    }
    
}
