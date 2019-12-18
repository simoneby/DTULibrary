package com.services;

import com.helpers.ReturnMessageHelper;
import com.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import com.models.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;

//@Author: s191772
@Component
public class SurveyService {
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

    public String save(User user, Survey survey){

        if (userRepository.findUserByStudentnr(user.getStudentnr()) != null) {

            java.util.Date current = new java.util.Date();
            Date today = new Date(current.getTime());
            survey.setStartDate(today);
            survey.setCreator(user);
            Set<Question> questions = survey.getQuestions();
            survey.setQuestions(null);
            surveyRepository.save(survey);
            for (Question question : questions) {
                question.setSurvey(survey);
                questionRepository.save(question);
            }
            return "survey saved!";
        }
        else {
            return "You need to log in to create a survey!";
        }

    }

    public String testShit(User user)
    {
        if(userRepository== null)
        {
            return "This is not getting injected";
        }
        if(surveyRepository== null)
        {
            return "This is not getting injected";
        }

        if(surveyAnswerRepository== null)
        {
            return "This is not getting injected";
        }
        if(questionRepository== null)
        {
            return "This is not getting injected";
        }
        if(questionAnswerRepository== null)
        {
            return "This is not getting injected";
        }

        return "ok";

    }

    public Set<Survey> activeSurvey(){
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

    public String saveAnswer(User currentUser,SurveyAnswer surveyAnswer,Survey currentSurvey) {
        if (userRepository.findUserByStudentnr(currentUser.getStudentnr()) != null) {

            java.util.Date current = new java.util.Date();
            Date today = new Date(current.getTime());
            surveyAnswer.setDate(today);
            surveyAnswer.setUser(currentUser);
            surveyAnswer.setSurvey(currentSurvey);

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
            return "survey answers saved!";
        }
        else {
            return "You need to log in to answer a survey!";
        }
    }

    public SurveyAnswer getCurrentSurveyAnswer(int survey_id){
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

}
