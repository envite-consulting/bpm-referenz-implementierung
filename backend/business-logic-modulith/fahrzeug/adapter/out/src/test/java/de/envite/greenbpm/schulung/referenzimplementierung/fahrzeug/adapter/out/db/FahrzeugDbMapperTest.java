package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import java.util.UUID;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class FahrzeugDbMapperTest {

  private final FahrzeugDbMapper classUnderTest = Mappers.getMapper(FahrzeugDbMapper.class);

  @Nested
  class ToDb {

    @Test
    void should_map_all_fields() {
      final Fahrzeug fahrzeug =
          new Fahrzeug(new Hersteller("Test Hersteller"), new Modell("Test Model"), new Jahr(1990));
      fahrzeug.setFahrzeugId(new FahrzeugId(UUID.randomUUID().toString()));

      FahrzeugEntity result = classUnderTest.toEntity(fahrzeug);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.getId()).isEqualTo(fahrzeug.getFahrzeugId().getValue());
      softAssertions.assertThat(result.getJahr()).isEqualTo(fahrzeug.getJahr().getValue());
      softAssertions
          .assertThat(result.getHersteller())
          .isEqualTo(fahrzeug.getHersteller().getValue());
      softAssertions.assertThat(result.getModell()).isEqualTo(fahrzeug.getModell().getValue());
      softAssertions.assertAll();
    }

    @Test
    void should_map_all_fields_without_id() {
      final Fahrzeug fahrzeug =
          new Fahrzeug(new Hersteller("Test Hersteller"), new Modell("Test Model"), new Jahr(1990));

      FahrzeugEntity result = classUnderTest.toEntity(fahrzeug);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.getId()).isNull();
      softAssertions.assertThat(result.getJahr()).isEqualTo(fahrzeug.getJahr().getValue());
      softAssertions
          .assertThat(result.getHersteller())
          .isEqualTo(fahrzeug.getHersteller().getValue());
      softAssertions.assertThat(result.getModell()).isEqualTo(fahrzeug.getModell().getValue());
      softAssertions.assertAll();
    }
  }

  @Nested
  class ToDomain {

    @Test
    void should_map_all_fields() {
      FahrzeugEntity entity = new FahrzeugEntity();
      entity.setId(UUID.randomUUID().toString());
      entity.setModell("Test Modell");
      entity.setHersteller("Test Hersteller");
      entity.setJahr(2022);

      Fahrzeug result = classUnderTest.toDomain(entity);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.getFahrzeugId().getValue()).isEqualTo(entity.getId());
      softAssertions.assertThat(result.getModell().getValue()).isEqualTo(entity.getModell());
      softAssertions
          .assertThat(result.getHersteller().getValue())
          .isEqualTo(entity.getHersteller());
      softAssertions.assertThat(result.getJahr().getValue()).isEqualTo(entity.getJahr());
      softAssertions.assertAll();
    }
  }
}
