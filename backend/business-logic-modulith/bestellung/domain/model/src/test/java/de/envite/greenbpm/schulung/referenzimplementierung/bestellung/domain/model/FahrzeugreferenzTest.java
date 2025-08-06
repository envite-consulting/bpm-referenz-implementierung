package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class FahrzeugreferenzTest {

  @Test
  void should_create_valid_fahrzeugreferenz() {

    String validId = "83be66a7-fa89-401d-b90a-66c3efb8e2b7";
    Fahrzeugreferenz fahrzeugreferenz = new Fahrzeugreferenz(validId);

    assertThat(fahrzeugreferenz.getValue()).isEqualTo(validId);
  }

  @Test
  void should_throw_exception_if_id_is_null() {

    assertThatThrownBy(() -> new Fahrzeugreferenz(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_id_is_invalid_uuid() {

    String invalidId = "invalid";

    assertThatThrownBy(() -> new Fahrzeugreferenz(invalidId))
        .isInstanceOf(InvariantException.class);
  }
}
