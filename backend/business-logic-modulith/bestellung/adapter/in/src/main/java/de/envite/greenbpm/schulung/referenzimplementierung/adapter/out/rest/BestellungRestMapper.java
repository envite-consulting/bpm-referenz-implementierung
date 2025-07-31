package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.resource.BestellungResource;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.*;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Fahrzeug;
import java.time.LocalDateTime;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(
    componentModel = "spring",
    uses = {FahrzeugRestMapper.class})
public interface BestellungRestMapper {

  BestellungResource toResource(Bestellung bestellung);

  Bestellung toDomain(BestellungResource bestellungResource);

  @ObjectFactory
  default Bestellung createBestellung(
      BestellungResource resource, @Context FahrzeugRestMapper fahrzeugMapper) {

    Fahrzeug fahrzeug = fahrzeugMapper.toDomain(resource.fahrzeug());

    if (resource.bestellungId() == null) {
      return new Bestellung(
          mapAntragstellerId(resource.antragstellerId()),
          fahrzeug,
          mapBestelldatum(resource.bestelldatum()),
          mapStatus(resource.status()));
    } else {
      return new Bestellung(
          mapBestellungId(resource.bestellungId()),
          mapAntragstellerId(resource.antragstellerId()),
          fahrzeug,
          mapBestelldatum(resource.bestelldatum()),
          mapStatus(resource.status()));
    }
  }

  default Long mapBestellungId(BestellungId bestellungId) {
    return bestellungId.getValue();
  }

  default BestellungId mapBestellungId(Long bestellungId) {
    return new BestellungId(bestellungId);
  }

  default Long mapAntragstellerId(AntragstellerId id) {
    return id.getValue();
  }

  default AntragstellerId mapAntragstellerId(Long id) {
    return new AntragstellerId(id);
  }

  default LocalDateTime mapBestelldatum(Bestelldatum datum) {
    return datum.getValue();
  }

  default Bestelldatum mapBestelldatum(LocalDateTime datum) {
    return new Bestelldatum(datum);
  }

  default String mapStatus(Status status) {
    return status.toString();
  }

  default Status mapStatus(String status) {
    return Status.valueOf(status);
  }
}
