package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.github.domainprimitives.validation.InvariantException;
import org.junit.jupiter.api.Test;

class AntragstellerIdTest {

  @Test
  void should_create_valid_antragstellerId() {

    String validId = "0e871523-0a8b-40b7-acbf-2a6d17902866";
    AntragstellerId antragstellerId = new AntragstellerId(validId);

    assertThat(antragstellerId.getValue()).isEqualTo(validId);
  }

  @Test
  void should_throw_exception_if_id_is_null() {

    assertThatThrownBy(() -> new AntragstellerId(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_id_is_invalid_uuid() {

    String invalidId = "invalid";

    assertThatThrownBy(() -> new AntragstellerId(invalidId)).isInstanceOf(InvariantException.class);
  }
}
