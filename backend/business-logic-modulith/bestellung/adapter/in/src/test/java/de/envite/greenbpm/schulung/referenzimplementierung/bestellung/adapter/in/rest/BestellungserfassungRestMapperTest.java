package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class BestellungserfassungRestMapperTest {

  private final BestellungserfassungRestMapper classUnderTest =
      Mappers.getMapper(BestellungserfassungRestMapper.class);

  @Test
  void should_map_all_fields_to_domain_with_id() {
    BestellungserfassungResource resource =
        new BestellungserfassungResource(
            "46710277-85ba-468f-a903-8d9164c9ea2b",
            "d195c538-4c67-4677-9324-1f5707654706",
            LocalDateTime.of(2023, 5, 20, 0, 0),
            "ANGELEGT");

    Bestellung result = classUnderTest.toDomain(resource);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.getBestellungId()).isNull();
    softAssertions
        .assertThat(result.getAntragstellerreferenz().getValue())
        .isEqualTo(resource.antragstellerreferenz());
    softAssertions
        .assertThat(result.getFahrzeugreferenz().getValue())
        .isEqualTo(resource.fahrzeugreferenz());
    softAssertions
        .assertThat(result.getBestelldatum().getValue())
        .isEqualTo(resource.bestelldatum());
    softAssertions.assertThat(result.getStatus().name()).isEqualTo(resource.status());
    softAssertions.assertAll();
  }
}
