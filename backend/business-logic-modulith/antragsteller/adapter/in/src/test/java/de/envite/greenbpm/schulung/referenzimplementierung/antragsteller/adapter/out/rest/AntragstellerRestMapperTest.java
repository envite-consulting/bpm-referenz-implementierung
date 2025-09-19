package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest;

import static org.assertj.core.api.Assertions.assertThat;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.*;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AntragstellerRestMapperTest {

  private final AntragstellerRestMapper classUnderTest =
      Mappers.getMapper(AntragstellerRestMapper.class);

  @Test
  void should_map_all_fields_to_resource() {
    final Antragsteller antragsteller =
        new Antragsteller(
            new AntragstellerId(UUID.randomUUID().toString()),
            new Vorname("Test"),
            new Nachname("Name"),
            new Abteilung("Abteilung"));

    AntragstellerResource result = classUnderTest.toResource(antragsteller);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.id()).isEqualTo(antragsteller.getAntragstellerId().getValue());
    softAssertions.assertThat(result.vorname()).isEqualTo(antragsteller.getVorname().getValue());
    softAssertions.assertThat(result.nachname()).isEqualTo(antragsteller.getNachname().getValue());
    softAssertions
        .assertThat(result.abteilung())
        .isEqualTo(antragsteller.getAbteilung().getValue());
    softAssertions.assertAll();
  }

  @Test
  void should_return_null_when_source_is_null() {

    AntragstellerResource result = classUnderTest.toResource(null);

    assertThat(result).isNull();
  }
}
