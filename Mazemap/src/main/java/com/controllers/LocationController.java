package com.controllers;

import java.util.HashSet;
import java.util.Set;
import com.models.*;
import com.repositories.*;
import com.services.LocationService;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;
import java.lang.*;


@RestController
@RequestMapping("/location/")
public class LocationController {
	@Autowired
    private LocationRepository locationRepository;
    @Autowired
    private FilteredUserRepository userRepository;
    @Autowired
    private LocationService locationService;


    @RequestMapping(value = "/addMessage", method = RequestMethod.POST)
    public String addMessage(@SessionAttribute("user") User currentUser, @RequestBody LocationOfUsers location) {
        String returnMessage = locationService.addMessage(currentUser.getEmail(),location);
        return returnMessage;
    }
    //     User cu = userRepository.findUsersByEmail(currentUser.getEmail()).get(0);

    //     LocationOfUsers loc = locationRepository.findLocationOfUsersByUser(cu);
    //     if (location != null && cu != null) {
    //         try {
    //         	if (loc!= null){
				// 	loc.setLocationMessage(location.getLocationMessage());
	   //          	loc.setCoordinateX(location.getCoordinateX());
	   //          	loc.setCoordinateY(location.getCoordinateY());
    //         		locationRepository.save(loc);
    //         	}
    //         	else{
    //         	location.setUser(cu);  
  	
    //         	locationRepository.save(location);
    //         	}
    //             return String.format("Your location was saved %s",location.getLocationMessage());
            
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             return "There was an error and location could not be saved";
    //         }
    //     } else {
    //         return "There was an error and location could not be broadcasted! Try again!";
    //     }
    // }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ArrayList<LocationOfUsers> getAllLocations(){
    //     ArrayList<LocationOfUsers> locations = new ArrayList<LocationOfUsers>();
    //     locationRepository.findAll().forEach(locations::add);
        return locationService.getAllLocations();
        
    }

}
