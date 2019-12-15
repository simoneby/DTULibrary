package com.controllers;
import com.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.validation.Errors;
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

//@Author s183051, s170899
@RestController
@RequestMapping(path = "/events")
public class EventController 
{
	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;
	 
	@Autowired
	    private EventService eventService;
	
	
	@RequestMapping(value = "/eventdata", method = RequestMethod.GET)
	  public Set<Event> getEventData() 
	  {
		return eventService.getEventData();
	  }
	
	@RequestMapping(value = "/userfilteredeventdata", method = RequestMethod.GET)
	  public Set<Event> getUserFilteredEventData(@SessionAttribute("user") User user) 
	  {
		return eventService.getUserFilteredEventData(user);
	  }
	
	@RequestMapping(value = "/createevent", method = RequestMethod.POST)
	public ResponseEntity<?> createEvent(@SessionAttribute("user") User user,
            @Valid @RequestBody Event event, Errors errors) {
		
		eventService.createEvent(user, event);
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
	public String deleteEvent(@SessionAttribute("user") User user,
			@RequestParam  Integer id) {
	   return eventService.deleteEvent(user, id);
    }
			  
    @RequestMapping(value = "/updateevent", method = RequestMethod.POST)
	public void updateEvent(@SessionAttribute("user") User user, @RequestBody Event event, Integer id)  
	{
		eventService.updateEvent(user, event, id);
	}
}