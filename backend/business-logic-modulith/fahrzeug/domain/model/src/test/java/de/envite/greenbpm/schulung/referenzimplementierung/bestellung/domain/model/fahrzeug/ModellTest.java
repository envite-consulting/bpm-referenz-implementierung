package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class ModellTest {

  @Test
  void should_create_valid_modell() {

    String validModell = "Modell";
    Modell modell = new Modell(validModell);

    assertThat(modell.getValue()).isEqualTo(validModell);
  }

  @Test
  void should_throw_exception_if_modell_is_null() {

    assertThatThrownBy(() -> new Modell(null)).isInstanceOf(InvariantException.class);
  }
}
