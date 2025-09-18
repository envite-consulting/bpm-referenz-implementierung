package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;

public interface FachdatenFahrzeugQuery {

  FachdatenFahrzeug queryByReferenz(String fahrzeugReferenz);
}
