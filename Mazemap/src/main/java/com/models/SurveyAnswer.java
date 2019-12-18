package com.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.*;
import org.hibernate.SessionFactory;
import java.io.Serializable;
import java.util.*;




// @Author: s191772, s154666
@Entity() // This tells Hibernate to make a table out of this class
public class SurveyAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    public Integer getId() {
        return id;
    }

    private Date date;

    private Time time;

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


    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    @JoinColumn(name="survey_id")
    private Survey survey;
    public void setSurvey(Survey survey){
        this.survey = survey;
    }

    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name="surveyAnswer_id")
    private Set<QuestionAnswer> questionAnswers;

    public Set<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public User getUser() {
        return user;
    }

    public Survey getSurvey() {
        return survey;
    }
}