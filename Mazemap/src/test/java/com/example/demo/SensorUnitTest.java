//@Author s191218

package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

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

//run by using cmd mvn -q test
@SpringBootTest
@RunWith(SpringRunner.class)
@org.springframework.transaction.annotation.Transactional
// @ContextConfiguration(
// classes = {FilteredUserRepository.class,FriendListService.class})
@TestPropertySource("/application.properties")
public class SensorUnitTest 
{
   @Autowired
   SensorDataService sensorDataService;

   //this method is executed before every test case
   //use it to create any data you might need / initialize properties for the test
   
   //test case checking that method getZoneData returns the proper result
   @Test
   public void testGetZoneData() 
   {
		int maxlevel = 2;
      //execute the method from the service
	  for(short i = 0; i<=maxlevel; ++i)
	  {
	      ProcessedSensorData[] sensorData = sensorDataService.getZoneData(i);
		  //declare asserts (conditions that the result of the method must meet for the tests to pass)
		  if(sensorData==null)
			System.out.println("There is no sensor data on level" + i);
	  }      
   } 
}
