package com.controllers;

import java.util.*;

import com.helpers.ReturnMessageHelper;
import com.models.*;
import com.repositories.*;
import com.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;

// @Author: s191772 and s154666
@RequestMapping("/survey")
@RestController
public class SurveyController {

    @Autowired
    private FilteredUserRepository userRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    private QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    private SurveyService surveyservice;

    @PostMapping(value = "/save", headers = "Accept='application/json'")
    public String save(@SessionAttribute("user") User currentUser, @RequestBody Survey survey) {
        return ReturnMessageHelper.getReturnMessage(surveyservice.save(currentUser, survey));
    }

    @GetMapping(value = "/active")
    public @ResponseBody Set<Survey> activeSurvey() {
        return surveyservice.activeSurvey();

    }

    @GetMapping(value = "/current")
    public @ResponseBody
    Survey currentSurvey(@RequestParam int survey_id) {
        return surveyRepository.findById(survey_id);

    }

    @PostMapping(value = "/answer/save", headers = "Accept='application/json'")
    public String saveAnswer(@SessionAttribute("user") User currentUser, @RequestBody SurveyAnswer surveyAnswer) {
        return ReturnMessageHelper.getReturnMessage(surveyservice.saveAnswer(currentUser, surveyAnswer));

    }

    @GetMapping(value = "/my_surveys")
    public @ResponseBody
    Set<Survey> getMySurvey(@SessionAttribute("user") User currentUser) {
        return surveyRepository.findByCreator(currentUser);
    }

    @GetMapping(value = "/current/answer")
    public @ResponseBody SurveyAnswer currentSurveyAnswer(@RequestParam int survey_id) {
        return surveyservice.getCurrentSurveyAnswer(survey_id);
    }


    @GetMapping(value = "/question_answers")
    public @ResponseBody Set<QuestionAnswer> getQuestionAnswers(@RequestParam int question_id) {
        return questionAnswerRepository.findByQuestionId(question_id);
    }

}