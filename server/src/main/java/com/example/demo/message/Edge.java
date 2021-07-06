package com.example.demo.message;

import java.util.Map;

public class Edge {

    private Long id;

    private Long from;

    private Long to;

    private String label;

    private Color color;

    private Map<String, Object> properties;

    public Edge() {}

    public Edge(Long id, Long from, Long to, String label, Color color, Map<String, Object> properties){
        this.id = id;
        this.from = from;
        this.to = to;
        this.label = label;
        this.color = color;
        this.properties = properties;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    public Long getFrom() {
        return from;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Long getTo() {
        return to;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }
}
