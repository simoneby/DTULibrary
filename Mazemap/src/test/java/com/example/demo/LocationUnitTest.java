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
      deleteCreatedUsers();
      HashMap<Integer,User> createdUsers = new HashMap<Integer,User>();
      HashMap<LocationOfUsers> createdLocations = new HashMap<LocationOfUsers>();

      for(int i=1;i<7;i++)
      {
         String studentNr = String.format(student_number_format,i);
         String email = String.format(student_email_format,i);
         User user = new User("Name"+i,studentNr,email);
         LocationOfUsers location = new LocationOfUsers(i,i*10,i*10,"message"+i);//id, coordx,coordy,message
         locationRespository.save(location)
         userRepository.save(user);
         createdUsers.put(i,user);
         createdLocations.put(location)
      }
      User friend1 = createdUsers.get(1);
      User friend2 = createdUsers.get(2);
      User friend3 = createdUsers.get(3);
      User friend4 = createdUsers.get(4);
      friend1.setFriend(friend2);
      friend2.setFriend(friend1);
      friend1.setFriend(friend3);
      friend4.setFriend(friend1);


      LocationOfUsers location1 = createdLocations.get(1);
      LocationOfUsers location2 = createdLocations.get(2);

      friend1.setLocationOfUsers(location1);
      friend2.setLocationOfUsers(location2);

      
      userRepository.save(friend1);
      userRepository.save(friend2);
      userRepository.save(friend3);
      userRepository.save(friend4);

      locationRespository.save(loc)

      // friendListService = new FriendListService();
   }
   //this method is executed after every test method
   //use this to cleanup / remove any data you have created in the before method or in the tests
   @After
   public void cleanupTest() {
      deleteCreatedUsers();
   }

   //test case checking that method getAllFriends returns the proper result
   //results need to be used to check user location
   @Test
   public void testGetAllFriends() {
      //execute the method from the service
      String userEmail = String.format(student_email_format,1);
      Set<User> friends = friendListService.getAllFriends(userEmail);
      //declare asserts (conditions that the result of the method must meet for the tests to pass)
      Assert.assertNotNull("getAllFriends is null", friends);
      Assert.assertTrue(!friends.isEmpty());
   }


   //results need to be used to check user location
   @Test
   public void testGetAllLocations() {
      //execute the method from the service
      ArrayList<LocationOfUsers> locations = locationService.getAllLocations();
      //declare asserts (conditions that the result of the method must meet for the tests to pass)
      Assert.assertNotNull("getAllLocations is null", locations);
      Assert.assertTrue(!location.isEmpty());
   }

   //test case checking that method getSentFriendRequests returns the proper result
   @Test
   public void testGetAllFriendsLocations() {
      String userEmail = String.format(student_email_format,1);
      Set<User> friends = friendListService.getAllFriends(userEmail);
      //declare asserts (conditions that the result of the method must meet for the tests to pass)
      Assert.assertNotNull("getAllFriends is null", friends);
      Assert.assertNotNull("FriendsLocation is null", friends[0].locationOfUsers);

      // String userEmail = String.format(student_email_format,1);
      // Assert.assertNotNull(friendListService);
      // Set<User> friends = friendListService.getSentFriendRequests(userEmail);
      // Assert.assertNotNull("getSentFriendRequests is null", friends);
      // Assert.assertTrue(!friends.isEmpty());
   }

   //test case checking that method getSentFriendRequests returns the proper result
   @Test
   public void testAddLocation() {
      String userEmail = String.format(student_email_format,1);
      LocationOfUsers locationTest = new LocationOfUsers(192,50,12,"messageTest");      
      String didItAdd = locationService.addMessage(userEmail, locationTest)
      Assert.assertTrue(didItAdd.contains("Your location was saved"));

   }
   @Test
   public void testBroadcastLocation() {
      //This test has been modified to test for manually 
     
   }

   // private void deleteCreatedUsers()
   // {
   //    for(int i=1;i<7;i++)
   //    {
   //       List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
   //       for (User user : users) {
   //          user.removeAllFriends();
   //       }
   //    }
   //    for(int i=1;i<7;i++)
   //    {
   //       List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
   //       userRepository.deleteAll(users);
   //    }
   // }
}