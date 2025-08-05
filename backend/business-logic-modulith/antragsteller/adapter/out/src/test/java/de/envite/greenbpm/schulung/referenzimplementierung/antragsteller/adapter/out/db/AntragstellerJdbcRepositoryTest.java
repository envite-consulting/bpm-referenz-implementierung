package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import java.util.Optional;
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

  @Test
  void should_find_by_id_after_save() {
    AntragstellerEntity entity = saveAntragsteller("Test", "Name", "Abteilung");

    AntragstellerEntity saved = classUnderTest.save(entity);
    Optional<AntragstellerEntity> result = classUnderTest.findById(saved.getId());

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result).isPresent();
    softAssertions.assertThat(result.get()).usingRecursiveComparison().isEqualTo(saved);
    softAssertions.assertAll();
  }

  @Test
  void should_find_all_after_save() {
    AntragstellerEntity entity1 = saveAntragsteller("Test1", "Name1", "Abteilung1");
    AntragstellerEntity entity2 = saveAntragsteller("Test2", "Name2", "Abteilung2");

    AntragstellerEntity saved1 = classUnderTest.save(entity1);
    AntragstellerEntity saved2 = classUnderTest.save(entity2);
    Optional<AntragstellerEntity> result1 = classUnderTest.findById(saved1.getId());
    Optional<AntragstellerEntity> result2 = classUnderTest.findById(saved2.getId());

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result1).isPresent();
    softAssertions.assertThat(result1.get()).usingRecursiveComparison().isEqualTo(saved1);
    softAssertions.assertThat(result2).isPresent();
    softAssertions.assertThat(result2.get()).usingRecursiveComparison().isEqualTo(saved2);
    softAssertions.assertAll();
  }

  private AntragstellerEntity saveAntragsteller(String vorname, String nachname, String abteilung) {
    AntragstellerEntity entity = new AntragstellerEntity();
    entity.setVorname(vorname);
    entity.setNachname(nachname);
    entity.setAbteilung(abteilung);
    return classUnderTest.save(entity);
  }
}
