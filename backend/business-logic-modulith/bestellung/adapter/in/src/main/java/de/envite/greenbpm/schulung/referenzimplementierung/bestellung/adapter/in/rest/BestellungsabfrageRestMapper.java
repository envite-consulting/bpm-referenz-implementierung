package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface BestellungsabfrageRestMapper {

  @Mapping(source = "bestellung.antragstellerreferenz.value", target = "antragstellerreferenz")
  @Mapping(source = "bestellung.fahrzeugreferenz.value", target = "fahrzeugreferenz")
  @Mapping(source = "bestellung.bestelldatum.value", target = "bestelldatum")
  @Mapping(source = "bestellung.status", target = "status")
  @Mapping(source = "bestellung.bestellungId.value", target = "id")
  BestellungsabfrageResource toResource(Bestellung bestellung);
}
