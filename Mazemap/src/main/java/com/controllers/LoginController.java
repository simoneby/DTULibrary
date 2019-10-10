package com.controllers;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class LoginController 
{
    @RequestMapping(value="/login",method=RequestMethod.POST)
    public String login(@RequestParam(value="studnum", required=false, defaultValue="") String name) 
    {
    	
        return "Grats you've been logged in!\t" + name;
    }
}