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
            Set<Question> questions = survey.getQuestions(); 
            survey.setQuestions(null);
            surveyRepository.save(survey);
            for (Question question : questions) {
                question.setSurvey(survey);
                questionRepository.save(question);
            }
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
        Survey survey = surveyRepository.findById(survey_id);
        return survey;

    }


    // TODO: Save surveyAnswers (and QuestionAnswers) (DONE)

    @PostMapping(value = "/answer/save", headers = "Accept='application/json'")
    public String saveAnswer(@SessionAttribute("user") User currentUser, @RequestBody SurveyAnswer surveyAnswer, @RequestBody Survey currentSurvey) {

        if (userRepository.findUserByStudentnr(currentUser.getStudentnr()) != null) {

            Date today = new Date(0);
            surveyAnswer.setDate(today);
            surveyAnswer.setUser(currentUser);
            surveyAnswer.setSurvey(currentSurvey);
            // surveyAnswerRepository.save(surveyAnswer);

            Set<Question> questions = currentSurvey.getQuestions();
            Set<QuestionAnswer> questionAnswerSet = surveyAnswer.getQuestionAnswers();
            
            for (Question q : questions)
            {
                for (QuestionAnswer qa : questionAnswerSet)
                {
                    if (q.getNumber() == qa.getNumber())
                    {
                        qa.setQuestion(q);
                        qa.setQuestionType(q.getType());
                        questionAnswerRepository.save(qa);
                        break;
                    }
                }    
            }

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


    // TODO: get results from a survey, bundle question answers together

    @GetMapping(value="/question_answers")
    public @ResponseBody Set<QuestionAnswer> getQuestionAnswers(@RequestParam int question_id){
        return questionAnswerRepository.findByQuestionId(question_id);
    }


   /* TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS */

    // THIS METHOD OLY WORKS WHEN USERS HAVE A UNIQUE STUDENTNR IN THE DATABASE
    @GetMapping(value="/my_surveys_test")
    public @ResponseBody Set<Survey> getMySurvey(){
        User user = userRepository.findUserByStudentnr("s191772");
        Set<Survey> surveys = surveyRepository.findByCreator(user);
        return surveys;
    }

    @GetMapping(path="/test")
    public @ResponseBody Survey createSurveyTest() {

        Survey survey = new Survey();

        User user = userRepository.findUserByStudentnr("s2");


        survey.setCreator(user);
        survey.setName("Course evaluation: 02762 (Birdwatching)");
        survey.setDescription("How do you feel about these courses");
        survey.setCreator(user);


        survey.setStartDate(new Date(1574238634000L)); // 2019-11-20
        survey.setEndDate(new Date(1575102634000L));   // 2019-11-30

        Question question1 = new Question();
        question1.setText("What is your favourite bird?");
        question1.setNumber(1);
        question1.setType(QuestionType.TEXT);
        questionRepository.save(question1);


        Question question2 = new Question();
        question2.setText("How many birds did you see?");
        question2.setNumber(2);
        question2.setType(QuestionType.TEXT);
        questionRepository.save(question2);

        Question question3 = new Question();
        question3.setText("Did you ever get pooped on");
        question3.setNumber(3);
        question3.setType(QuestionType.TEXT);
        questionRepository.save(question3);


        Set<Question> questionSet = new HashSet<Question>();
        questionSet.add(question1);
        questionSet.add(question2);
        questionSet.add(question3);

        survey.setQuestions(questionSet);
        surveyRepository.save(survey);
        return survey;
    }

    @GetMapping(path="/test2")
    public @ResponseBody SurveyAnswer SurveyAnswerTest(@RequestParam int survey_id) {


        User user = userRepository.findById(1);
        Survey survey = surveyRepository.findById(survey_id);

        java.util.Date current = new java.util.Date();
        Date today = new Date(current.getTime());


        SurveyAnswer surveyAnswer = new SurveyAnswer();
        surveyAnswer.setUser(user);
        surveyAnswer.setDate(today);
        surveyAnswer.setSurvey(survey);

        QuestionAnswer questionAnswer1 = new QuestionAnswer();
        questionAnswer1.setText("Swan");
        questionAnswer1.setNumber(1);
        QuestionAnswer questionAnswer2 = new QuestionAnswer();
        questionAnswer2.setText("22");
        questionAnswer2.setNumber(2);
        QuestionAnswer questionAnswer3 = new QuestionAnswer();
        questionAnswer3.setText("twice");
        questionAnswer3.setNumber(3);

        Set<QuestionAnswer> questionAnswerSet = new HashSet<>();
        questionAnswerSet.add(questionAnswer1);
        questionAnswerSet.add(questionAnswer2);
        questionAnswerSet.add(questionAnswer3);

        Set<Question> questions = survey.getQuestions();

        for (Question q : questions) {
            for (QuestionAnswer qa : questionAnswerSet) {
                if (q.getNumber() == qa.getNumber()) {
                    qa.setQuestion(q);
                    questionAnswerRepository.save(qa);
                    break;
                    }
                }
            }

        questionAnswerRepository.save(questionAnswer1);
        questionAnswerRepository.save(questionAnswer2);
        questionAnswerRepository.save(questionAnswer3);
        surveyAnswer.setQuestionAnswers(questionAnswerSet);
        surveyAnswerRepository.save(surveyAnswer);

        return surveyAnswer;
    }

    @GetMapping(path="/test3")
    public @ResponseBody SurveyAnswer SurveyAnswerTestAgain(@RequestParam int survey_id) {


        User user = userRepository.findById(3);
        Survey survey = surveyRepository.findById(survey_id);

        java.util.Date current = new java.util.Date();
        Date today = new Date(current.getTime());


        SurveyAnswer surveyAnswer = new SurveyAnswer();
        surveyAnswer.setUser(user);
        surveyAnswer.setDate(today);
        surveyAnswer.setSurvey(survey);

        QuestionAnswer questionAnswer1 = new QuestionAnswer();
        questionAnswer1.setText("Ravens");
        questionAnswer1.setNumber(1);
        QuestionAnswer questionAnswer2 = new QuestionAnswer();
        questionAnswer2.setText("261");
        questionAnswer2.setNumber(2);
        QuestionAnswer questionAnswer3 = new QuestionAnswer();
        questionAnswer3.setText("never!");
        questionAnswer3.setNumber(3);

        Set<QuestionAnswer> questionAnswerSet = new HashSet<>();
        questionAnswerSet.add(questionAnswer1);
        questionAnswerSet.add(questionAnswer2);
        questionAnswerSet.add(questionAnswer3);

        Set<Question> questions = survey.getQuestions();

        for (Question q : questions) {
            for (QuestionAnswer qa : questionAnswerSet) {
                if (q.getNumber() == qa.getNumber()) {
                    qa.setQuestion(q);
                    questionAnswerRepository.save(qa);
                    break;
                }
            }
        }

        questionAnswerRepository.save(questionAnswer1);
        questionAnswerRepository.save(questionAnswer2);
        questionAnswerRepository.save(questionAnswer3);
        surveyAnswer.setQuestionAnswers(questionAnswerSet);
        surveyAnswerRepository.save(surveyAnswer);

        return surveyAnswer;
    }



}