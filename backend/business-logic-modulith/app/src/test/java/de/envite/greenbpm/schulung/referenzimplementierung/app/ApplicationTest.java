package de.envite.greenbpm.schulung.referenzimplementierung.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class ApplicationTest {

  @Test
  void contextLoads() {
    // empty
  }
}
