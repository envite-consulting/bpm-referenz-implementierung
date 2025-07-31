package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FahrzeugDbMapper {

  FahrzeugEntity toEntity(Fahrzeug fahrzeug);

  Fahrzeug toDomain(FahrzeugEntity fahrzeugResource);

  default Long mapFahrzeugId(FahrzeugId fahrzeugId) {
    return fahrzeugId.getValue();
  }

  default FahrzeugId mapFahrzeugId(Long fahrzeugId) {
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
