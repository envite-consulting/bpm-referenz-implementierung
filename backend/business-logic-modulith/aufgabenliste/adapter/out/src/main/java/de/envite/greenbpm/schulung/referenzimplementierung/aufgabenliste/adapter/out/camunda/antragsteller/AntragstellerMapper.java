package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.antragsteller;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.FachdatenAntragsteller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface AntragstellerMapper {

    @Mapping(source = "antragsteller.vorname.value", target = "vorname")
    @Mapping(source = "antragsteller.nachname.value", target = "nachname")
    FachdatenAntragsteller toDomain(Antragsteller antragsteller);
}
