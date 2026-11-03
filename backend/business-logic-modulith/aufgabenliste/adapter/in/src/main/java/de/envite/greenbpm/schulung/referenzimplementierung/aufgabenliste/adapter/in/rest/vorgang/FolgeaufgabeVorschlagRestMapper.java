package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.FolgeaufgabeVorschlag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface FolgeaufgabeVorschlagRestMapper {

  FolgeaufgabeVorschlagResource toResource(FolgeaufgabeVorschlag folgeaufgabeVorschlag);
}
