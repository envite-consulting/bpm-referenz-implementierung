package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
interface BestellungserfassungRestMapper {

  Bestellung toDomain(BestellungserfassungResource bestellungserfassungResource);

  @ObjectFactory
  default Bestellung createBestellung(BestellungserfassungResource resource) {
    return new Bestellung(
        new Antragstellerreferenz(resource.antragstellerreferenz()),
        new Fahrzeugreferenz(resource.fahrzeugreferenz()));
  }
}
