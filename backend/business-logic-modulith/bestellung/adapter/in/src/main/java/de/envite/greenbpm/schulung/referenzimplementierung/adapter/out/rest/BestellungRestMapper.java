package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest;


import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.dto.BestellungDto;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestelldatum;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Status;
import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = {FahrzeugRestMapper.class})
public interface BestellungRestMapper {

    BestellungDto toDto(Bestellung bestellung);

    Bestellung toDomain(BestellungDto bestellungDto);

    default UUID mapAntragstellerId(AntragstellerId id) {
        return id == null ? null : id.getValue(); // getValue() liefert UUID
    }

    default AntragstellerId mapAntragstellerId(UUID id) {
        return id == null ? null : new AntragstellerId(id);
    }

    default LocalDateTime mapBestelldatum(Bestelldatum datum) {
        return datum == null ? null : datum.getValue(); // getValue liefert LocalDateTime
    }

    default Bestelldatum mapBestelldatum(LocalDateTime datum) {
        return datum == null ? null : new Bestelldatum(datum);
    }

    default String mapStatus(Status status) {
        return status == null ? null : status.toString(); // oder status.getName() etc.
    }

    default Status mapStatus(String status) {
        return status == null ? null : Status.valueOf(status); // falls Enum
    }
}

