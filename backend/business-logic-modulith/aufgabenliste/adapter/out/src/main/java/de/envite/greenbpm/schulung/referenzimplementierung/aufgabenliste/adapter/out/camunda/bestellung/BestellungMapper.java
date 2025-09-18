package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.bestellung;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenReferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface BestellungMapper {

  @Mapping(source = "bestellung.antragstellerreferenz.value", target = "antragstellerreferenz")
  @Mapping(source = "bestellung.fahrzeugreferenz.value", target = "fahrzeugreferenz")
  FachdatenReferenz toDomain(Bestellung bestellung);
}
