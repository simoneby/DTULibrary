package com.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.*;


//@Author s192671
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "friends", "teammates", "friendRequests",
        "sentFriendRequests", "receivedFriendRequests" })
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

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @JoinTable(name = "following", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "friend_id") })
    private Set<User> friends = new HashSet<User>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY)
    @JoinTable(name = "followers", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "follower_id") })
    private Set<User> friendRequests = new HashSet<User>();

    public Set<User> getReceivedFriendRequests() {
        Set<User> receivedFriendRequests = new HashSet<User>(friendRequests);
        receivedFriendRequests.removeAll(friends);
        return receivedFriendRequests;
    }

    public Set<User> getSentFriendRequests() {
        Set<User> sentFriendRequests = new HashSet<User>(friends);
        sentFriendRequests.removeAll(friendRequests);
        return sentFriendRequests;
    }

    public void setFriendRequest(User user) {
        friendRequests.add(user);
    }

    public void setFriend(User friend) {
        friends.add(friend);
        friend.setFriendRequest(this);
    }

    @ManyToMany(mappedBy = "friends", fetch = FetchType.LAZY)
    private Set<User> teammates = new HashSet<User>();

    public Set<User> getFriends() {
        Set<User> intersection = new HashSet<User>(friends);
        intersection.retainAll(friendRequests);
        return intersection;
    }

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
            @JoinColumn(name = "role_id") })
    private Set<Role> roles = new HashSet<Role>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @OneToOne(mappedBy="user")
    private LocationOfUsers location;

    public void setLocationOfUser(LocationOfUsers location) {
        this.location = location;
    }
    public LocationOfUsers getLocationOfUser() {
        return this.location;
    }



    @Override
    public String toString() {
        return "User [email=" + email + ", friends=" + friends + ", id=" + id + ", name=" + name + ", roles=" + roles
                + ", teammates=" + teammates + "]";
    }

    public User findFriendByEmail(String email) {
        for (User user : getFriends()) {
            if (user.getEmail().equals(email))
                return user;
        }
        return null;
    }

    public boolean removeFollower(String email) {
        User found = null;
        for (User user : friendRequests) {
            if (user.getEmail().equals(email)) {
                found = user;
                break;
            }
        }
        if (found == null)
            return false;
        return friendRequests.remove(found);
    }

    public boolean removeFollowing(String email) {
        User found = null;
        for (User user : friends) {
            if (user.getEmail().equals(email)) {
                found = user;
                break;
            }
        }
        if (found == null)
            return false;
        return friends.remove(found);
    }

    public boolean removeFriendFromFollowingByEmail(String email) {
        User friend = findFriendByEmail(email);
        if (friend == null)
            return false;
        friends.remove(friend);
        return true;
    }

    public boolean removeFriendFromFollowerByEmail(String email) {
        User friend = findFriendByEmail(email);
        if (friend == null)
            return false;
        friendRequests.remove(friend);
        return true;
    }
    public void removeAllFriends()
    {
        friends.clear();
        friendRequests.clear();
    }

    public User(String name, String studentnr, String email) {
        this.name = name;
        this.studentnr = studentnr;
        this.email = email;
    }

    public User() {
    }
}
