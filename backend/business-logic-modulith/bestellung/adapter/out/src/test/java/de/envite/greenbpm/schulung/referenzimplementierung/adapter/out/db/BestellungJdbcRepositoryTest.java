package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
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

    @Autowired
    private FahrzeugJdbcRepository fahrzeugJdbcRepository;

    private String createFahrzeug() {
        FahrzeugEntity entity =  new FahrzeugEntity();
        entity.setModell("Test Modell");
        entity.setHersteller("Test Hersteller");
        entity.setJahr(2022);
        return fahrzeugJdbcRepository.save(entity).getId();
    }

    @Test
    void should_save_with_uuid() {
        BestellungEntity entity = new BestellungEntity();
        entity.setAntragstellerId(12L);
        entity.setStatus("test");
        entity.setBestelldatum(LocalDateTime.MIN);
        // TODO: Use @MappedCollection in Entity to automaticall create the relation or insert some sample Fahrzeug via Liquibase
        // FahrzeugEntity fahrzeug = createFahrzeug();
        // entity.setFahrzeug(fahrzeug);

        BestellungEntity result = classUnderTest.save(entity);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getId()).isNotNull();
        softAssertions.assertThat(result.getStatus()).isEqualTo(entity.getStatus());
        softAssertions.assertThat(result.getBestelldatum()).isEqualTo(entity.getBestelldatum());
        softAssertions.assertThat(result.getFahrzeug()).isEqualTo(entity.getFahrzeug());
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