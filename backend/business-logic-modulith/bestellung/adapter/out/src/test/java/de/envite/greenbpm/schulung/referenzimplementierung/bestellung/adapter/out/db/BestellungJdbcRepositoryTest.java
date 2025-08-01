package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJdbcTest
@Import(UUIDGenerator.class)
class BestellungJdbcRepositoryTest {

    @Autowired
    private BestellungJdbcRepository classUnderTest;

    @Test
    void should_save_with_uuid() {
        BestellungEntity entity = new BestellungEntity();
        entity.setAntragstellerId(12L);
        entity.setStatus("test");
        entity.setBestelldatum(LocalDateTime.MIN);
        entity.setFahrzeugreferenz("ref-1");

        BestellungEntity result = classUnderTest.save(entity);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getId()).isNotNull();
        softAssertions.assertThat(result.getStatus()).isEqualTo(entity.getStatus());
        softAssertions.assertThat(result.getBestelldatum()).isEqualTo(entity.getBestelldatum());
        softAssertions.assertThat(result.getFahrzeugreferenz()).isEqualTo(entity.getFahrzeugreferenz());
        softAssertions.assertAll();
    }

    @Test
    void should_throw_on_save_without_fahrzeut() {
        BestellungEntity entity = new BestellungEntity();
        entity.setAntragstellerId(12L);
        entity.setStatus("test");
        entity.setBestelldatum(LocalDateTime.MIN);

        assertThatThrownBy(() -> classUnderTest.save(entity))
                .isInstanceOf(DbActionExecutionException.class)
                .hasStackTraceContaining("NULL nicht zulässig für Feld \"FAHRZEUG_ID\"");
    }
}