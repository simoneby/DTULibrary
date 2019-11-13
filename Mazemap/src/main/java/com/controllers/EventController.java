package com.controllers;
import com.models.*;
//import java.util.concurrent.atomic.AtomicLong;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping(path = "/events")
public class EventController 
{
	@Autowired
	private EventRepository eventRepository;
	@RequestMapping(value = "/eventdata", method = RequestMethod.GET)
	  public ArrayList<Event> getEventData(
	      @RequestParam(name = "userid", required = false, defaultValue = "0") short level) {
	    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
		ArrayList<Event> events = new ArrayList<Event>();
		eventRepository.findAll().forEach(events::add);
	    
	    	    return events;
	  }

    @RequestMapping({"/otherevent"})
    public String event(@RequestParam(value="name", required=false, defaultValue="World") String name) 
    {
        return "some evnt stuff";
    }
}