package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.aufgabe;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface AufgabenabfrageRestMapper {

  AufgabenabfrageResource toResource(Aufgabe aufgabe);
}
