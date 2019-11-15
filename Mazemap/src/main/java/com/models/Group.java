package com.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// @Author: s191772
@Entity(name = "group_of_users") // This tells Hibernate to make a table out of this class
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

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

    @ManyToMany
    @JoinTable(name="group_members")
    private Set<User> member = new HashSet<User>();
    public void addMember (User user){
        member.add(user);
    }


}



