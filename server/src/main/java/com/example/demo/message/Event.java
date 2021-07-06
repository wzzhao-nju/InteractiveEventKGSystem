package com.example.demo.message;

import java.util.Map;

public class Event {

    private Long id;

    private String content;

    private String start;

    private String end;

    private Map<String, Object> properties;

    public Event() {}

    public Event(Long id, String content, String start, String end, Map<String, Object> properties){
        this.id = id;
        this.content = content;
        this.start = start;
        this.end = end;
        this.properties = properties;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStart() {
        return start;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEnd() {
        return end;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
