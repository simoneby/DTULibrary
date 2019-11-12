package com.models;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.repository.cdi.Eager;

import java.util.*;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "friends","teammates"})
@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public String getStudentnr() {
        return studentnr;
    }

    public void setStudentnr(String studentnr) {
        this.studentnr = studentnr;
    }

    private String studentnr;

    private String email;
    @JsonIgnore
    private String password;
    @JsonIgnore
    @Transient
    private String repassword;
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
    
    @ManyToMany(cascade={CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.LAZY)
    @JoinTable(name="friends",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="friend_id")})
    private Set<User> friends = new HashSet<User>();

    public void setFriend(User friend) {
        //Hibernate.initialize(friends);
        friends.add(friend);
        //friend.friends.add(this);
    }
    @ManyToMany(mappedBy = "friends",fetch =  FetchType.LAZY)
    private Set<User> teammates = new HashSet<User>();

    public Set<User> getFriends(){
        return friends;
    }

    @ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns={@JoinColumn(name="user_id")},
            inverseJoinColumns={@JoinColumn(name="role_id")})
    private Set<Role> roles = new HashSet<Role>();
    public void addRole (Role role){
        roles.add(role);
    }
    public Set<Role> getRoles()
    {
    	return roles;
    }
    public void setRoles(Set<Role> roles)
    {
    	this.roles = roles;
    }
    @Override
    public String toString() {
        return "User [email=" + email + ", friends=" + friends + ", id=" + id + ", name=" + name + ", roles=" + roles
                + ", teammates=" + teammates + "]";
    }
}



