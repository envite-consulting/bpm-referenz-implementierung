package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.fahrzeug;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.Fahrzeugabfrage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("bestellungFahrzeugAdapter")
@RequiredArgsConstructor
class FahrzeugAdapter implements FahrzeugQuery {

  private final Fahrzeugabfrage fahrzeugabfrage;

  @Override
  public boolean validateExistence(Fahrzeugreferenz fahrzeugreferenz) {
    return fahrzeugabfrage.existiertFahrzeug(new FahrzeugId(fahrzeugreferenz.getValue()));
  }
}
