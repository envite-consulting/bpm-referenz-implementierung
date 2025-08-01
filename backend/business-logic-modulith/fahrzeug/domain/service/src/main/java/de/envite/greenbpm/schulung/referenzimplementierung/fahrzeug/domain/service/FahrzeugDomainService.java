package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.FahrzeugQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FahrzeugDomainService implements FahrzeugQuery {

    private final FahrzeugStore fahrzeugStore;

    @Override
    public Fahrzeug query(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException {
        return fahrzeugStore.find(fahrzeugId);
    }
}
