package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface AntragstellerRestMapper {

  @Mapping(source = "antragsteller.antragstellerId.value", target = "id")
  @Mapping(source = "antragsteller.vorname.value", target = "vorname")
  @Mapping(source = "antragsteller.nachname.value", target = "nachname")
  @Mapping(source = "antragsteller.abteilung.value", target = "abteilung")
  AntragstellerResource toResource(Antragsteller antragsteller);
}
