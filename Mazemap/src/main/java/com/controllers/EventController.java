package com.controllers;
import com.models.*;
//import java.util.concurrent.atomic.AtomicLong;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.sql.Date;

@RestController
@RequestMapping(path = "/events")
public class EventController 
{
	@Autowired
	private EventRepository eventRepository;
	
	
	
	@RequestMapping(value = "/eventdata", method = RequestMethod.GET)
	  public ArrayList<Event> getEventData(
	      @RequestParam(name = "userId", required = false, defaultValue = "0") short level) {
	    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
		ArrayList<Event> events = new ArrayList<Event>();
		eventRepository.findAll().forEach(events::add);
	    
	    	    return events;
	  }

	
	@RequestMapping(value = "/createevent", method = RequestMethod.POST)
	  public void createEvent(
			  @RequestParam(value = "creatorId") int creatorId, 
			  @RequestParam(value = "eventDescr") String description,
			  @RequestParam(value = "date") String date,
	  		  @RequestParam(value = "hour") int hour,
			  @RequestParam(value = "minute") int minute,
			  @RequestParam(value = "lng") double lng,
			  @RequestParam(value = "lat") double lat)
		{
	    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
		java.sql.Date tempDate = java.sql.Date.valueOf(date);
		long longDate = tempDate.getTime();
		tempDate.setTime(longDate + 4320000);
		Event event = new Event();
		 /*event.setCreator(creator);*/ event.setDescription(description); event.setLng(lng); event.setLat(lat); 
				 event.setDate(tempDate); event.setTime(new java.sql.Time(hour, minute, 0));
		eventRepository.save(event);
	    
	    	    
	
	
	}
			  @RequestMapping(value = "/createeventtest", method = RequestMethod.GET)
			  public String createEventTest()
					  
				{
			    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
				Event event = new Event();
				java.sql.Date date = java.sql.Date.valueOf("2019-11-30");
				long tempDate = date.getTime();
				date.setTime(tempDate + 4320000);
				/*event.setCreator(512);*/ 
				event.setDescription("testdescription"); event.setLng(12.525362); event.setLat(55.786078); 
						 event.setDate(date);  event.setTime(new java.sql.Time(10, 20, 0));
				eventRepository.save(event);
			    
			    	    return "got it";
			
			
			}
			  
			  @RequestMapping(value = "/updateevent", method = RequestMethod.GET)
			  public String updateEvent()
					  
				{
			    // ArrayList<SensorData> sensorData = new ArrayList<SensorData>();
				Long tempId = (long) 3;
				Optional<Event> event = eventRepository.findById(3);
				
				java.sql.Date date = java.sql.Date.valueOf("2019-11-28");
				long tempDate = date.getTime();
				date.setTime(tempDate + 4320000);
				event.get().setDate(date);
				
				/*event.get().setCreator(2);*/
				
				eventRepository.save(event.get());
			    
			    	    return "got it";
			
			
			}
			  
    @RequestMapping({"/otherevent"})
    public String event(@RequestParam(value="name", required=false, defaultValue="World") String name) 
    {
        return "some evnt stuff";
    }
}