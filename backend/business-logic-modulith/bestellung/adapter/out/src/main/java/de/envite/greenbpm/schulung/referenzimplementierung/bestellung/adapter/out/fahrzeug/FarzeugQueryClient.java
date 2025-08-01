package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.fahrzeug;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.out.FahrzeugQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class FarzeugQueryClient implements FahrzeugQuery {

    private final de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.FahrzeugQuery fahrzeugQuery;

    @Override
    public boolean validateExistence(Fahrzeugreferenz fahrzeugreferenz) {
        try {
            fahrzeugQuery.query(new FahrzeugId(fahrzeugreferenz.getValue()));
            return true;
        } catch (FahrzeugNotFoundException e) {
            return false;
        }
    }
}
