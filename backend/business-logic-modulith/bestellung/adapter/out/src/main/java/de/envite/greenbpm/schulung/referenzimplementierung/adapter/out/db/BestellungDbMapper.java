package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.*;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Fahrzeug;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = FahrzeugDbMapper.class)
public abstract class BestellungDbMapper {

  @Autowired
  protected FahrzeugDbMapper fahrzeugmapper;

  abstract BestellungEntity toEntity(Bestellung bestellung);

  abstract Bestellung toDomain(BestellungEntity bestellungEntity);

  @ObjectFactory
  Bestellung createBestellung(BestellungEntity entity) {

    Fahrzeug fahrzeug = fahrzeugmapper.toDomain(entity.getFahrzeug());

    return new Bestellung(
        mapBestellungId(entity.getId()),
        mapAntragstellerId(entity.getAntragstellerId()),
        fahrzeug,
        mapBestelldatum(entity.getBestelldatum()),
        mapStatus(entity.getStatus()));
  }

  String mapBestellungId(BestellungId bestellungId) {
    return bestellungId.getValue();
  }

  BestellungId mapBestellungId(String bestellungId) {
    return new BestellungId(bestellungId);
  }

  Long mapAntragstellerId(AntragstellerId id) {
    return id.getValue();
  }

  AntragstellerId mapAntragstellerId(Long id) {
    return new AntragstellerId(id);
  }

  LocalDateTime mapBestelldatum(Bestelldatum datum) {
    return datum.getValue();
  }

  Bestelldatum mapBestelldatum(LocalDateTime datum) {
    return new Bestelldatum(datum);
  }

  String mapStatus(Status status) {
    return status.toString();
  }

  Status mapStatus(String status) {
    return Status.valueOf(status);
  }
}
