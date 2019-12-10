package com.example.demo;

import com.models.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

public class SurveyUnitTests {

    /* TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS */

    // THIS METHOD OLY WORKS WHEN USERS HAVE A UNIQUE STUDENTNR IN THE DATABASE
    @GetMapping(value="/my_surveys_test")
    public @ResponseBody
    Set<Survey> getMySurvey(){
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
    public @ResponseBody
    SurveyAnswer SurveyAnswerTest(@RequestParam int survey_id) {


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
}
