package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
interface BestellungRestMapper {

  @Mapping(source = "bestellung.antragstellerreferenz.value", target = "antragstellerreferenz")
  @Mapping(source = "bestellung.fahrzeugreferenz.value", target = "fahrzeugreferenz")
  @Mapping(source = "bestellung.bestelldatum.value", target = "bestelldatum")
  @Mapping(source = "bestellung.status", target = "status")
  @Mapping(source = "bestellung.bestellungId.value", target = "id")
  BestellungResource toResource(Bestellung bestellung);

  Bestellung toDomain(BestellungResource bestellungResource);

  @ObjectFactory
  default Bestellung createBestellung(BestellungResource resource) {

    if (resource.id() == null) {
      return new Bestellung(
          new Antragstellerreferenz(resource.antragstellerreferenz()),
          new Fahrzeugreferenz(resource.fahrzeugreferenz()),
          new Bestelldatum(resource.bestelldatum()),
          Status.valueOf(resource.status()));
    } else {
      return new Bestellung(
          new BestellungId(resource.id()),
          new Antragstellerreferenz(resource.antragstellerreferenz()),
          new Fahrzeugreferenz(resource.fahrzeugreferenz()),
          new Bestelldatum(resource.bestelldatum()),
          Status.valueOf(resource.status()));
    }
  }
}
