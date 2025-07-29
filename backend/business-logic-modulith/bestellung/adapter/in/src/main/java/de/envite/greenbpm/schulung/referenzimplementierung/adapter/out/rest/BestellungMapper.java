package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest;


import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.dto.BestellungDto;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.Bestellung;
import org.mapstruct.Mapper;

@Mapper
public interface BestellungMapper {

    BestellungDto toDto(Bestellung bestellung);

    Bestellung toDomain(BestellungDto bestellungDto);
}

