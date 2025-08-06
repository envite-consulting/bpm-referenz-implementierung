package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class BestellungIdTest {

  @Test
  void should_create_valid_bestellungId() {

    String validId = "2db8ea58-8a44-4557-b06e-2dba5dd05f17";
    BestellungId bestellungId = new BestellungId(validId);

    assertThat(bestellungId.getValue()).isEqualTo(validId);
  }

  @Test
  void should_throw_exception_if_id_is_null() {

    assertThatThrownBy(() -> new BestellungId(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_id_is_invalid_uuid() {

    String invalidId = "invalid";

    assertThatThrownBy(() -> new BestellungId(invalidId)).isInstanceOf(InvariantException.class);
  }
}
