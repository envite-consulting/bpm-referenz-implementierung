package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import static org.assertj.core.api.Assertions.tuple;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJdbcTest
@ActiveProfiles("testdata")
@Import(UUIDGenerator.class)
public class AntragstellerSampleDataJdbcRepositoryTest {

  @Autowired private AntragstellerJdbcRepository classUnderTest;

  @Test
  void should_find_all_testdata() {
    AntragstellerEntity savedEntity = saveAntragsteller("Test", "Name", "Abteilung");

    List<AntragstellerEntity> result = classUnderTest.findAll();

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result).isNotEmpty();
    softAssertions
        .assertThat(result)
        .hasSize(4)
        .extracting(
            AntragstellerEntity::getId,
            AntragstellerEntity::getVorname,
            AntragstellerEntity::getNachname,
            AntragstellerEntity::getAbteilung)
        .containsExactly(
            tuple("df2ee843-6d3c-4c69-921d-86078c4fa3db", "Lisa", "Schreiber", "Marketing"),
            tuple("206d7500-42ff-42f2-a52d-52615a189690", "Jonas", "Becker", "IT-Support"),
            tuple("8503c574-8034-415a-b1c1-081f3ecaa2bc", "Aylin", "König", "Personalwesen"),
            tuple(savedEntity.getId(), "Test", "Name", "Abteilung"));
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
