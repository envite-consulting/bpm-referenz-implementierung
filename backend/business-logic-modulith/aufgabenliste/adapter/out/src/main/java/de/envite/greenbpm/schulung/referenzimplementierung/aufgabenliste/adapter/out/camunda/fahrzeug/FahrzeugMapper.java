package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.fahrzeug;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenFahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface FahrzeugMapper {

  @Mapping(source = "fahrzeug.hersteller.value", target = "hersteller")
  @Mapping(source = "fahrzeug.modell.value", target = "modell")
  FachdatenFahrzeug toDomain(Fahrzeug fahrzeug);
}
