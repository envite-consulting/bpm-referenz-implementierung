package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

import java.time.LocalDateTime;

@DataJdbcTest
class BestellungCrudRepositoryTest {

    @Autowired
    private BestellungCrudRepository classUnderTest;

    @Test
    void should_save_with_uuid() {
        BestellungEntity entity = new BestellungEntity();
        entity.setAntragstellerId(12L);
        entity.setStatus("test");
        entity.setBestelldatum(LocalDateTime.MIN);
        FahrzeugEntity fahrzeugEntity = new FahrzeugEntity();
        fahrzeugEntity.setJahr(2022);
        fahrzeugEntity.setHersteller("Test Hersteller");
        fahrzeugEntity.setModell("Test Modell");
        entity.setFahrzeug(fahrzeugEntity);

        BestellungEntity result = classUnderTest.save(entity);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(result.getId()).isNotNull();
        softAssertions.assertThat(result.getStatus()).isEqualTo(entity.getStatus());
        softAssertions.assertThat(result.getBestelldatum()).isEqualTo(entity.getBestelldatum());
        softAssertions.assertThat(result.getFahrzeug()).isEqualTo(entity.getFahrzeug());
        softAssertions.assertAll();
    }
}