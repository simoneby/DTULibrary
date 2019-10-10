package com.models;

import org.hibernate.Session;

import javax.persistence.*;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.*;




@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

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

    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="friends",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="friend_id")})
    private Set<User> friends = new HashSet<User>();

    @ManyToMany(mappedBy="friends")
    private Set<User> teammates = new HashSet<User>();

    public void setFriend(User friend) {
        friends.add(friend);
        friend.friends.add(this);
    }

    public Set<User> getFriends(){
        return friends;
    }


}



