package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
interface BestellungDbMapper {

  @Mapping(source = "bestellung.antragstellerreferenz.value", target = "antragstellerreferenz")
  @Mapping(source = "bestellung.fahrzeugreferenz.value", target = "fahrzeugreferenz")
  @Mapping(source = "bestellung.bestelldatum.value", target = "bestelldatum")
  @Mapping(source = "bestellung.status", target = "status")
  @Mapping(source = "bestellung.bestellungId.value", target = "id")
  BestellungEntity toEntity(Bestellung bestellung);

  Bestellung toDomain(BestellungEntity bestellungEntity);

  @ObjectFactory
  default Bestellung createBestellung(BestellungEntity entity) {

    if (entity.getId() == null) {
      return new Bestellung(
          new Antragstellerreferenz(entity.getAntragstellerreferenz()),
          new Fahrzeugreferenz(entity.getFahrzeugreferenz()),
          new Bestelldatum(entity.getBestelldatum()),
          Status.valueOf(entity.getStatus()));
    } else {
      return new Bestellung(
          new BestellungId(entity.getId()),
          new Antragstellerreferenz(entity.getAntragstellerreferenz()),
          new Fahrzeugreferenz(entity.getFahrzeugreferenz()),
          new Bestelldatum(entity.getBestelldatum()),
          Status.valueOf(entity.getStatus()));
    }
  }
}
