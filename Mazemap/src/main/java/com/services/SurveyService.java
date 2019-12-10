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
            return "survey answers saved!";
        }
        else {
            return "You need to log in to answer a survey!";
        }
    }
}
