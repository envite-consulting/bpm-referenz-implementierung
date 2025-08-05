package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class VornameTest {

  @Test
  void should_create_valid_vorname() {

    String validVorname = "Vorname";
    Vorname vorname = new Vorname(validVorname);

    assertThat(vorname.getValue()).isEqualTo(validVorname);
  }

  @Test
  void should_throw_exception_if_vorname_is_null() {

    assertThatThrownBy(() -> new Vorname(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_vorname_has_invalid_length() {

    String invalidVorname = "A";

    assertThatThrownBy(() -> new Vorname(invalidVorname)).isInstanceOf(InvariantException.class);
  }
}
