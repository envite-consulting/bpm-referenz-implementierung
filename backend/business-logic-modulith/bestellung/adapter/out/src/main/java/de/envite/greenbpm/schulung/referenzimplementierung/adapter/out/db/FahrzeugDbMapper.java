package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Fahrzeug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FahrzeugDbMapper {

  @Mapping(source = "fahrzeug.fahrzeugId.value", target = "id")
  @Mapping(source = "fahrzeug.hersteller.value", target = "hersteller")
  @Mapping(source = "fahrzeug.modell.value", target = "modell")
  @Mapping(source = "fahrzeug.jahr.value", target = "jahr")
  FahrzeugEntity toEntity(Fahrzeug fahrzeug);

  @Mapping(source = "id", target = "fahrzeugId.value")
  @Mapping(source = "hersteller", target = "hersteller.value")
  @Mapping(source = "modell", target = "modell.value")
  @Mapping(source = "jahr", target = "jahr.value")
  Fahrzeug toDomain(FahrzeugEntity fahrzeugResource);
}
