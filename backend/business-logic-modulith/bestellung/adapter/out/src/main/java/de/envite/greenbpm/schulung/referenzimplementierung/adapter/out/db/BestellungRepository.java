package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.usecase.out.BestellungStore;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BestellungRepository implements BestellungStore {

    private final BestellungCrudRepository bestellungCrudRepository;
    private final BestellungDbMapper bestellungDbMapper;

    public BestellungRepository(BestellungCrudRepository bestellungCrudRepository, BestellungDbMapper bestellungDbMapper) {
        this.bestellungCrudRepository = bestellungCrudRepository;
        this.bestellungDbMapper = bestellungDbMapper;
    }


    @Override
    public Bestellung persist(Bestellung bestellung) {
        BestellungEntity entity = bestellungDbMapper.toEntity(bestellung);
        return bestellungDbMapper.toDomain(bestellungCrudRepository.save(entity));
    }

    @Override
    public Bestellung query(UUID bestellungId) {
        return bestellungDbMapper.toDomain(bestellungCrudRepository.findById(bestellungId).orElseThrow(() -> new BestellungNotFoundException("Bestellung mit der ID %s nicht gefunden.".formatted(bestellungId))));
    }
}
