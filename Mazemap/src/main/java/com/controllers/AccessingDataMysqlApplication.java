//package com.controllers;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.hibernate.*;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.Session;
//import org.hibernate.Transaction;
//import javax.persistence.*;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import java.io.Serializable;
//import java.util.*;
//import com.models.*;
//
//
//@SpringBootApplication
//public class AccessingDataMysqlApplication{
//
//    public static void main(String[] args) {
//
//        SpringApplication.run(AccessingDataMysqlApplication.class, args);
//
//
//        User user1 = new User();
//        user1.setName("andrew");
//        user1.setEmail("andrewnitu@mail.com");
//        user1.setFaculty(false);
//        user1.setResearcher(true);
//
//        User user2 = new User();
//        user2.setName("Emily");
//        user2.setEmail("emilypha@hotmail.com");
//        user2.setFaculty(false);
//        user2.setResearcher(false);
//
//        User user3 = new User();
//        user3.setName("Anna");
//        user3.setEmail("anna@mail.com");
//        user3.setFaculty(false);
//        user3.setResearcher(false);
//
//        User user4 = new User();
//        user4.setName("Simone");
//        user4.setEmail("simone@mail.com");
//        user4.setFaculty(true);
//        user4.setResearcher(false);
//
//
//        user1.setFriend(user2);
//        user3.setFriend(user1);
//        user3.setFriend(user4);
//
//        SurveyResult surveyresult = new SurveyResult();
//        surveyresult.setResult("result");
//        surveyresult.setUser(user1);
//
//        Survey survey = new Survey();
//        survey.seQuestions("Dummy");
//        survey.setCreator(user2);
//        surveyresult.setSurvey(survey);
//
//
//        Event event = new Event();
//        event.setCreator(user1);
//        event.addAttender(user2);
//        event.addAttender(user3);
//
//        Event event2 = new Event();
//        event2.setCreator(user4);
//        event2.addAttender(user1);
//        event2.addAttender(user2);
//        event2.addAttender(user3);
//
//        Group group = new Group();
//        group.setName("Group of nerds");
//        group.addMember(user2);
//        group.addMember(user3);
//
//
//        SessionFactory sf=new Configuration().configure().buildSessionFactory();
//        Session session=sf.openSession();
//        session.beginTransaction();
//        session.save(user1);
//        session.save(user2);
//        session.save(surveyresult);
//        session.save(survey);
//        session.save(event);
//        session.save(event2);
//        session.save(group);
//        session.getTransaction().commit();
//        session.close();
//
//    }
//
//
//}
//
