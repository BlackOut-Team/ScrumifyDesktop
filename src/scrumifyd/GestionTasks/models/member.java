/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTasks.models;

import javafx.scene.control.CheckBox;

/**
 *
 * @author mahdi
 */

public class member {
    
private int id;
private String nom;
CheckBox check;

    public member() {
    }

    public member(int id,String nom) {
        this.id = id;
        this.nom = nom;
    }

    public member(int id, String nom, CheckBox check) {
        this.id = id;
        this.nom = nom;
        this.check = check;
    }

    public CheckBox getCheck() {
        return check;
    }

    public void setCheck(CheckBox check) {
        this.check = check;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
