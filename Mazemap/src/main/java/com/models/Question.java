package com.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

//@Author s154666, s192671, s191772
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "survey" })
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;

    private Integer number;

    private QuestionType type;

    @Transient
    private boolean isRange = false;

    public boolean getIsRange() {
        return type == QuestionType.RANGE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    private Integer start;
    private Integer end;
    private String start_label;
    private String end_label;

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getStart_label() {
        return start_label;
    }

    public void setStart_label(String start_label) {
        this.start_label = start_label;
    }

    public String getEnd_label() {
        return end_label;
    }

    public void setEnd_label(String end_label) {
        this.end_label = end_label;
    }

}