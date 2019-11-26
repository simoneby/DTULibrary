package com.controllers;

import java.util.*;

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

    // TODO: get/filter survey by date (to display only current surveys)

    @GetMapping(value="/active")
    public @ResponseBody Set<Survey> activeSurvey(){
        java.util.Date current = new java.util.Date();
        Date today = new Date(current.getTime());
        Set<Survey> all_surveys = surveyRepository.findByIdGreaterThanEqual(1);
        Set<Survey> active_surveys = new HashSet<Survey>();
        for (Survey survey:all_surveys){
            if (survey.getStartDate().before(today) && survey.getEndDate().after(today)){
                active_surveys.add(survey);
            }

        }
        return active_surveys;

    }

    @GetMapping(value="/current")
    public @ResponseBody Survey currentSurvey(@RequestParam int survey_id){
        return surveyRepository.findById(survey_id);

    }

    @GetMapping(value="/current_test")
    public @ResponseBody Survey currentSurvey(){
        int survey_id = 30;
        return surveyRepository.findById(survey_id);

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


        survey.setStartDate(new Date(1574238634000L)); // 2019-11-20
        survey.setEndDate(new Date(1575102634000L));   // 2019-11-30

        Question question1 = new Question();
        question1.setText("How shit is it?");
        questionRepository.save(question1);


        Question question2 = new Question();
        question2.setText("Which one is the shittiest?");
        questionRepository.save(question2);


        Set<Question> questionSet = new HashSet<Question>();
        questionSet.add(question1);
        questionSet.add(question2);

        survey.setQuestions(questionSet);
        surveyRepository.save(survey);
        return survey;
    }

    // TODO: Save surveyAnswers (and QuestionAnswers) (DONE?)

    @PostMapping(value = "/answer/save", headers = "Accept='application/json'")
    public String saveAnswer(@SessionAttribute("user") User currentUser, @RequestBody SurveyAnswer surveyAnswer, @RequestBody Survey currentSurvey) {

        if (userRepository.findUserByStudentnr(currentUser.getStudentnr()) != null) {

            Date today = new Date(0);
            surveyAnswer.setDate(today);
            surveyAnswer.setUser(currentUser);
            surveyAnswer.setSurvey(currentSurvey);

            surveyAnswerRepository.save(surveyAnswer);
            return ReturnMessageHelper.getReturnMessage("survey answers saved!");
        }
        else {
            return ReturnMessageHelper.getReturnMessage("You need to log in to answer a survey!");
        }
    }




    // TODO: get/filter survey by creator (to display to the user)


    @GetMapping(value="/my_surveys")
    public @ResponseBody Set<Survey> getMySurvey(@SessionAttribute("user") User currentUser){
        Set<Survey> surveys = surveyRepository.findByCreator(currentUser);
        return surveys;
    }
    // THIS METHOD OLY WORKS WHEN USERS HAVE A UNIQUE STUDENTNR IN THE DATABASE
    @GetMapping(value="/my_surveys_test")
    public @ResponseBody Set<Survey> getMySurvey(){
        User user = userRepository.findUserByStudentnr("s191772");
        Set<Survey> surveys = surveyRepository.findByCreator(user);
        return surveys;
    }





    @GetMapping(path="/test2")
    public @ResponseBody SurveyAnswer SurveyAnswerTest() {

        SurveyAnswer surveyAnswer = new SurveyAnswer();

        User user = new User();
        user.setStudentnr("s172637");
        user.setName("Frank");
        user.setEmail("s172637");
        userRepository.save(user);


        surveyAnswer.setUser(user);


        QuestionAnswer questionAnswer1 = new QuestionAnswer();
        questionAnswer1.setText("Very shit");
        questionAnswerRepository.save(questionAnswer1);


        QuestionAnswer questionAnswer2 = new QuestionAnswer();
        questionAnswer2.setText("americano");
        questionAnswerRepository.save(questionAnswer2);


        Set<QuestionAnswer> questionAnswerSet = new HashSet<>();
        questionAnswerSet.add(questionAnswer1);
        questionAnswerSet.add(questionAnswer2);

        surveyAnswer.setQuestionAnswers(questionAnswerSet);
        surveyAnswerRepository.save(surveyAnswer);
        return surveyAnswer;
    }



}