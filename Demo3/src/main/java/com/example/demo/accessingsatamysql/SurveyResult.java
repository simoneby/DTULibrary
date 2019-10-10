package com.example.demo.accessingsatamysql;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

@Entity // This tells Hibernate to make a table out of this class
public class SurveyResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String location;

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    private String result;      // Returns a string or a text file???

    private java.sql.Date date;

    private java.sql.Time time;

    public Integer getId() {
        return id;
    }



}