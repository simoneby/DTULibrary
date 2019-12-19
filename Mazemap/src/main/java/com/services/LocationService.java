package com.services;

import com.repositories.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import com.models.*;
import java.util.*;

//@Author s191545
@Component
public class LocationService {
	@Autowired
    private LocationRepository locationRepository;
    @Autowired
    private FilteredUserRepository userRepository;


    public String addMessage(String userEmail, LocationOfUsers location) {

        User cu = userRepository.findUsersByEmail(userEmail).get(0);

        LocationOfUsers loc = locationRepository.findLocationOfUsersByUser(cu);
        

        if (location != null && cu != null) {
            try {
            	if (loc!= null){
					loc.setLocationMessage(location.getLocationMessage());
	            	loc.setCoordinateX(location.getCoordinateX());
	            	loc.setCoordinateY(location.getCoordinateY());
            		locationRepository.save(loc);
            	}
            	else{
            	location.setUser(cu);  
  	
            	locationRepository.save(location);
            	}
                return String.format("Your location was saved %s",location.getLocationMessage());
            
            } catch (Exception e) {
                e.printStackTrace();
                return "There was an error and location could not be saved";
            }
        }
        return "not saved";
    }

    public ArrayList<LocationOfUsers> getAllLocations() {
        ArrayList<LocationOfUsers> locations = new ArrayList<LocationOfUsers>();
        locationRepository.findAll().forEach(locations::add);
        return locations;
    }

    // public Set<User> getAllFriends(String userEmail) {
    //     if (userEmail == null) {
    //         try {
    //             throw new Exception("User email undefined");
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //             return new HashSet<User>();
    //         }
    //     }
    //     return userRepository.findUserByEmail(userEmail).getFriends();
    // }

}