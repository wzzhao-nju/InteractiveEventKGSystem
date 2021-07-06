package com.example.demo.repository;

import com.example.demo.node.Movie;
import org.neo4j.driver.internal.value.RelationshipValue;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends Neo4jRepository<Movie, Long> {
    Optional<Movie> findById(Long id);

    Optional<Movie> findByTitle(String title);

    @Query("match(n) where n.title =~$title return n")
    List<Movie> fuzzyFindByTitle(@Param("title") String title);

    @Query("match (:Movie{title:$title})-[r]-() return type(r)")
    List<String> findAllEdgesType(@Param("title") String title);

    @Query("match (:Movie{title:$title})-[r]-() return r")
    List<RelationshipValue> findAllEdges(@Param("title") String title);
}
