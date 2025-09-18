package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.Fachdaten;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface FachdatenabfrageRestMapper {

  @Mapping(source = "fachdaten.antragsteller.vorname", target = "antragstellerVorname")
  @Mapping(source = "fachdaten.antragsteller.nachname", target = "antragstellerNachname")
  @Mapping(source = "fachdaten.fahrzeug.hersteller", target = "fahrzeugHersteller")
  @Mapping(source = "fachdaten.fahrzeug.modell", target = "fahrzeugModell")
  FachdatenabfrageResource toResource(Fachdaten fachdaten);
}
