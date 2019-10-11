package com.example.demo.accessingsatamysql;



import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.io.Serializable;
import java.text.StringCharacterIterator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.*;



@Entity
@Table(name = "FRIENDSHIPS")
public class Friendship implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    User requester;

    @Id
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    User friend;

    @Temporal(javax.persistence.TemporalType.DATE)
    Date date;

    @Column(nullable = false)
    boolean active;

    // getters & setters

}