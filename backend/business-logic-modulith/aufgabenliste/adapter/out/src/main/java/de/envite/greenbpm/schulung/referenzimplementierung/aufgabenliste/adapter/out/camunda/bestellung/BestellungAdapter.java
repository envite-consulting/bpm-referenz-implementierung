package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.bestellung;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenReferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenReferenzQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungsabfrage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class BestellungAdapter implements FachdatenReferenzQuery {

    private final Bestellungsabfrage bestellungsabfrage;
    private final BestellungMapper bestellungMapper;

    @Override
    public FachdatenReferenz queryByFachlicherSchluessel(String bestellungId) {
        Bestellung bestellung = bestellungsabfrage.abfragen(new BestellungId(bestellungId));
        return bestellungMapper.toDomain(bestellung);
    }
}
