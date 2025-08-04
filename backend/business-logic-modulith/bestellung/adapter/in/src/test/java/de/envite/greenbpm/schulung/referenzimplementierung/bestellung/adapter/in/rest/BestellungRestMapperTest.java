package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class BestellungRestMapperTest {

  private final BestellungRestMapper classUnderTest = Mappers.getMapper(BestellungRestMapper.class);

  @Nested
  class toResource {

    @Test
    void should_map_all_fields_to_resource() {
      Bestellung bestellung =
          new Bestellung(
              new BestellungId("088a8827-e960-4696-9f6e-904691b1d10b"),
              new Antragstellerreferenz("c30ce400-1244-4056-9954-ee10ce676e70"),
              new Fahrzeugreferenz("37a9605e-56f1-40dc-a733-7ad5cb8b2b66"),
              new Bestelldatum(LocalDateTime.of(2023, 5, 20, 0, 0)),
              Status.ANGELEGT);

      BestellungResource result = classUnderTest.toResource(bestellung);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.id()).isEqualTo(bestellung.getBestellungId().getValue());
      softAssertions
          .assertThat(result.antragstellerreferenz())
          .isEqualTo(bestellung.getAntragstellerreferenz().getValue());
      softAssertions
          .assertThat(result.fahrzeugreferenz())
          .isEqualTo(bestellung.getFahrzeugreferenz().getValue());
      softAssertions
          .assertThat(result.bestelldatum())
          .isEqualTo(bestellung.getBestelldatum().getValue());
      softAssertions.assertThat(result.status()).isEqualTo(bestellung.getStatus().name());
      softAssertions.assertAll();
    }
  }

  @Nested
  class toDomain {

    @Test
    void should_map_all_fields_to_domain_with_id() {
      BestellungResource resource =
          new BestellungResource(
              "088a8827-e960-4696-9f6e-904691b1d10b",
              "46710277-85ba-468f-a903-8d9164c9ea2b",
              "d195c538-4c67-4677-9324-1f5707654706",
              LocalDateTime.of(2023, 5, 20, 0, 0),
              "ANGELEGT");

      Bestellung result = classUnderTest.toDomain(resource);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.getBestellungId().getValue()).isEqualTo(resource.id());
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

    @Test
    void should_map_all_fields_to_domain_without_id() {
      BestellungResource resource =
          new BestellungResource(
              null,
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
}
