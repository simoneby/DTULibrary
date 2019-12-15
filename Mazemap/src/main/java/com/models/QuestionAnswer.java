// Relates to one Question in a specific survey
package com.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

//@Author s154666, s192671, s191772
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler","surveyAnswer"})
@Entity
public class QuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer number; // what number in the survey.
    private QuestionType questionType;
    private String text;
    private Integer range_answer;
    private String text_answer;
    @Transient
    private String user_studentnr;
    
    public Integer getId() {
        return id;
    }


    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    public void setQuestion(Question question) {
        this.question = question;
    }


    public Question getQuestion() {
        return question;
    }


    @ManyToOne
    @JoinColumn(name = "surveyAnswer_id")
    private SurveyAnswer surveyAnswer;

    public SurveyAnswer getSurveyAnswer() {
        return surveyAnswer;
    }

    public void setSurveyAnswer(SurveyAnswer surveyAnswer) {
        this.surveyAnswer = surveyAnswer;
        user_studentnr = this.surveyAnswer.getUser().getStudentnr();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRange_answer() {
        return range_answer;
    }

    public void setRange_answer(Integer range_answer) {
        this.range_answer = range_answer;
    }

    public String getText_answer() {
        return text_answer;
    }

    public void setText_answer(String text_answer) {
        this.text_answer = text_answer;
    }

    public QuestionAnswer(Question question) {
        this.question = question;
        this.number = question.getNumber();
    }

    public QuestionAnswer()
    {
        
    }

    public String getUser_studentnr() {
        if(this.surveyAnswer != null && this.surveyAnswer.getUser() != null)
        user_studentnr = this.surveyAnswer.getUser().getStudentnr();
        else user_studentnr = "NA";
        return user_studentnr;
    }

}
