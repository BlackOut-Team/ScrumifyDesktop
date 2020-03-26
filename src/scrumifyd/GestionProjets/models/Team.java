/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionProjets.models;

/**
 *
 * @author Amira Doghri
 */
public class Team {
    private String name ; 

    public Team() {
    }

    public Team(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Team{" + "name=" + name + '}';
    }
    
    
    
}
