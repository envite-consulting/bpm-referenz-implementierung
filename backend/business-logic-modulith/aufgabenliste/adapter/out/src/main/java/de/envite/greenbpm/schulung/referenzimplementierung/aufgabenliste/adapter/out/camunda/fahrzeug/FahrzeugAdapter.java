package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.fahrzeug;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenFahrzeugQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.Fahrzeugabfrage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("aufgabenlisteFahrzeugAdapter")
@RequiredArgsConstructor
class FahrzeugAdapter implements FachdatenFahrzeugQuery {

  private final Fahrzeugabfrage fahrzeugabfrage;
  private final FahrzeugMapper fahrzeugMapper;

  @Override
  public FachdatenFahrzeug queryByReferenz(String fahrzeugReferenz) {
    Fahrzeug fahrzeug = fahrzeugabfrage.abfragen(new FahrzeugId(fahrzeugReferenz));
    return fahrzeugMapper.toDomain(fahrzeug);
  }
}
