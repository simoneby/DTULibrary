package com.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class HelloController 
{
    @GetMapping({"/", "/index1"})
    public String index(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) 
    {
        model.addAttribute("name", name);
        return "index";
    }

    @GetMapping({"/events"})
    public String events(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name)
    {
    	return "events";
    }
}