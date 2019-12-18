package com.example.demo;

import com.models.*;
import com.repositories.*;
import com.services.SurveyService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;
import java.sql.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

// @Author: s191772
@SpringBootTest
@RunWith(SpringRunner.class)
@org.springframework.transaction.annotation.Transactional

@TestPropertySource("/testing.properties")
public class SurveyUnitTests {


    @Autowired
    FilteredUserRepository userRepository;
    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    QuestionAnswerRepository questionAnswerRepository;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    SurveyAnswerRepository surveyAnswerRepository;

    @Autowired
    SurveyService surveyService;


    static String student_number_format="sxxxxx%s";
    static String student_email_format="sxxxxx%s@student.dtu.dk";

    @Before
    public void initializeTest() {

        deleteCreatedUsers();
        HashMap<Integer,User> createdUsers = new HashMap<Integer,User>();
        for(int i=1;i<10;i++)
        {
            String studentNr = String.format(student_number_format,i);
            String email = String.format(student_email_format,i);
            User user = new User("Name"+i,studentNr,email);
            userRepository.save(user);
            createdUsers.put(i,user);
        }

        Date yesterday = new Date(System.currentTimeMillis()-24*60*60*1000);
        Date tomorrow = new Date(System.currentTimeMillis()+24*60*60*1000);
        Date overtomorrow = new Date(System.currentTimeMillis()+24*60*60*1000*2);
        List<Date> datelist = new ArrayList<Date>();
        datelist.add(yesterday);
        datelist.add(tomorrow);
        datelist.add(overtomorrow);

        HashMap<Integer,Survey> createdSurveys = new HashMap<Integer,Survey>();

        for(int i=1;i<2;i++)
        {
            String name = "test_survey";
            Survey survey = new Survey();
            survey.setStartDate(datelist.get(i-1));
            survey.setEndDate(datelist.get(i));
            survey.setName(name);
            surveyRepository.save(survey);
            createdSurveys.put(i,survey);
        }



    }

    //this method is executed after every test method
    //use this to cleanup / remove any data you have created in the before method or in the tests
    @After
    public void cleanupTest() {
        deleteCreatedUsers();
    }

    private void deleteCreatedUsers() {

        for(int i=1;i<10;i++)
        {
            Set<User> users = userRepository.findByIdGreaterThanEqual(1);
            userRepository.deleteAll(users);
            Set<Survey> surveys = surveyRepository.findByIdGreaterThanEqual(1);
            surveyRepository.deleteAll(surveys);
            Set<Question> questions = questionRepository.findByIdGreaterThanEqual(1);
            questionRepository.deleteAll(questions);
            Set<SurveyAnswer> surveyAnswers = surveyAnswerRepository.findByIdGreaterThanEqual(1);
            surveyAnswerRepository.deleteAll(surveyAnswers);
            Set<QuestionAnswer> questionAnswers = questionAnswerRepository.findByIdGreaterThanEqual(1);
            questionAnswerRepository.deleteAll(questionAnswers);

        }


    }

    @Test
    public void testGetAllActiveSurveys(){
        Assert.assertNotNull(surveyService);
        Set<Survey> survey = surveyService.activeSurvey();
        Assert.assertNotNull("getActiveSurvey is null", survey);
        Assert.assertTrue(!survey.isEmpty());
        Assert.assertEquals(survey.size(), 1);
    }


    @Test
    public void testSaveSurvey(){
        Set<Survey> all_surveys = surveyRepository.findByIdGreaterThanEqual(1);
        int old_surveys = all_surveys.size();

        Survey new_survey = createSurvey();

        User surveyUser = getAnyUser();
        Assert.assertFalse(surveyUser == null);

        surveyService.save(surveyUser, new_survey);

        all_surveys = surveyRepository.findByIdGreaterThanEqual(1);
        int new_surveys = all_surveys.size();
        Assert.assertEquals(old_surveys + 1, new_surveys);
        Survey found = surveyRepository.findByName("new survey");
        Assert.assertNotNull(found);
        Assert.assertTrue(questionRepository.findBySurvey(new_survey).size() > 0);

    }

    @Test
    public void testAnswerSurvey(){

        Survey new_survey = createSurvey();
        surveyService.save(getAnyUser(), new_survey);


        Set<SurveyAnswer> all_answers = surveyAnswerRepository.findByIdGreaterThanEqual(1);
        int old_answers = all_answers.size();

        SurveyAnswer survey_answer = new SurveyAnswer();
        survey_answer.setSurvey(new_survey);
        Set<QuestionAnswer> questionAnswers = new HashSet<>();

        for (Question q : questionRepository.findBySurvey(new_survey)){

            int number = q.getNumber();

            QuestionAnswer questionAnswer = new QuestionAnswer();
            questionAnswer.setNumber(number);
            questionAnswer.setText("random answer");
            questionAnswer.setQuestion(q);
            questionAnswers.add(questionAnswer);
        }

        survey_answer.setQuestionAnswers(questionAnswers);
        Assert.assertNotNull(survey_answer);
        Assert.assertNotNull(new_survey);
        Assert.assertNotNull(surveyRepository.findByName("new survey"));
        surveyService.saveAnswer(getAnyUser(), survey_answer);
        all_answers = surveyAnswerRepository.findByIdGreaterThanEqual(1);
        int new_answers = all_answers.size();
        Assert.assertEquals(old_answers + 1, new_answers);
    }

    @Test
    public void testGetCurrentSurvey(){

        Survey survey = createSurvey();
        surveyService.save(getAnyUser(), survey);

        Survey survey1 = surveyRepository.findByName("new survey");
        SurveyAnswer surveyAnswer = surveyService.getCurrentSurveyAnswer(survey1.getId());

        Assert.assertNotNull(surveyAnswer);
        Assert.assertEquals(2, surveyAnswer.getQuestionAnswers().size());

    }

    private User getAnyUser(){
        Set<User> users = userRepository.findByIdGreaterThanEqual(1);
        Assert.assertNotSame(0, users.size());
        User surveyUser = users.iterator().next();
        return surveyUser;

    }

    private Survey createSurvey(){
        Survey new_survey = new Survey();
        new_survey.setName("new survey");
        new_survey.setDescription("description new survey");

        Set<Question> questions = new HashSet<Question>();
        Question new_question = new Question();
        new_question.setType(QuestionType.TEXT);
        new_question.setText("new question?");
        new_question.setNumber(1);

        Question new_question2 = new Question();
        new_question2.setType(QuestionType.TEXT);
        new_question2.setText("2nd new question?");
        new_question2.setNumber(2);

        questions.add(new_question);
        questions.add(new_question2);

        new_survey.setQuestions(questions);
        Date tomorrow = new Date(System.currentTimeMillis()+24*60*60*1000);
        new_survey.setEndDate(tomorrow);
        return new_survey;
    }




}
