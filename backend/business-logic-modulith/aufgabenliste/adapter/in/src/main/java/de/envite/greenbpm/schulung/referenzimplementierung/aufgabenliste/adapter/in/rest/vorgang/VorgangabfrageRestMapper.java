package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {FachdatenabfrageRestMapper.class})
interface VorgangabfrageRestMapper {

  VorgangabfrageResource toResource(Vorgang vorgang);
}
