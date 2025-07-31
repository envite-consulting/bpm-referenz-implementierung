package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.BestellungEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.*;
import java.time.LocalDateTime;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = FahrzeugDbMapper.class)
public interface BestellungDbMapper {

  BestellungEntity toEntity(Bestellung bestellung);

  Bestellung toDomain(BestellungEntity bestellungEntity);

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
