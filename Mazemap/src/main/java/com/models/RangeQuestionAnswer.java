package com.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class RangeQuestionAnswer extends QuestionAnswer{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer start;
    private Integer end;

    private Integer userInput; // what the user answered

    public Integer getUserInput() {
        return userInput;
    }

    public void setUserInput(Integer userInput) {
        this.userInput = userInput;
    }

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


}
