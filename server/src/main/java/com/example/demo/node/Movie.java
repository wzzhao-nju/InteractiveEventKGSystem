package com.example.demo.node;

import org.springframework.data.neo4j.core.schema.*;

@Node(labels = "Movie")
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    @Property("title")
    private String title;

    @Property("released")
    private Integer released;

    @Property("tagline")
    private String tagline;

    //@Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    //private List<Person> directors;

    public Movie(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    public Integer getReleased() {
        return released;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTagline() {
        return tagline;
    }
/*
    public void setDirectors(List<Person> directors) {
        this.directors = directors;
    }

    public List<Person> getDirectors() {
        return directors;
    }*/
}
