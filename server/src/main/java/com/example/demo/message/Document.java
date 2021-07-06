package com.example.demo.message;

public class Document {

    private Long id;

    private String content;

    private String start;

    public Document() {}

    public Document(Long id, String content, String start){
        this.id = id;
        this.content = content;
        this.start = start;
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

}
