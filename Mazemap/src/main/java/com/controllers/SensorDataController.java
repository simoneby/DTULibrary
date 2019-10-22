package com.controllers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;
import com.models.*;
import com.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/sensors")
public class SensorDataController {
	@Autowired
	RestTemplate restTemplate;
    @RequestMapping(value="/base",method=RequestMethod.GET)
    public String getBaseData()
    {
    	 ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
    	 HttpHeaders headers = new HttpHeaders();
         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
         //HttpEntity<SensorData> entity = new HttpEntity<SensorData>(sensorData,headers);
         HttpEntity entity = new HttpEntity(headers);
         //var x = new entity
         return restTemplate.getForObject("https://scadadataapi.azurewebsites.net/api/values", sensorData.getClass()).toString();
    }
    @RequestMapping(value="/peopleCounter",method=RequestMethod.GET)
    public String getPeopleCounter(@RequestParam(value="studnum", required=false, defaultValue="") String name) 
    {
    	ArrayList<Installation> installations = new ArrayList<Installation>();
    	HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("x-client-id", "DTUAPI");
        headers.add("x-api-key", "3593e5b65f4ad590f859a876f976ba18");
        Object uriVariables =null;
        HttpEntity<ArrayList<Installation>> entity = new HttpEntity<ArrayList<Installation>>(installations,headers);
        ResponseEntity<? extends ArrayList> response = restTemplate.exchange(
        		"https://eds.modcam.io/v1/peoplecounter/installations", HttpMethod.GET, entity, installations.getClass(),uriVariables);
       return response.getBody().toString();
        //restTemplate.exch
        //installations = restTemplate.getForObject( "https://eds.modcam.io/v1/heatmap/installations", installations.getClass());
        /*
          for(Installation i : installations)
         
        {
        	
        }
        */
    }
}
