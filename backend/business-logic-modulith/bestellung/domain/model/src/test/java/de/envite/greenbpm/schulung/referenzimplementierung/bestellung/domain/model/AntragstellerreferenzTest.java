package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class AntragstellerreferenzTest {

  @Test
  void should_create_valid_antragstellerreferenz() {

    String validId = "47fe7b60-5322-4feb-8798-d28e92261733";
    Antragstellerreferenz antragstellerreferenz = new Antragstellerreferenz(validId);

    assertThat(antragstellerreferenz.getValue()).isEqualTo(validId);
  }

  @Test
  void should_throw_exception_if_id_is_null() {

    assertThatThrownBy(() -> new Antragstellerreferenz(null))
        .isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_id_is_invalid_uuid() {

    String invalidId = "invalid";

    assertThatThrownBy(() -> new Antragstellerreferenz(invalidId))
        .isInstanceOf(InvariantException.class);
  }
}
