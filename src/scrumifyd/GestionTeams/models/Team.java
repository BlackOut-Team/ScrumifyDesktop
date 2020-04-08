/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionTeams.models;

/**
 *
 * @author Amira Doghri
 */
public class Team {
    int id;
    String name;

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    

    public Team() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    
}
