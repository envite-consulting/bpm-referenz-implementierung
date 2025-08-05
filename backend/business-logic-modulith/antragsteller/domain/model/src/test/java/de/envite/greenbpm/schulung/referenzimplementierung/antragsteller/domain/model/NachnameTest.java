package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class NachnameTest {

  @Test
  void should_create_valid_nachname() {

    String validNachname = "Nachname";
    Nachname nachname = new Nachname(validNachname);

    assertThat(nachname.getValue()).isEqualTo(validNachname);
  }

  @Test
  void should_throw_exception_if_nachname_is_null() {

    assertThatThrownBy(() -> new Nachname(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_nachname_has_invalid_length() {

    String invalidNachname = "A";

    assertThatThrownBy(() -> new Nachname(invalidNachname)).isInstanceOf(InvariantException.class);
  }
}
