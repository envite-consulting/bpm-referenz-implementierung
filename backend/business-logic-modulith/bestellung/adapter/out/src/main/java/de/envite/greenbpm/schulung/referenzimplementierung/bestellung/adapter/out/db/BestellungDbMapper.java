package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface BestellungDbMapper {

  default BestellungEntity toEntity(Bestellung bestellung) {
    // TODO: Configure mapper
    return null;
  }

  default Bestellung toDomain(BestellungEntity bestellungEntity) {
    // TODO: Configure mapper
    return null;
  }

}
