package com.controllers;

import java.util.*;

import com.helpers.ReturnMessageHelper;
import com.models.*;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Date;

// @Author: s191772, s154666
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
            java.util.Date current = new java.util.Date();
            Date today = new Date(current.getTime());
            survey.setStartDate(today);
            survey.setCreator(currentUser);
            Set<Question> questions = survey.getQuestions();
            survey.setQuestions(null);
            surveyRepository.save(survey);
            for (Question question : questions) {
                question.setSurvey(survey);
                questionRepository.save(question);
            }
            return ReturnMessageHelper.getReturnMessage("Survey was succesfully created!");
        } else {
            return ReturnMessageHelper.getReturnMessage("You need to log in to create a survey!");
        }
    }

    // TODO: get/filter survey by date (to display only current surveys)

    @GetMapping(value = "/active")
    public @ResponseBody Set<Survey> activeSurvey() {
        java.util.Date current = new java.util.Date();
        Date today = new Date(current.getTime());
        Set<Survey> all_surveys = surveyRepository.findByIdGreaterThanEqual(1);
        Set<Survey> active_surveys = new HashSet<Survey>();
        for (Survey survey : all_surveys) {
            if (survey.getStartDate().before(today) && survey.getEndDate().after(today)) {
                active_surveys.add(survey);
            }

        }
        return active_surveys;

    }

    @GetMapping(value = "/current/answer")
    public @ResponseBody SurveyAnswer currentSurveyAnswer(@RequestParam int survey_id) {
        Survey survey = surveyRepository.findById(survey_id);
        SurveyAnswer answer = new SurveyAnswer();
        answer.setSurvey(survey);
        Set<QuestionAnswer> question_answers = new HashSet<QuestionAnswer>();
        for (Question question : survey.getQuestions()) {
            question_answers.add(new QuestionAnswer(question));
        }
        answer.setQuestionAnswers(question_answers);
        return answer;
    }

    @GetMapping(value = "/current")
    public @ResponseBody Survey currentSurvey(@RequestParam int survey_id) {
        return surveyRepository.findById(survey_id);
    }

    // TODO: Save surveyAnswers (and QuestionAnswers) (DONE)

    @PostMapping(value = "/answer/save", headers = "Accept='application/json'")
    public String saveAnswer(@SessionAttribute("user") User currentUser, @RequestBody SurveyAnswer surveyAnswer) {

        if (userRepository.findUserByStudentnr(currentUser.getStudentnr()) != null) {

            Date today = new Date(0);
            surveyAnswer.setDate(today);
            surveyAnswer.setUser(currentUser);
            
            Set<QuestionAnswer> questionAnswerSet = surveyAnswer.getQuestionAnswers();
            surveyAnswer.setQuestionAnswers(null);

            surveyAnswerRepository.save(surveyAnswer);
            for (QuestionAnswer qa : questionAnswerSet) {
                qa.setQuestionType(qa.getQuestion().getType());
                qa.setSurveyAnswer(surveyAnswer);
                questionAnswerRepository.save(qa);
            }
            return ReturnMessageHelper.getReturnMessage("Thank you for answering the survey!");
        } else {
            return ReturnMessageHelper.getReturnMessage("You need to log in to answer a survey!");
        }
    }

    // TODO: get/filter survey by creator (to display to the user)

    @GetMapping(value = "/my_surveys")
    public @ResponseBody Set<Survey> getMySurvey(@SessionAttribute("user") User currentUser) {
        Set<Survey> surveys = surveyRepository.findByCreator(currentUser);
        return surveys;
    }

    // TODO: get results from a survey, bundle question answers together

    @GetMapping(value = "/question_answers")
    public @ResponseBody Set<QuestionAnswer> getQuestionAnswers(@RequestParam int question_id) {
        return questionAnswerRepository.findByQuestionId(question_id);
    }

}