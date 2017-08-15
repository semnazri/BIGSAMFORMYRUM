package com.sibertama.bigforum.Model.POJO;

/**
 * Created by Semmy
 * mr.shanky08@gmail.com on 7/25/16.
 *
 * @copyright 2016
 * PT.Bisnis Indonesia Sibertama
 */
public class Event {
    public String Event_id;
    public String Event_name;
    public String Event_desc;
    public String Event_date;
    public String Event_time;
    public String Event_location;
    public String Event_img;

    public Event(){

    }

    public Event(String Event_id, String Event_name, String Event_desc, String Event_date, String Event_time, String Event_location, String Event_img){
        this.Event_id = Event_id;
        this.Event_name = Event_name;
        this.Event_desc = Event_desc;
        this.Event_date = Event_date;
        this.Event_time = Event_time;
        this.Event_location = Event_location;
        this.Event_img = Event_img;
    }

    public void setEvent_id(String event_id) {
        Event_id = event_id;
    }

    public String getEvent_id() {
        return Event_id;
    }

    public void setEvent_name(String event_name) {
        Event_name = event_name;
    }

    public String getEvent_name() {
        return Event_name;
    }

    public void setEvent_desc(String event_desc) {
        Event_desc = event_desc;
    }

    public String getEvent_desc() {
        return Event_desc;
    }

    public void setEvent_date(String event_date) {
        Event_date = event_date;
    }

    public String getEvent_date() {
        return Event_date;
    }

    public void setEvent_time(String event_time) {
        Event_time = event_time;
    }

    public String getEvent_time() {
        return Event_time;
    }

    public void setEvent_location(String event_location) {
        Event_location = event_location;
    }

    public String getEvent_location() {
        return Event_location;
    }

    public void setEvent_img(String event_img) {
        Event_img = event_img;
    }

    public String getEvent_img() {
        return Event_img;
    }
}
