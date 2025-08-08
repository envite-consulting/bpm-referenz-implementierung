package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

class BestellungDbMapperTest {

  private final BestellungDbMapper classUnderTest = Mappers.getMapper(BestellungDbMapper.class);

  @Nested
  class toEntity {

    @Test
    void should_map_all_fields_to_entity_without_id() {
      Bestellung bestellung =
          new Bestellung(
              new Antragstellerreferenz("c30ce400-1244-4056-9954-ee10ce676e70"),
              new Fahrzeugreferenz("37a9605e-56f1-40dc-a733-7ad5cb8b2b66"),
              new Bestelldatum(LocalDateTime.of(2023, 5, 20, 0, 0)),
              Status.ANGELEGT);

      BestellungEntity result = classUnderTest.toEntity(bestellung);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.getId()).isNotNull();
      softAssertions
          .assertThat(result.getAntragstellerreferenz())
          .isEqualTo(bestellung.getAntragstellerreferenz().getValue());
      softAssertions
          .assertThat(result.getFahrzeugreferenz())
          .isEqualTo(bestellung.getFahrzeugreferenz().getValue());
      softAssertions
          .assertThat(result.getBestelldatum())
          .isEqualTo(bestellung.getBestelldatum().getValue());
      softAssertions.assertThat(result.getStatus()).isEqualTo(bestellung.getStatus().name());
      softAssertions.assertAll();
    }

    @Test
    void should_map_all_fields_to_entity_with_id() {
      Bestellung bestellung =
          new Bestellung(
              new BestellungId("088a8827-e960-4696-9f6e-904691b1d10b"),
              new Antragstellerreferenz("c30ce400-1244-4056-9954-ee10ce676e70"),
              new Fahrzeugreferenz("37a9605e-56f1-40dc-a733-7ad5cb8b2b66"),
              new Bestelldatum(LocalDateTime.of(2023, 5, 20, 0, 0)),
              Status.ANGELEGT);

      BestellungEntity result = classUnderTest.toEntity(bestellung);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions.assertThat(result.getId()).isEqualTo(bestellung.getBestellungId().getValue());
      softAssertions
          .assertThat(result.getAntragstellerreferenz())
          .isEqualTo(bestellung.getAntragstellerreferenz().getValue());
      softAssertions
          .assertThat(result.getFahrzeugreferenz())
          .isEqualTo(bestellung.getFahrzeugreferenz().getValue());
      softAssertions
          .assertThat(result.getBestelldatum())
          .isEqualTo(bestellung.getBestelldatum().getValue());
      softAssertions.assertThat(result.getStatus()).isEqualTo(bestellung.getStatus().name());
      softAssertions.assertAll();
    }
  }

  @Nested
  class toDomain {

    @Test
    void should_map_all_fields_to_domain_with_id() {
      BestellungEntity resourceEntity = new BestellungEntity();

      resourceEntity.setId("088a8827-e960-4696-9f6e-904691b1d10b");
      resourceEntity.setAntragstellerreferenz("c30ce400-1244-4056-9954-ee10ce676e70");
      resourceEntity.setFahrzeugreferenz("37a9605e-56f1-40dc-a733-7ad5cb8b2b66");
      resourceEntity.setBestelldatum(LocalDateTime.of(2023, 5, 20, 0, 0));
      resourceEntity.setStatus("ANGELEGT");

      Bestellung result = classUnderTest.toDomain(resourceEntity);

      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions
          .assertThat(result.getBestellungId().getValue())
          .isEqualTo(resourceEntity.getId());
      softAssertions
          .assertThat(result.getAntragstellerreferenz().getValue())
          .isEqualTo(resourceEntity.getAntragstellerreferenz());
      softAssertions
          .assertThat(result.getFahrzeugreferenz().getValue())
          .isEqualTo(resourceEntity.getFahrzeugreferenz());
      softAssertions
          .assertThat(result.getBestelldatum().getValue())
          .isEqualTo(resourceEntity.getBestelldatum());
      softAssertions.assertThat(result.getStatus().name()).isEqualTo(resourceEntity.getStatus());
      softAssertions.assertAll();
    }
  }
}
