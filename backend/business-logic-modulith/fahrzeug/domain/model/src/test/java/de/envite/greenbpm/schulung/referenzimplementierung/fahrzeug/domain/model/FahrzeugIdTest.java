package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class FahrzeugIdTest {

  @Test
  void should_create_valid_fahrzeugId() {

    String validId = "ff208156-31a2-4e37-aa7b-dc018a437fd0";
    FahrzeugId fahrzeugId = new FahrzeugId(validId);

    assertThat(fahrzeugId.getValue()).isEqualTo(validId);
  }

  @Test
  void should_throw_exception_if_id_is_null() {

    assertThatThrownBy(() -> new FahrzeugId(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_id_is_invalid_uuid() {

    String invalidId = "invalid";

    assertThatThrownBy(() -> new FahrzeugId(invalidId)).isInstanceOf(InvariantException.class);
  }
}
