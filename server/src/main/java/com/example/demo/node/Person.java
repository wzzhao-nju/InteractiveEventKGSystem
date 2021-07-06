package com.example.demo.node;

import org.springframework.data.neo4j.core.schema.*;

@Node(labels = "Person")
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    @Property("name")
    private String name;

    @Property("born")
    private Integer born;

    public Person(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setBorn(Integer born) {
        this.born = born;
    }

    public Integer getBorn() {
        return born;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
