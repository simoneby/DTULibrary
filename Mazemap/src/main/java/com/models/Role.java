
package com.models;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
// @Author: s191772
@Entity(name = "role") // This tells Hibernate to make a table out of this class
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    public Integer getId() {
        return id;
    }

    public Role() {
    	
    }
    public Role(String name) {
		super();
		this.name = name;
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
/*
    @ManyToMany
    @JoinTable(name="roles_of_users")
    private Set<User> role = new HashSet<User>();
    public void addRole (User user){
        role.add(user);
    }
*/

}
