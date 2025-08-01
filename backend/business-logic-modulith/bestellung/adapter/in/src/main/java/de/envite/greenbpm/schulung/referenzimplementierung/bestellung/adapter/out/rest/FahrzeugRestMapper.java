package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.rest.resource.FahrzeugResource;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.*;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface FahrzeugRestMapper {

  FahrzeugRestMapper INSTANCE = Mappers.getMapper(FahrzeugRestMapper.class);

  FahrzeugResource toResource(Fahrzeug fahrzeug);

  Fahrzeug toDomain(FahrzeugResource fahrzeugResource);

  @ObjectFactory
  default Fahrzeug createFahrzeug(FahrzeugResource resource) {

    if (resource.fahrzeugId() == null) {
      return new Fahrzeug(
          mapHersteller(resource.hersteller()),
          mapModell(resource.modell()),
          mapJahr(resource.jahr()));
    } else {
      Fahrzeug fahrzeug= new Fahrzeug(
          mapHersteller(resource.hersteller()),
          mapModell(resource.modell()),
          mapJahr(resource.jahr()));
      fahrzeug.setFahrzeugId(mapFahrzeugId(resource.fahrzeugId()));
      return fahrzeug;
    }
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
