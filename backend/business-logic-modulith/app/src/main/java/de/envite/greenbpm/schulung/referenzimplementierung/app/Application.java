package de.envite.greenbpm.schulung.referenzimplementierung.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "de.envite.greenbpm.schulung.referenzimplementierung")
@EnableJdbcRepositories(basePackages = "de.envite.greenbpm.schulung.referenzimplementierung")
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }
}
