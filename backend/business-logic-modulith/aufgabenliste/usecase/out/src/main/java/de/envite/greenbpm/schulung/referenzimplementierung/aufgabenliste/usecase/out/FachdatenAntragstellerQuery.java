package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;

public interface FachdatenAntragstellerQuery {

  FachdatenAntragsteller queryByReferenz(String antragstellerReferenz);
}
