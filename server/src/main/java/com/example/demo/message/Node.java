package com.example.demo.message;

import java.util.Map;

public class Node {
    private Long id;

    private Integer group;

    private String label;

    private Map<String, Object> properties;

    private Map<String, Integer> relation_num;

    public Node() {}

    public Node(Long id, Integer group, String label, Map<String, Object> properties, Map<String, Integer> relation_num){
        this.id = id;
        this.group = group;
        this.label = label;
        this.properties = properties;
        this.relation_num = relation_num;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public Integer getGroup() {
        return group;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setRelation_num(Map<String, Integer> relation_num) {
        this.relation_num = relation_num;
    }

    public Map<String, Integer> getRelation_num() {
        return relation_num;
    }
}
