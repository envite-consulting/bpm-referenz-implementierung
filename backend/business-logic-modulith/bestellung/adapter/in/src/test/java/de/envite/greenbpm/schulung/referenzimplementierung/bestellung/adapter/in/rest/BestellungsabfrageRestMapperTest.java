package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class BestellungsabfrageRestMapperTest {

  private final BestellungsabfrageRestMapper classUnderTest = Mappers.getMapper(BestellungsabfrageRestMapper.class);

    @Test
    void should_map_all_fields_to_resource() {
      Bestellung bestellung =
          new Bestellung(
              new BestellungId("088a8827-e960-4696-9f6e-904691b1d10b"),
              new Antragstellerreferenz("c30ce400-1244-4056-9954-ee10ce676e70"),
              new Fahrzeugreferenz("37a9605e-56f1-40dc-a733-7ad5cb8b2b66"),
              new Bestelldatum(LocalDateTime.of(2023, 5, 20, 0, 0)),
              Status.ANGELEGT);

      BestellungsabfrageResource result = classUnderTest.toResource(bestellung);

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
