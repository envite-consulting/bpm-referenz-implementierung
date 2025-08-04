package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.AntragstellerAbfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out.AntragstellerStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class AntragstellerDomainService implements AntragstellerAbfrage {

    private final AntragstellerStore antragstellerStore;

    @Override
    public Antragsteller abfragen(AntragstellerId antragstellerId) throws AntragstellerNotFoundException {
        return antragstellerStore.query(antragstellerId);
    }
}
