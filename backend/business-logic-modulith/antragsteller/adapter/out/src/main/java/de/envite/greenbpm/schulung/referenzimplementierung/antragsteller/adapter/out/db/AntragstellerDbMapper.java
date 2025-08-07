package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface AntragstellerDbMapper {

  @Mapping(source = "antragsteller.antragstellerId.value", target = "id")
  @Mapping(source = "antragsteller.vorname.value", target = "vorname")
  @Mapping(source = "antragsteller.nachname.value", target = "nachname")
  @Mapping(source = "antragsteller.abteilung.value", target = "abteilung")
  AntragstellerEntity toEntity(Antragsteller antragsteller);

  @Mapping(source = "id", target = "antragstellerId.value")
  @Mapping(source = "vorname", target = "vorname.value")
  @Mapping(source = "nachname", target = "nachname.value")
  @Mapping(source = "abteilung", target = "abteilung.value")
  Antragsteller toDomain(AntragstellerEntity antragstellerResource);
}
