package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import com.repositories.*;
import java.util.Set;

import com.SpringBootJspApplication;
import com.models.*;
import com.services.*;
import java.util.*;
import java.util.List;
import java.sql.Date;

//run by using cmd mvn -q test
@SpringBootTest
@RunWith(SpringRunner.class)
@org.springframework.transaction.annotation.Transactional
// @ContextConfiguration(
// classes = {FilteredUserRepository.class,FriendListService.class})
@TestPropertySource("/testing.properties")
public class EventTest {
   static String student_number_format="sxxxxx%s";
   static String student_email_format="sxxxxx%s@student.dtu.dk";
   java.util.Date current = new java.util.Date();
   Date today = new Date(current.getTime());
   @Autowired
   FilteredUserRepository userRepository;
   @Autowired
   EventRepository eventRepository;
   @Autowired
   EventService eventService;
   
   //this method is executed before every test case
   //use it to create any data you might need / initialize properties for the test
   @Before
   public void initializeTest() {
	   
	   deleteCreatedEvents();
	   deleteCreatedUsers();
	   
	   
	   HashMap<Integer,User> createdUsers = new HashMap<Integer,User>();
	   HashMap<Integer,Event> createdEvents = new HashMap<Integer,Event>();
	      
	   for(int i=1;i<3;i++)
	   	{
		   String studentNr = String.format(student_number_format,i);
	       String email = String.format(student_email_format,i);
	       User user = new User("Name"+i,studentNr,email);
	       userRepository.save(user);
	       createdUsers.put(i,user);
	    }
	       Event event = new Event("yesterday", 14, 14, "yesterday", new Date(today.getTime()-(1L*24L*60L*60L*1000L)), createdUsers.get(1));
	       eventRepository.save(event);
	       createdEvents.put(1,event);
	       Event event2 = new Event("tomorrow", 14, 14, "tomorrow", new Date(today.getTime()+(1L*24L*60L*60L*1000L)), createdUsers.get(2));
	       eventRepository.save(event2);
	       createdEvents.put(2,event);
	       Event event3 = new Event("in30Days", 14, 14, "in30Days", new Date(today.getTime()+(30L*24L*60L*60L*1000L)), createdUsers.get(1));
	       eventRepository.save(event3);
	       createdEvents.put(3,event);
   }
 
   //this method is executed after every test method
   //use this to cleanup / remove any data you have created in the before method or in the tests
   @After
   public void cleanupTest() {
	  deleteCreatedEvents();
      deleteCreatedUsers();    
   }

   //test case checking that method getAllFriends returns the proper result
   @Test
   public void testGetEventData() {
      //execute the method from the service
      Set<Event> events = eventService.getEventData();
      Boolean hasTomorrowsEvent = false;
      Boolean noYesterdaysEvent = true;
      Boolean noMonthEvent = true;
      System.out.println(events);
      for (Event event : events) {
          if (event.getDescription() == "tomorrow") {
             hasTomorrowsEvent = true;
          }
          if (event.getDescription() == "yesterday") {
              noYesterdaysEvent = false;
           }
          if (event.getDescription() == "in30Days") {
              noMonthEvent = false;
           }

      }
      //declare asserts (conditions that the result of the method must meet for the tests to pass)
      Assert.assertNotNull("getEventData is null", events);
      Assert.assertTrue(hasTomorrowsEvent);
      Assert.assertTrue(noYesterdaysEvent);
      Assert.assertTrue(noMonthEvent);
   }
   @Test
   public void testCreateEvent() {
	   User user = userRepository.findUserByEmail(String.format(student_email_format, 1));
      //execute the method from the service
	  Event event = new Event("createEvent", 14, 14, "createEventdesc", new Date(today.getTime()), user);
      eventService.createEvent(user, event);
      List<Event> events = eventRepository.findEventsByCreator(user);
      
      Boolean foundEvent = false;
      
      for (Event testEvent : events) {
    	  if(testEvent.getName() == "createEvent")
    		  foundEvent = true;
      }
      //declare asserts (conditions that the result of the method must meet for the tests to pass)
      Assert.assertTrue(foundEvent);
   }
   @Test
   public void testDeleteEvent() {
	   User user = userRepository.findUserByEmail(String.format(student_email_format, 1));
	   List<Event> events = eventRepository.findEventsByCreator(user);
	   Event event = events.get(1);
	   Assert.assertNotNull("List is not null", events);
	   eventService.deleteEvent(user, event.getId());
	   events = eventRepository.findEventsByCreator(user);
	   Boolean eventWasDeleted = true; 
	   for( Event iterateEvent : events) {
		   if(iterateEvent.getId() == event.getId())
			   eventWasDeleted = false;
	   }
	   Assert.assertTrue(eventWasDeleted);
   }
   
   @Test
   public void testUpdateEvent() {
	   User user = userRepository.findUserByEmail(String.format(student_email_format, 1));
	   List<Event> events = eventRepository.findEventsByCreator(user);
	   Event event = events.get(1);
	   Assert.assertNotNull("List is not null", events);
	   Assert.assertTrue(event.getName() != "tested");
	   event.setName("tested");
	   eventService.updateEvent(user, event);
	   events = eventRepository.findEventsByCreator(user);
	   Event event1 = events.get(1);
	   Boolean eventWasUpdated = false; 
	   for( Event iterateEvent : events) {
		   if(iterateEvent.getId() == event.getId() && iterateEvent.getName() == "tested")
			   eventWasUpdated = true;
	   }
		   Assert.assertTrue(eventWasUpdated);
   }
  
   private void deleteCreatedEvents()
   {
      for(int i=1;i<3;i++)
      {
    	 List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
         
         for (User user : users) {
        	 List<Event> events = eventRepository.findEventsByCreator(user);
        	 eventRepository.deleteAll(events);  
         }
      }
   }
   
   private void deleteCreatedUsers()
   {
      for(int i=1;i<7;i++)
      {
         List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
         userRepository.deleteAll(users);
      }
   }
}