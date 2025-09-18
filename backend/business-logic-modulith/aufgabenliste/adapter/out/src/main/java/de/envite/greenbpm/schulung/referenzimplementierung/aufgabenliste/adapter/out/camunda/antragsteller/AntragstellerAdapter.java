package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.antragsteller;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.Antragstellerabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.FachdatenAntragstellerQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service("aufgabenlisteAntragstellerAdapter")
@RequiredArgsConstructor
class AntragstellerAdapter implements FachdatenAntragstellerQuery {

    private final Antragstellerabfrage antragstellerabfrage;
    private final AntragstellerMapper antragstellerMapper;

    @Override
    public FachdatenAntragsteller queryByReferenz(String antragstellerReferenz) {
        Antragsteller antragsteller = antragstellerabfrage.abfragen(new AntragstellerId(antragstellerReferenz));
        return antragstellerMapper.toDomain(antragsteller);
    }
}
