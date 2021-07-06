package com.example.demo.message;

public class Color {

    private String color;

    private String highlight;

    private String hover;

    public Color() {}

    public Color(String color){
        this.color = color;
        this.highlight = color;
        this.hover = color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHover(String hover) {
        this.hover = hover;
    }

    public String getHover() {
        return hover;
    }
}
