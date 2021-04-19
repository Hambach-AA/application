package com.example.myclient.Models;

public class Free_time {
    private String start_free_time, end_free_time;

    public Free_time(String start_free_time, String end_free_time) {
        this.start_free_time = start_free_time;
        this.end_free_time = end_free_time;
    }

    public String getStart_free_time() {
        return start_free_time;
    }

    public void setStart_free_time(String start_free_time) {
        this.start_free_time = start_free_time;
    }

    public String getEnd_free_time() {
        return end_free_time;
    }

    public void setEnd_free_time(String end_free_time) {
        this.end_free_time = end_free_time;
    }
    public String get_time(){
        return start_free_time+" - "+end_free_time;
    }
}
