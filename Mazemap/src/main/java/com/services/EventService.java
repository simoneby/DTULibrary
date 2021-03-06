//@Author s170899,s183051
package com.services;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.validation.Errors;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import java.util.stream.Collectors;
import com.helpers.*;
import com.helpers.ReturnMessageHelper;
import com.models.*;
import java.util.Set;
//import java.util.concurrent.atomic.AtomicLong;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.sql.Date;

@Component
public class EventService
{
    @Autowired
    private EventRepository eventRepository;
    
    @Autowired
    private UserRepository userRepository;

    
    public Set<Event> getEventData() 
	  {
    	java.util.Date current = new java.util.Date();
        Date today = new Date(current.getTime());
        HashSet<Event> allEvents = new HashSet<Event>();
		eventRepository.findAll().forEach(allEvents::add);
	    Set<Event> activeEvents = new HashSet<Event>();
        for (Event event : allEvents) {
            if (event.getDate().after(new Date(today.getTime() -1l*24l*60l*60l*1000l)) && event.getDate().before(new Date(today.getTime() + 7l*24l*60l*60l*1000l))) {
               activeEvents.add(event);
            }

        }
        return activeEvents;
	  }
    public Set<Event> getUserFilteredEventData(User user) 
	  {
    	List<Event> userFilteredEvents = eventRepository.findEventsByCreator(user);
		return new HashSet<Event>(userFilteredEvents);
	  }
    
    public void createEvent(User user,
            Event event) {
			event.setCreator(user);
			eventRepository.save(event);
    }
    public String deleteEvent(User user,
			@RequestParam  Integer id) {
    	String returnMessage = "";
    	if (id == 0)
    		returnMessage = String.format("The event with id %s does not exist!", id);
    	else {
       	Optional<Event> tempEvent = eventRepository.findById(id);
		Event event = tempEvent.get();
		if(event.getCreator().getId() != user.getId())
			returnMessage = String.format("Creator doesn't match logged in user!");
		else
			eventRepository.deleteById(id);
    	}
    	return ReturnMessageHelper.getReturnMessage(returnMessage);
    }
    
    public void updateEvent(User user,
    		Event event)  
	{
    	Optional<Event> optionalEvent = eventRepository.findById(event.getId());
    	Event tempEvent = optionalEvent.get();
    	
    	if(tempEvent.getCreator().getId() != user.getId())
    		return;
    	else {
    	tempEvent.setDescription(event.getDescription());
    	tempEvent.setName(event.getName());
    	tempEvent.setDate(event.getDate());
    	tempEvent.setTime(event.getTime());
    	tempEvent.setLng(event.getLng());
    	tempEvent.setLat(event.getLat());
    		
		eventRepository.save(tempEvent);
    	}
	}
}