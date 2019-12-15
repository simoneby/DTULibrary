package com.models;


import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.helpers.*;
import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.io.Serializable;
import java.util.*;

//@Author s191772, s183051
@Entity // This tells Hibernate to make a table out of this class
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
  
    private String name; // name of the event

    private double lat;  // string or ??
    
    private double lng;  // string or ??

    private String description; // string or text file(?)

    private Boolean isPublic;      // is the event public (open for everyone)

    private java.sql.Date date;
    
    @JsonDeserialize(using = SqlTimeHelper.class)
    private java.sql.Time time;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public void setTime(Time time) {
        this.time = time;
    }


    public Time getTime() {
        return time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }


    public User getCreator() {
        return creator;
    }

    @OneToOne
    @JoinColumn(name="event_creator")
    private User creator;
    public void setCreator (User creator) {
        this.creator = creator;
    }

    @ManyToMany
    @JoinTable(name="attends_event")
    private Set<User> attends = new HashSet<User>();
    public void addAttender (User user){
        attends.add(user);
    }


}
