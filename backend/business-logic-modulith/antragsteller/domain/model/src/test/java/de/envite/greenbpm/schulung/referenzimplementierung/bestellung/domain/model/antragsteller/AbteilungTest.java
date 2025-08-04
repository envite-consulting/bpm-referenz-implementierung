package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class AbteilungTest {

  @Test
  void should_create_valid_abteilung() {

    String validAbteilung = "Test Abteilung";
    Abteilung abteilung = new Abteilung(validAbteilung);

    assertThat(abteilung.getValue()).isEqualTo(validAbteilung);
  }

  @Test
  void should_throw_exception_if_abteilung_is_null() {

    assertThatThrownBy(() -> new Abteilung(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_abteilung_has_invalid_length() {

    String invalidAbteilung = "A";

    assertThatThrownBy(() -> new Abteilung(invalidAbteilung))
        .isInstanceOf(InvariantException.class);
  }
}
