package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface AufgabenabfrageRestMapper {

  AufgabenabfrageResource toResource(Aufgabe aufgabe);
}
