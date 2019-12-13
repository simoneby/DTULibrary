package com.helpers;
import com.models.*;

import java.util.List;

//@Author s183051, s170899
public class AjaxResponseBody {

    String msg;
    Event event;

    //getters and setters
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
    
    public void setEvent(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
    
}