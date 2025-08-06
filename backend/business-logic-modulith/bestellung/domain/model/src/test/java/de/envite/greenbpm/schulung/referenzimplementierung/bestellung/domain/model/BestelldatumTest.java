package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import io.github.domainprimitives.validation.InvariantException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class BestelldatumTest {

  @Test
  void should_create_valid_bestelldatum() {
    LocalDateTime validDatum = LocalDateTime.now().minusDays(1);
    Bestelldatum bestelldatum = new Bestelldatum(validDatum);

    assertThat(bestelldatum.getValue()).isEqualTo(validDatum);
  }

  @Test
  void should_throw_exception_if_date_is_null() {

    assertThatThrownBy(() -> new Bestelldatum(null)).isInstanceOf(InvariantException.class);
  }

  @Test
  void should_throw_exception_if_date_is_in_future() {
    LocalDateTime invalidDatum = LocalDateTime.now().plusDays(1);

    assertThatThrownBy(() -> new Bestelldatum(invalidDatum)).isInstanceOf(InvariantException.class);
  }
}
