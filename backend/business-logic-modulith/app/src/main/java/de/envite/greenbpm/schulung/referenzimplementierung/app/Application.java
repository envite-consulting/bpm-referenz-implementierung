package de.envite.greenbpm.schulung.referenzimplementierung.app;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.in.config.EnableDeployment;
import de.envite.greenbpm.schulung.referenzimplementierung.testcoverage.ExcludeFromJacocoGeneratedReport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "de.envite.greenbpm.schulung.referenzimplementierung")
@EnableJdbcRepositories(basePackages = "de.envite.greenbpm.schulung.referenzimplementierung")
@EnableDeployment
@ExcludeFromJacocoGeneratedReport
public class Application {

  public static void main(String... args) {
    SpringApplication.run(Application.class, args);
  }
}
