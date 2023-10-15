package com.example.tp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableJpaRepositories(basePackages = "com.example.tp.domain.repository")
@EnableJpaAuditing
@SpringBootApplication
public class TpApplication {

    public static void main(String[] args) {
        SpringApplication.run(TpApplication.class, args);
    }



}
