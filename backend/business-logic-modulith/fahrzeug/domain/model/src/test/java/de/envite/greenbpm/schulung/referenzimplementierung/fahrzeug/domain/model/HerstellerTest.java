package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class HerstellerTest {

  @Test
  void should_create_valid_hersteller() {

    String validHersteller = "Hersteller";
    Hersteller hersteller = new Hersteller(validHersteller);

    assertThat(hersteller.getValue()).isEqualTo(validHersteller);
  }

  @Test
  void should_throw_exception_if_hersteller_is_null() {

    assertThatThrownBy(() -> new Hersteller(null)).isInstanceOf(InvariantException.class);
  }
}
