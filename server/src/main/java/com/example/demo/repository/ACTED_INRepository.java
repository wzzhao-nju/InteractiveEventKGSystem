package com.example.demo.repository;

import com.example.demo.relationship.ACTED_IN;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ACTED_INRepository extends Neo4jRepository<ACTED_IN, Long> {
}
