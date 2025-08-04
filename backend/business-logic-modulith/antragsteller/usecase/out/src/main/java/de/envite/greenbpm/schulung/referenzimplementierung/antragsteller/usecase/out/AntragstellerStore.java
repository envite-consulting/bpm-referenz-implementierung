package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception.AntragstellerNotFoundException;

public interface AntragstellerStore {

    Antragsteller query(AntragstellerId antragstellerId) throws AntragstellerNotFoundException;
}
