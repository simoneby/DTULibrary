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
// @ContextConfiguration(
// classes = {FilteredUserRepository.class,FriendListService.class})
@TestPropertySource("/application.properties")
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
    public void dummyTest(){
        Set<User> users = userRepository.findByIdGreaterThanEqual(1);
        Assert.assertNotSame(0, users.size());
        User surveyUser = users.iterator().next();
        Assert.assertFalse(surveyUser == null);

        String x = surveyService.testShit(surveyUser);
        Assert.assertSame("ok",x);
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

    }

    /*@Test
    public void testAnswerSurvey(){
        Set<SurveyAnswer> all_answers = surveyAnswerRepository.findByIdGreaterThanEqual(1);
        int old_answers = all_answers.size();

        Survey new_survey = createSurvey();

        SurveyAnswer survey_answer = new SurveyAnswer();

        Set<QuestionAnswer> questionAnswers = new HashSet<>();
        QuestionAnswer questionAnswer = new QuestionAnswer();
        questionAnswer.setNumber(1);
        questionAnswer.setText("this is the answer");
        questionAnswers.add(questionAnswer);

        QuestionAnswer questionAnswer2 = new QuestionAnswer();
        questionAnswer2.setNumber(2);
        questionAnswer2.setText("this 2nd is the answer");
        questionAnswers.add(questionAnswer2);


        survey_answer.setQuestionAnswers(questionAnswers);

        Assert.assertNotNull(survey_answer);
        Assert.assertNotNull(new_survey);

        surveyService.save(getAnyUser(), new_survey);
        Assert.assertNotNull(surveyRepository.findByName("new survey"));
        surveyService.saveAnswer(getAnyUser(), survey_answer,surveyRepository.findByName("new survey"));

        all_answers = surveyAnswerRepository.findByIdGreaterThanEqual(1);
        int new_answers = all_answers.size();

        Assert.assertEquals(old_answers + 1, new_answers);



    }
*/
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





/*


    */
/* TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS TESTS *//*


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
*/
