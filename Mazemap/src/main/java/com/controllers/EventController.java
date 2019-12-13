package com.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.validation.Errors;
import javax.validation.Valid;
import java.util.stream.Collectors;
import com.helpers.*;
import com.helpers.ReturnMessageHelper;
import com.models.*;
import java.util.Set;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.sql.Date;

//@Author s183051, s170899
@RestController
@RequestMapping(path = "/events")
public class EventController 
{
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;	
	
	
	@RequestMapping(value = "/eventdata", method = RequestMethod.GET)
	  public Set<Event> getEventData() 
	  {
		HashSet<Event> tempEvents = new HashSet<Event>();
		eventRepository.findAll().forEach(tempEvents::add);
	    Set<Event> events = tempEvents;
	    
	    	    return events;
	  }
	
	@RequestMapping(value = "/createevent", method = RequestMethod.POST)
	public ResponseEntity<?> createEvent(
            @Valid @RequestBody Event event, Errors errors) {
		
		Optional<User> user = userRepository.findById(13);
		if(user.isPresent()) {
			User saveUser = user.get();
			event.setCreator(saveUser);
		}
		eventRepository.save(event);
        AjaxResponseBody result = new AjaxResponseBody();
        
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }
        
        result.setMsg("Event created");

        return ResponseEntity.ok(result);

    }

			  @RequestMapping(value = "/deleteevent", method = RequestMethod.DELETE)
			    public String deleteFriend(@RequestParam  Integer id) {
			        String returnMessage;
			        if (id == 0)
			            returnMessage = String.format("The event with id %s does not exist!", id);
			        else {
			        	Optional<Event> tempEvent = eventRepository.findById(id);
			        	Event event = tempEvent.get();
			        	eventRepository.deleteById(id);
			           
			                returnMessage = "Error: one of the entities is null!";
			         
			        }
			        return ReturnMessageHelper.getReturnMessage(returnMessage);
			    }
			  
			  @RequestMapping(value = "/updateevent", method = RequestMethod.POST)
			  public void updateEvent(@Valid @RequestBody Event event, Errors errors)  
				{
				  eventRepository.save(event);
			}
}