package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.*;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FahrzeugDbMapper {

  FahrzeugDbMapper INSTANCE = Mappers.getMapper(FahrzeugDbMapper.class);

  FahrzeugEntity toEntity(Fahrzeug fahrzeug);

  Fahrzeug toDomain(FahrzeugEntity fahrzeugResource);

  @ObjectFactory
  default Fahrzeug createFahrzeug(FahrzeugEntity entity) {

    return new Fahrzeug(
        mapFahrzeugId(entity.getId()),
        mapHersteller(entity.getHersteller()),
        mapModell(entity.getModell()),
        mapJahr(entity.getJahr()));
  }

  default String mapFahrzeugId(FahrzeugId fahrzeugId) {
    return fahrzeugId.getValue();
  }

  default FahrzeugId mapFahrzeugId(String fahrzeugId) {
    return new FahrzeugId(fahrzeugId);
  }

  default String mapHersteller(Hersteller hersteller) {
    return hersteller.getValue();
  }

  default Hersteller mapHersteller(String hersteller) {
    return new Hersteller(hersteller);
  }

  default String mapModell(Modell modell) {
    return modell.getValue();
  }

  default Modell mapModell(String modell) {
    return new Modell(modell);
  }

  default Integer mapJahr(Jahr jahr) {
    return jahr.getValue();
  }

  default Jahr mapJahr(Integer jahr) {
    return new Jahr(jahr);
  }
}
