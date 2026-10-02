package com.example.th02;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String time;
    private String note;
    private boolean isCompleted;

    public Task(String title, String time, String note) {
        this.title = title;
        this.time = time;
        this.note = note;
        this.isCompleted = false;
    }

    public String getTitle() { return title; }
    public String getTime() { return time; }
    public String getNote() { return note; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}