package de.envite.greenbpm.schulung.referenzimplementierung.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.in.Bestellungsverwaltung;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.out.BestellungStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BestellungService implements Bestellungsverwaltung {

    private final BestellungStore bestellungStore;

    @Override
    public Bestellung erfassen(Bestellung bestellung) {
        return bestellungStore.persist(bestellung);
    }

    @Override
    public Bestellung anzeigen(UUID bestellungId) {
        return bestellungStore.query(bestellungId);
    }
}
