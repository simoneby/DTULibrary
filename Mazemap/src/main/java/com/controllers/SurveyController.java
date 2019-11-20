package com.controllers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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

    @Autowired
    private QuestionRepository questionRepository;


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

    @GetMapping(path="/test")
    public @ResponseBody Survey createSurveyTest() {

        Survey survey = new Survey();

        User user = new User();
        user.setStudentnr("s191772");
        user.setName("Simone");
        user.setEmail("");
        userRepository.save(user);


        survey.setCreator(user);
        survey.setName("How crappy is the coffee in 303?");
        survey.setDescription("About the coffee in 303");
        survey.setCreator(user);

        Question question1 = new Question();
        question1.setText("How shit is it?");
        //question1.setSurvey(survey);
        questionRepository.save(question1);


        Question question2 = new Question();
        question2.setText("Which one is the shittiest?");
        //question2.setSurvey(survey);
        questionRepository.save(question2);


        Set<Question> questionSet = new HashSet<Question>();
        questionSet.add(question1);
        questionSet.add(question2);

        survey.setQuestions(questionSet);
        surveyRepository.save(survey);
        return survey;
    }



}