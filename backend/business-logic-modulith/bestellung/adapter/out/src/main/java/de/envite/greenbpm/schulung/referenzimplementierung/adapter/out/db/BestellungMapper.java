package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import org.mapstruct.Mapper;

@Mapper
public interface BestellungMapper {

    BestellungEntity toEntity(Bestellung bestellung);

    Bestellung toDomain(BestellungEntity bestellungEntity);
}

