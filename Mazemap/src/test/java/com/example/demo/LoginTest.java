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
import com.helpers.*;
import java.util.*;
import java.util.List;
import java.sql.Date;
import java.io.IOException;

//@Author s154666
//run by using cmd mvn -q test
@SpringBootTest
@RunWith(SpringRunner.class)
@org.springframework.transaction.annotation.Transactional
// @ContextConfiguration(
// classes = {FilteredUserRepository.class,FriendListService.class})
@TestPropertySource("/application.properties")
public class LoginTest 
{
	static String student_number_format="sxxxxx%s";
	static String student_email_format="sxxxxx%s@student.dtu.dk";
	@Autowired
	FilteredUserRepository userRepository;


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


	@Test
	public void testRegisterUser() 
	{
		String studentNr = String.format(student_number_format,9);
		String email = String.format(student_email_format,9);
		User user = new User("Name"+9,studentNr,email);
		
		RedirectWrapper result = null;
		try {
			result = LoginService.redirectService(studentNr);
		} catch (IOException e)
		{
			Assert.fail();
		}

		Assert.assertFalse(result.getExisted());

		User foundUser = userRepository.findUserByStudentnr(studentNr);

		Assert.assertNotNull(foundUser);

		Assert.assertEquals(user.getStudentnr(),foundUser.getStudentnr());
		Assert.assertEquals(user.getEmail(),foundUser.getEmail());


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
