package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;

@DataJdbcTest
@Import(UUIDGenerator.class)
class AntragstellerJdbcRepositoryTest {

  @Autowired private AntragstellerJdbcRepository classUnderTest;

  @Test
  void should_save_with_uuid() {
    AntragstellerEntity entity = new AntragstellerEntity();
    entity.setVorname("Test");
    entity.setNachname("Name");
    entity.setAbteilung("Abteilung");

    AntragstellerEntity result = classUnderTest.save(entity);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.getId()).isNotNull();
    softAssertions.assertThat(result.getVorname()).isEqualTo(entity.getVorname());
    softAssertions.assertThat(result.getNachname()).isEqualTo(entity.getNachname());
    softAssertions.assertThat(result.getAbteilung()).isEqualTo(entity.getAbteilung());
    softAssertions.assertAll();
  }
}
