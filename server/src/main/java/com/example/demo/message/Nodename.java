package com.example.demo.message;

public class Nodename {
    private String value;

    private String type;

    public Nodename(String value, String type){
        this.value = value;
        this.type = type;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
