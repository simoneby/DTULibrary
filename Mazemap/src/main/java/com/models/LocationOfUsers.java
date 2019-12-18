package com.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//@Author s191772, s191545
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "user"})
@Entity(name = "location") // This tells Hibernate to make a table out of this class
public class LocationOfUsers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private double coordinateX;

    private double coordinateY;

    private String locationMessage;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(double coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(double coordinateY) {
        this.coordinateY = coordinateY;
    }

    public String getLocationMessage(){
        return locationMessage;
    }

    public void setLocationMessage(String locationMessage){
        this.locationMessage = locationMessage;

    }


    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser() {
        return this.user;
    }



}
