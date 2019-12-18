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

@TestPropertySource("/testing.properties")
public class FriendListTest {
   static String student_number_format="sxxxxx%s";
   static String student_email_format="sxxxxx%s@student.dtu.dk";
   @Autowired
   FilteredUserRepository userRepository;
   @Autowired
   FriendListService friendListService;

   //this method is executed before every test case
   //use it to create any data you might need / initialize properties for the test
   @Before
   public void initializeTest() {
      deleteCreatedUsers();
      HashMap<Integer,User> createdUsers = new HashMap<Integer,User>();
      for(int i=1;i<7;i++)
      {
         String studentNr = String.format(student_number_format,i);
         String email = String.format(student_email_format,i);
         User user = new User("Name"+i,studentNr,email);
         userRepository.save(user);
         createdUsers.put(i,user);
      }
      User friend1 = createdUsers.get(1);
      User friend2 = createdUsers.get(2);
      User friend3 = createdUsers.get(3);
      User friend4 = createdUsers.get(4);
      friend1.setFriend(friend2);
      friend2.setFriend(friend1);
      friend1.setFriend(friend3);
      friend4.setFriend(friend1);
      
      userRepository.save(friend1);
      userRepository.save(friend2);
      userRepository.save(friend3);
      userRepository.save(friend4);
      // friendListService = new FriendListService();
   }
   //this method is executed after every test method
   //use this to cleanup / remove any data you have created in the before method or in the tests
   @After
   public void cleanupTest() {
      deleteCreatedUsers();
   }

   //test case checking that method getAllFriends returns the proper result
   @Test
   public void testGetAllFriends() {
      //execute the method from the service
      String userEmail = String.format(student_email_format,1);
      Set<User> friends = friendListService.getAllFriends(userEmail);
      //declare asserts (conditions that the result of the method must meet for the tests to pass)
      Assert.assertNotNull("getAllFriends is null", friends);
      Assert.assertTrue(!friends.isEmpty());
   }

   //test case checking that method getSentFriendRequests returns the proper result
   @Test
   public void testGetSentFriendRequests() {
      String userEmail = String.format(student_email_format,1);
      Assert.assertNotNull(friendListService);
      Set<User> friends = friendListService.getSentFriendRequests(userEmail);
      Assert.assertNotNull("getSentFriendRequests is null", friends);
      Assert.assertTrue(!friends.isEmpty());
   }

