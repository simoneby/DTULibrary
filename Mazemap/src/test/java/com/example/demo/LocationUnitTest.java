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
import java.util.Iterator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import com.repositories.*;
import java.util.Set;

import com.SpringBootJspApplication;
import com.models.*;
import com.services.*;

//@s191545
//run by using cmd mvn -q test
@SpringBootTest
@RunWith(SpringRunner.class)
@org.springframework.transaction.annotation.Transactional
// @ContextConfiguration(
// classes = {FilteredUserRepository.class,FriendListService.class})
@TestPropertySource("/application.properties")
public class LocationUnitTest {
   static String student_number_format="sxxxxx%s";
   static String student_email_format="sxxxxx%s@student.dtu.dk";
   @Autowired
   FilteredUserRepository userRepository;
   @Autowired
   FriendListService friendListService;
   @Autowired
   LocationRepository locationRepository;
   @Autowired
   LocationService locationService;

   //this method is executed before every test case
   //use it to create any data you might need / initialize properties for the test
   @Before
   public void initializeTest() {
      deleteCreatedUsers(); // delete locations and locations
      HashMap<Integer,User> createdUsers = new HashMap<Integer,User>();
      HashMap<Integer,LocationOfUsers> createdLocations = new HashMap<Integer,LocationOfUsers>();

      for(int i=1;i<7;i++)
      {
         String studentNr = String.format(student_number_format,i);
         String email = String.format(student_email_format,i);
         User user = new User("Name"+i,studentNr,email);
         LocationOfUsers location = new LocationOfUsers(user,i*10,i*10,"message"+i);//id, coordx,coordy,message
         locationRepository.save(location);
         userRepository.save(user);
         createdUsers.put(i,user);
      }
      User friend1 = createdUsers.get(1);
      User friend2 = createdUsers.get(2);
      
      friend1.setFriend(friend2);
      friend2.setFriend(friend1);

      userRepository.save(friend1);
      userRepository.save(friend2);

   }
   //this method is executed after every test method
   //use this to cleanup / remove any data you have created in the before method or in the tests
   @After
   public void cleanupTest() {
      deleteCreatedUsers();

   }

   //test case to get all the locations from database
   @Test
   public void testGetAllLocations() {
      //execute the method from the service
      ArrayList<LocationOfUsers> locations = locationService.getAllLocations();
      //declare asserts (conditions that the result of the method must meet for the tests to pass)
      Assert.assertNotNull("getAllLocations is null", locations);
      Assert.assertTrue(!locations.isEmpty());
      for (LocationOfUsers location : locations){
         Assert.assertNotNull("Location must have a user", location.getUser());

      }
   }

   //test case checking that all your friends locations
   @Test
   public void testGetAllFriendsLocations() {
      String userEmail = String.format(student_email_format,1);
      Set<User> friends = friendListService.getAllFriends(userEmail);
      Assert.assertNotNull("getAllFriends is null", friends);
      for (User user : friends)
      {
         Assert.assertNotNull("Cannot get friends location", locationRepository.findLocationOfUsersByUser(user));
      }
   }

   //testing that all add location storing location information into the database
   @Test
   public void testAddLocation() {
      // String userEmail = String.format(student_email_format,1);
      String studentNr = String.format(student_number_format,5);
      String email = String.format(student_email_format,5);
      User user = new User("Name"+5,studentNr,email);
      LocationOfUsers locationTest = new LocationOfUsers(user,50,12,"messageTest");      
      String didItAdd = locationService.addMessage(email, locationTest);
      Assert.assertTrue(didItAdd.contains("Your location was saved"));

   }
  
   private void deleteCreatedUsers()
   {
      for(int i=1;i<7;i++)
      {
         List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
         for (User user : users) {
            user.removeAllFriends();
            LocationOfUsers userLocation = locationRepository.findLocationOfUsersByUser(user);
            if (userLocation != null){
               locationRepository.delete(userLocation);
            }
         }
      }
      for(int i=1;i<8;i++)
      {
         List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
         userRepository.deleteAll(users);
      }
   }
  
}