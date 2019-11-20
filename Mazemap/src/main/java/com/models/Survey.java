package com.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.*;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.util.*;
import java.sql.Blob;


// @Author: s191772
@Entity // This tells Hibernate to make a table out of this class
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Set<Question> questions;
    @ManyToOne
    @JoinColumn(name="survey_creator")

    private User creator;
    public void setCreator (User creator) {
        this.creator = creator;
    }

    public User getCreator() {
        return creator;
    }

}