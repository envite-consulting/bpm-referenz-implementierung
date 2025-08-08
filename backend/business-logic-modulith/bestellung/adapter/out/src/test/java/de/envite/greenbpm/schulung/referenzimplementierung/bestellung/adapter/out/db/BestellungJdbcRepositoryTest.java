package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import org.assertj.core.api.SoftAssertions;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJdbcTest
@Import(UUIDGenerator.class)
class BestellungJdbcRepositoryTest {

  @Autowired private BestellungJdbcRepository classUnderTest;

  @Test
  void should_save_with_uuid() {
    BestellungEntity entity = new BestellungEntity();
    entity.setAntragstellerreferenz("ref-1");
    entity.setFahrzeugreferenz("ref-2");
    entity.setStatus("ANGELEGT");
    entity.setBestelldatum(LocalDateTime.MIN);

    BestellungEntity result = classUnderTest.save(entity);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.getId()).isNotNull();
    softAssertions.assertThat(result.getStatus()).isEqualTo(entity.getStatus());
    softAssertions.assertThat(result.getBestelldatum()).isEqualTo(entity.getBestelldatum());
    softAssertions.assertThat(result.getFahrzeugreferenz()).isEqualTo(entity.getFahrzeugreferenz());
    softAssertions
        .assertThat(result.getAntragstellerreferenz())
        .isEqualTo(entity.getAntragstellerreferenz());
    softAssertions.assertAll();
  }

  @Test
  void should_throw_on_save_without_fahrzeug() {
    BestellungEntity entity = new BestellungEntity();
    entity.setAntragstellerreferenz("ref-1");
    entity.setStatus("test");
    entity.setBestelldatum(LocalDateTime.MIN);

    assertThatThrownBy(() -> classUnderTest.save(entity))
        .isInstanceOf(DbActionExecutionException.class)
        .hasStackTraceContaining(JdbcSQLIntegrityConstraintViolationException.class.getSimpleName())
        .hasStackTraceContaining("FAHRZEUGREFERENZ");
  }

  @Test
  void should_throw_on_save_with_invalid_status() {
    BestellungEntity entity = new BestellungEntity();
    entity.setAntragstellerreferenz("ref-1");
    entity.setFahrzeugreferenz("ref-2");
    entity.setStatus("invalid");
    entity.setBestelldatum(LocalDateTime.MIN);

    assertThatThrownBy(() -> classUnderTest.save(entity))
        .isInstanceOf(DbActionExecutionException.class)
        .hasStackTraceContaining(JdbcSQLIntegrityConstraintViolationException.class.getSimpleName())
        .hasStackTraceContaining("CHK_BESTELLUNG_STATUS");
  }

  @Test
  void should_find_by_id_after_save() {
    BestellungEntity entity =
        saveAntragsteller("ref-1", "ref-2", LocalDateTime.of(2023, 1, 2, 0, 0), "ANGELEGT");

    BestellungEntity saved = classUnderTest.save(entity);
    Optional<BestellungEntity> result = classUnderTest.findById(saved.getId());

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result).isPresent();
    softAssertions.assertThat(result.get()).usingRecursiveComparison().isEqualTo(saved);
    softAssertions.assertAll();
  }

  private BestellungEntity saveAntragsteller(
      String fahrzeugreferenz,
      String antragstellerreferenz,
      LocalDateTime bestelldatum,
      String status) {

    BestellungEntity entity = new BestellungEntity();
    entity.setFahrzeugreferenz(fahrzeugreferenz);
    entity.setAntragstellerreferenz(antragstellerreferenz);
    entity.setBestelldatum(bestelldatum);
    entity.setStatus(status);
    return classUnderTest.save(entity);
  }
}
