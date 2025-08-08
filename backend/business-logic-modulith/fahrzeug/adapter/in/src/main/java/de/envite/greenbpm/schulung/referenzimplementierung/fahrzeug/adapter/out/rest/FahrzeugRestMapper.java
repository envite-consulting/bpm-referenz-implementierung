package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface FahrzeugRestMapper {

  @Mapping(source = "fahrzeug.fahrzeugId.value", target = "id")
  @Mapping(source = "fahrzeug.hersteller.value", target = "hersteller")
  @Mapping(source = "fahrzeug.modell.value", target = "modell")
  @Mapping(source = "fahrzeug.jahr.value", target = "jahr")
  FahrzeugResource toResource(Fahrzeug fahrzeug);
}
