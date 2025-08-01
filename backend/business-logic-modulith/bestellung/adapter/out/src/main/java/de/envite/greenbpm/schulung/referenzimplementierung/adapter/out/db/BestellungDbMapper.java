package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.*;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Fahrzeug;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = FahrzeugDbMapper.class)
public interface BestellungDbMapper {

  BestellungEntity toEntity(Bestellung bestellung);

  Bestellung toDomain(BestellungEntity bestellungEntity);

  @ObjectFactory
  default Bestellung createBestellung(BestellungEntity entity) {

    Fahrzeug fahrzeug = FahrzeugDbMapper.INSTANCE.toDomain(entity.getFahrzeug());

    return new Bestellung(
        mapBestellungId(entity.getId()),
        mapAntragstellerId(entity.getAntragstellerId()),
        fahrzeug,
        mapBestelldatum(entity.getBestelldatum()),
        mapStatus(entity.getStatus()));
  }

  default String mapBestellungId(BestellungId bestellungId) {
    return bestellungId.getValue();
  }

  default BestellungId mapBestellungId(String bestellungId) {
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
