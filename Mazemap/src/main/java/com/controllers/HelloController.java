package com.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@Author Everyone
@Controller
public class HelloController 
{
    @GetMapping({"/", "/index"})
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

    @GetMapping({"/register"})
    public String register(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name)
    {
        return "register";
    }
    @GetMapping({"/friendlist"})
    public String friendlist(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name)
    {
        return "friendlist";
    }
    @GetMapping({"/save_survey"})
    public String save_survey(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name)
    {
        return "save_survey";
    }
    @GetMapping({"/complete_survey"})
    public String complete_survey(Model model, @RequestParam(value="survey_id", required=true, defaultValue="15") String survey_id)
    {
        return "complete_survey";
    }
    @GetMapping({"/view_survey_results"})
    public String view_survey_results(Model model, @RequestParam(value="survey_id", required=true, defaultValue="15") String survey_id)
    {
        return "survey_results";
    }
    @GetMapping({"/survey_main"})
    public String survey_main(Model model)
    {
        return "surveys_main";
    }
}