package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.antragsteller;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.Antragstellerabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Antragstellerreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.AntragstellerQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("bestellungAntragstellerAdapter")
@RequiredArgsConstructor
class AntragstellerAdapter implements AntragstellerQuery {

  private final Antragstellerabfrage antragstellerabfrage;

  @Override
  public boolean validateExistence(Antragstellerreferenz antragstellerreferenz) {
    return antragstellerabfrage.existiertAntragsteller(
        new AntragstellerId(antragstellerreferenz.getValue()));
  }
}
