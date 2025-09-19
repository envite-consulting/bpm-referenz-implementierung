package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest;

import static org.assertj.core.api.Assertions.assertThat;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.*;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class FahrzeugRestMapperTest {

  private final FahrzeugRestMapper classUnderTest = Mappers.getMapper(FahrzeugRestMapper.class);

  @Test
  void should_map_all_fields_to_resource() {
    final Fahrzeug fahrzeug =
        new Fahrzeug(new Hersteller("Test Hersteller"), new Modell("Test Model"), new Jahr(1990));
    fahrzeug.setFahrzeugId(new FahrzeugId(UUID.randomUUID().toString()));

    FahrzeugResource result = classUnderTest.toResource(fahrzeug);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.id()).isEqualTo(fahrzeug.getFahrzeugId().getValue());
    softAssertions.assertThat(result.jahr()).isEqualTo(fahrzeug.getJahr().getValue());
    softAssertions.assertThat(result.hersteller()).isEqualTo(fahrzeug.getHersteller().getValue());
    softAssertions.assertThat(result.modell()).isEqualTo(fahrzeug.getModell().getValue());
    softAssertions.assertAll();
  }

  @Test
  void should_return_null_when_source_is_null() {

    FahrzeugResource result = classUnderTest.toResource(null);

    assertThat(result).isNull();
  }
}
