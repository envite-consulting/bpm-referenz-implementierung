package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.aufgabe;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AufgabenabfrageRestMapperTest {

  private final AufgabenabfrageRestMapper classUnderTest =
      Mappers.getMapper(AufgabenabfrageRestMapper.class);

  @Test
  void should_map_all_fields_to_resource() {

    Aufgabe aufgabe = new Aufgabe("ID123", "My Task", "Test User 1", LocalDateTime.now(), "Ref1");

    AufgabenabfrageResource result = classUnderTest.toResource(aufgabe);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.id()).isEqualTo(aufgabe.getId());
    softAssertions.assertThat(result.name()).isEqualTo(aufgabe.getName());
    softAssertions.assertThat(result.bearbeiter()).isEqualTo(aufgabe.getBearbeiter());
    softAssertions.assertThat(result.erstelldatum()).isEqualTo(aufgabe.getErstelldatum());
    softAssertions.assertThat(result.formularreferenz()).isEqualTo(aufgabe.getFormularreferenz());
    softAssertions.assertAll();
  }
}
