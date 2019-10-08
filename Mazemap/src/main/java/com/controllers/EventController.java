package com.controllers;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class EventController 
{
    @RequestMapping({"/otherevent"})
    public String event(@RequestParam(value="name", required=false, defaultValue="World") String name) 
    {
        return "some evnt stuff";
    }
}