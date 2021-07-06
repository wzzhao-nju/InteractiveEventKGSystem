package com.example.demo.message;

import java.util.List;

public class DetailData {
    private List<TableData> attributes;

    private List<TableData> relations;

    public void setAttributes(List<TableData> attributes) {
        this.attributes = attributes;
    }

    public void setRelations(List<TableData> relations) {
        this.relations = relations;
    }

    public List<TableData> getAttributes() {
        return attributes;
    }

    public List<TableData> getRelations() {
        return relations;
    }
}