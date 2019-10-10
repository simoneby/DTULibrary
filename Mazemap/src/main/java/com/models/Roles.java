


package com.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "roles") // This tells Hibernate to make a table out of this class
public class Roles {
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
    @JoinTable(name="roles_of_users")
    private Set<User> role = new HashSet<User>();
    public void addRole (User user){
        role.add(user);
    }


}
