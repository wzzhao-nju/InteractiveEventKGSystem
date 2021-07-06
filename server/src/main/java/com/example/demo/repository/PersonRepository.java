package com.example.demo.repository;

import com.example.demo.node.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.neo4j.driver.internal.value.RelationshipValue;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Optional<Person> findById(Long id);

    Optional<Person> findByName(String name);

    @Query("match(n) where n.name =~$name return n")
    List<Person> fuzzyFindByName(@Param("name") String name);

    @Query("match (:Person{name:$name})-[r]-() return type(r)")
    List<String> findAllEdgesType(@Param("name") String name);

    @Query("match (:Person{name:$name})-[r]-() return r")
    List<RelationshipValue> findAllEdges(@Param("name") String name);
}
