package com.example.demo.relationship;

import com.example.demo.node.Movie;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public class REVIEWED {
    @Id
    @GeneratedValue
    private Long id;

    @Property("rating")
    private Integer rating;

    @Property("summary")
    private String summary;

    @TargetNode
    private Movie movie;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
}
