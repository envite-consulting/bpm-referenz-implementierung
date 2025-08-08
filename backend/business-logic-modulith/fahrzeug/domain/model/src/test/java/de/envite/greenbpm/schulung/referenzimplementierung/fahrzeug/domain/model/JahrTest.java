package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class JahrTest {

  @Test
  void should_create_valid_jahr() {

    Integer validJahr = 10;
    Jahr jahr = new Jahr(validJahr);

    assertThat(jahr.getValue()).isEqualTo(validJahr);
  }

  @Test
  void should_throw_exception_if_jahr_is_null() {

    assertThatThrownBy(() -> new Jahr(null)).isInstanceOf(InvariantException.class);
  }
}
