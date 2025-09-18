package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenReferenz;

public interface FachdatenReferenzQuery {

    FachdatenReferenz queryByFachlicherSchluessel(String fachlicherSchluessel);
}
