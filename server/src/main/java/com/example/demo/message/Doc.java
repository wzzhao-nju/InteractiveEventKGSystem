package com.example.demo.message;

public class Doc {

    private String id;

    private String title;

    private String time;

    public Doc() {}

    public Doc(String id, String title, String time){
        this.id = id;
        this.title = title;
        this.time = time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }
}
