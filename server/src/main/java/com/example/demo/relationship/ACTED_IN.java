package com.example.demo.relationship;

import com.example.demo.node.Movie;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@RelationshipProperties
public class ACTED_IN {
    @Id
    @GeneratedValue
    private Long id;

    @Property("roles")
    private List<String> roles;

    @TargetNode
    private Movie movie;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Movie getMovie() {
        return movie;
    }
}