   //test case checking that method testAddFriend returns the proper result
   @Test
   public void testAddFriend() {
      //setting up variable before executing the method
      String userEmail1 = String.format(student_email_format,1);
      String userEmail2 = String.format(student_email_format,5);
      //getting some information about the object before execution
      Set<User> sentFriendRequests_initial = userRepository.findUserByEmail(userEmail1).getSentFriendRequests();
      Set<User> receivedFriendRequests_initial = userRepository.findUserByEmail(userEmail2).getReceivedFriendRequests();      
      //executing the method
      String result = friendListService.addFriend(userEmail1,userEmail2);
      //getting info about the objects after execution
      Set<User> sentFriendRequests_modified = userRepository.findUserByEmail(userEmail1).getSentFriendRequests();
      Set<User> receivedFriendRequests_modified = userRepository.findUserByEmail(userEmail2).getReceivedFriendRequests();      
     
      Assert.assertNotNull(result);
      Assert.assertNotNull(sentFriendRequests_initial);
      Assert.assertNotNull(receivedFriendRequests_initial);
      Assert.assertNotNull(sentFriendRequests_modified);
      Assert.assertNotNull(receivedFriendRequests_modified);
      Assert.assertSame((sentFriendRequests_initial.size() + 1), sentFriendRequests_modified.size());
      Assert.assertSame((receivedFriendRequests_initial.size() + 1), receivedFriendRequests_modified.size());
   }
   @Test
   public void testGetReceivedFriendRequests() {
      String userEmail = String.format(student_email_format,1);
      Set<User> friends = friendListService.getReceivedFriendRequests(userEmail);
      Assert.assertNotNull("getReceivedFriendRequests is null", friends);
      System.out.println("Size " + friends.size());
      Assert.assertTrue(!friends.isEmpty());
   }
   //test case checking that method testAddFriend returns the proper result
   @Test
   public void testAcceptFriendRequest() {
      //setting up variable before executing the method
      String userEmail1 = String.format(student_email_format,1);
      
      //getting some information about the object before execution
      Set<User> receivedFriendRequests_initial = userRepository.findUserByEmail(userEmail1).getReceivedFriendRequests();
      Set<User> friends_initial = userRepository.findUserByEmail(userEmail1).getFriends();

      String userEmail2 = receivedFriendRequests_initial.iterator().next().getEmail();
      Assert.assertTrue(userEmail2!=null && userEmail2!="");
      //executing the method
      String result = friendListService.acceptFriendRequest(userEmail1,userEmail2);
      //getting info about the objects after execution
      Set<User> receivedFriendRequests_modified = userRepository.findUserByEmail(userEmail2).getReceivedFriendRequests();     
      Set<User> friends_modified = userRepository.findUserByEmail(userEmail1).getFriends();

      Assert.assertNotNull(result);
      Assert.assertNotNull(receivedFriendRequests_initial);
      Assert.assertNotNull(friends_initial);
      Assert.assertNotNull(friends_modified);
      Assert.assertNotNull(receivedFriendRequests_modified);
      Assert.assertSame((receivedFriendRequests_initial.size() -1), receivedFriendRequests_modified.size());
      Assert.assertSame((friends_initial.size()+1), friends_modified.size());
      boolean friendListContains = false;
      for (User user : friends_modified) {
         if(user.getEmail() == userEmail2)
         {
            friendListContains= true;
            break;
         }
      }
      Assert.assertTrue(friendListContains);
   }
      //test case checking that method testAddFriend returns the proper result
      @Test
      public void testRejectFriendRequest() {
         //setting up variable before executing the method
         String userEmail1 = String.format(student_email_format,1);
         
         //getting some information about the object before execution
         Set<User> receivedFriendRequests_initial = userRepository.findUserByEmail(userEmail1).getReceivedFriendRequests();
         Set<User> friends_initial = userRepository.findUserByEmail(userEmail1).getFriends();
   
         String userEmail2 = receivedFriendRequests_initial.iterator().next().getEmail();
         
         //executing the method
         String result = friendListService.rejectFriendRequest(userEmail1,userEmail2);
         //getting info about the objects after execution
         Set<User> receivedFriendRequests_modified = userRepository.findUserByEmail(userEmail2).getReceivedFriendRequests();     
         Set<User> friends_modified = userRepository.findUserByEmail(userEmail1).getFriends();
   
         Assert.assertNotNull(result);
         Assert.assertNotNull(receivedFriendRequests_initial);
         Assert.assertNotNull(friends_initial);
         Assert.assertNotNull(friends_modified);
         Assert.assertNotNull(receivedFriendRequests_modified);
         Assert.assertSame((receivedFriendRequests_initial.size() ), receivedFriendRequests_modified.size()+1);
         Assert.assertSame((friends_initial.size() ), friends_modified.size());
         boolean friendListContains = false;
         for (User user : friends_modified) {
            if(user.getEmail() == userEmail2)
            {
               friendListContains= true;
               break;
            }
         }
         Assert.assertFalse(friendListContains);
      }
   private void deleteCreatedUsers()
   {
      for(int i=1;i<7;i++)
      {
         List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
         for (User user : users) {
            user.removeAllFriends();
         }
      }
      for(int i=1;i<7;i++)
      {
         List<User> users = userRepository.findUsersByEmail(String.format(student_email_format, i));
         userRepository.deleteAll(users);
      }
   }
}