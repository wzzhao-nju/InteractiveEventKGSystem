package com.example.demo;

import com.example.demo.controller.DataHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@ComponentScan("com.example.demo")
@EnableNeo4jRepositories("com.example.demo.repository")
@EntityScan(basePackages = {"com.example.demo.node", "com.example.demo.relationship"})
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //DataHelper dataHelper = new DataHelper();
        //dataHelper.readOriginalText();
    }

}
