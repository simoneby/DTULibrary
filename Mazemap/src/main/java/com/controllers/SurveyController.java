package com.controllers;

import java.util.HashSet;
import java.util.Set;

import com.helpers.ReturnMessageHelper;
import com.models.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;



@RequestMapping("/survey")
@RestController
public class SurveyController {

    @Autowired
    private FilteredUserRepository userRepository;

    @Autowired
    private SurveyRepository surveyRepository;


    @PostMapping(value = "/save", headers = "Accept='application/json'")
    public String save(@SessionAttribute("user") User currentUser, @RequestBody Survey survey) {

        if (userRepository.findUserByStudentnr(currentUser.getStudentnr()) != null) {

            Date today = new Date(0);
            survey.setStartDate(today);

            survey.setCreator(currentUser);

            surveyRepository.save(survey);
            return ReturnMessageHelper.getReturnMessage("survey saved!");
        }
        else {
            return ReturnMessageHelper.getReturnMessage("You need to log in to create a survey!");
        }
    }

}