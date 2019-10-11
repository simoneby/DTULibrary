package com.example.demo.accessingsatamysql;

import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.*;



@Entity // This tells Hibernate to make a table out of this class
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "requester")
    private Set<Friendship> friendRequests = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "friend")
    private Set<Friendship> friends = new HashSet<>();

    private Integer id;

    private String name;

    private String email;

    private Boolean isFaculty;     // Grants access to make public events

    public Boolean getFaculty() {
        return isFaculty;
    }

    public void setFaculty(Boolean faculty) {
        isFaculty = faculty;
    }

    public Boolean getResearcher() {
        return isResearcher;
    }

    public void setResearcher(Boolean researcher) {
        isResearcher = researcher;
    }

    private Boolean isResearcher;  // Grants access to make and read survey/results





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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}



