/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionUsers.models;

/**
 *
 * @author Amira Doghri
 */
public class User {
    int id;
    String name;

    public String getName() {
        return name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    

    public User(int id) {
        this.id = id;
    }

    public User() {
    }

    public int getId() {
        return id;
    }
    
    
}
