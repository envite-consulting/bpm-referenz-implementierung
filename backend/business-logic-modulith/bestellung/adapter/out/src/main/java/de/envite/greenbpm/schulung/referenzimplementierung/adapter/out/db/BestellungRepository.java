package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.out.BestellungStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BestellungRepository implements BestellungStore {

    private final BestellungCrudRepository bestellungCrudRepository;
    private final BestellungMapper bestellungMapper;


    @Override
    public Bestellung persist(Bestellung bestellung) {
        return bestellungMapper.toDomain(bestellungCrudRepository.save(bestellungMapper.toEntity(bestellung)));
    }

    @Override
    public Bestellung query(UUID bestellungId) {
        return bestellungMapper.toDomain(bestellungCrudRepository.findById(bestellungId).orElseThrow(() -> new BestellungNotFoundException("Bestellung mit der ID %s nicht gefunden.".formatted(bestellungId))));
    }
}
