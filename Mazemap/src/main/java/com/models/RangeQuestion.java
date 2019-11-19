package com.models;
public class RangeQuestion extends Question
{
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