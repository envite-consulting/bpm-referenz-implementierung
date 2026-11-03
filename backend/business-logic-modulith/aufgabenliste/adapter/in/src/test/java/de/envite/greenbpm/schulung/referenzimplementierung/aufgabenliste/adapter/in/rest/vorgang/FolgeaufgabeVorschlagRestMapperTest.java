package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import static org.assertj.core.api.Assertions.assertThat;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.FolgeaufgabeVorschlag;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class FolgeaufgabeVorschlagRestMapperTest {

  private final FolgeaufgabeVorschlagRestMapper classUnderTest =
      Mappers.getMapper(FolgeaufgabeVorschlagRestMapper.class);

  @Test
  void should_map_all_fields_to_resource() {

    FolgeaufgabeVorschlagResource result =
        classUnderTest.toResource(new FolgeaufgabeVorschlag("ID1", "Vorgang1", "FAHRZEUG"));

    assertThat(result).isEqualTo(new FolgeaufgabeVorschlagResource("ID1", "Vorgang1", "FAHRZEUG"));
  }

  @Test
  void should_return_null_when_source_is_null() {

    assertThat(classUnderTest.toResource(null)).isNull();
  }
}
