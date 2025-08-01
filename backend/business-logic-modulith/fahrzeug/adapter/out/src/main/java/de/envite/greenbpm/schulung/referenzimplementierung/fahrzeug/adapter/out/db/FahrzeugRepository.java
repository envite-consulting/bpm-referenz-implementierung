package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception.FahrzeugNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.out.FahrzeugStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class FahrzeugRepository implements FahrzeugStore {

    private final FahrzeugJdbcRepository fahrzeugJdbcRepository;
    private final FahrzeugDbMapper fahrzeugDbMapper;

    @Override
    public Fahrzeug find(FahrzeugId fahrzeugId) throws FahrzeugNotFoundException {
        FahrzeugEntity entity = fahrzeugJdbcRepository.findById(fahrzeugId.getValue())
                .orElseThrow(() -> new FahrzeugNotFoundException(
                        String.format(
                                "Fahrzeug mit der ID %s nicht gefunden",
                                fahrzeugId.getValue()
                        )
                ));
        return fahrzeugDbMapper.toDomain(entity);
    }
}
