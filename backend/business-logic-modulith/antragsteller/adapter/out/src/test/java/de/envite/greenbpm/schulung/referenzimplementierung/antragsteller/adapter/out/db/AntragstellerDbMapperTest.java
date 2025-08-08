package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

class AntragstellerDbMapperTest {

  private final AntragstellerDbMapper classUnderTest =
      Mappers.getMapper(AntragstellerDbMapper.class);

  @Nested
  class ToDb {

    @Test
    void should_map_all_fields() {
      final Antragsteller antragsteller =
          new Antragsteller(
                  new AntragstellerId("3755e2e0-4aa2-43c9-bde4-d7cd98b7f427"),
                  new Vorname("Test"),
                  new Nachname("Name"),
                  new Abteilung("Test Abteilung")
          );

      AntragstellerEntity result = classUnderTest.toEntity(antragsteller);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions
          .assertThat(result.getId())
          .isEqualTo(antragsteller.getAntragstellerId().getValue());
      softAssertions
          .assertThat(result.getVorname())
          .isEqualTo(antragsteller.getVorname().getValue());
      softAssertions
          .assertThat(result.getNachname())
          .isEqualTo(antragsteller.getNachname().getValue());
      softAssertions
          .assertThat(result.getAbteilung())
          .isEqualTo(antragsteller.getAbteilung().getValue());
      softAssertions.assertAll();
    }
  }

  @Nested
  class ToDomain {

    @Test
    void should_map_all_fields() {
      AntragstellerEntity entity = new AntragstellerEntity();
      entity.setId(UUID.randomUUID().toString());
      entity.setVorname("Test");
      entity.setNachname("Name");
      entity.setAbteilung("Test Abteilung");

      Antragsteller result = classUnderTest.toDomain(entity);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.getAntragstellerId().getValue()).isEqualTo(entity.getId());
      softAssertions.assertThat(result.getVorname().getValue()).isEqualTo(entity.getVorname());
      softAssertions.assertThat(result.getNachname().getValue()).isEqualTo(entity.getNachname());
      softAssertions.assertThat(result.getAbteilung().getValue()).isEqualTo(entity.getAbteilung());
      softAssertions.assertAll();
    }
  }
}
