/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scrumifyd.GestionMeetings.models;

import java.time.LocalDate;

/**
 *
 * @author hp
 */
public class Meeting {

    private int id;
    private String name, place;
    private String type;
    private LocalDate meetingDate;
    private int sprint;

    public int getSprint() {
        return sprint;
    }

    public void setSprint(int sprint) {
        this.sprint = sprint;
    }

    public Meeting(String name, String place, String type, int sprint, LocalDate meetingDate) {
        this.name = name;
        this.place = place;
        this.sprint = sprint;
        this.type = type;
        this.meetingDate = meetingDate;
    }

    public Meeting(int id, String name, String place, String type, LocalDate meetingDate) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.type = type;
        this.meetingDate = meetingDate;
    }
     public Meeting(int id, String name, String place, String type, LocalDate meetingDate,int sprint) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.type = type;
        this.meetingDate = meetingDate;
          this.sprint=sprint;
    }

    public Meeting(String name, String place, String type, LocalDate meetingDate ) {
        this.name = name;
        this.place = place;
        this.type = type;
        this.meetingDate=meetingDate;
      
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setType(String type) {
        this.name = type;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Override
    public String toString() {
        return "meetings{" + "id=" + id + ", name=" + name + ", place=" + place + ", type=" + type + ", meetingDate=" + meetingDate + '}';
    }
}
