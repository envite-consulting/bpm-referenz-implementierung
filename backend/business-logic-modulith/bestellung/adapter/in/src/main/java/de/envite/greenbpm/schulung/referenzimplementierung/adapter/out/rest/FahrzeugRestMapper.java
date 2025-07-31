package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.dto.FahrzeugDto;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Hersteller;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Jahr;
import de.envite.greenbpm.schulung.referenzimplementierung.domain.model.fahrzeug.Modell;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FahrzeugRestMapper {

    FahrzeugDto toDto(Fahrzeug fahrzeug);

    Fahrzeug toDomain(FahrzeugDto fahrzeugDto);

    default String mapHersteller(Hersteller hersteller) {
        return hersteller == null ? null : hersteller.getValue();
    }

    default Hersteller mapHersteller(String hersteller) {
        return hersteller == null ? null : new Hersteller(hersteller);
    }

    default String mapModell(Modell modell) {
        return modell == null ? null : modell.getValue();
    }

    default Modell mapModell(String modell) {
        return modell == null ? null : new Modell(modell);
    }

    default Integer mapJahr(Jahr jahr) {
        return jahr == null ? null : jahr.getValue();
    }

    default Jahr mapJahr(Integer jahr) {
        return jahr == null ? null : new Jahr(jahr);
    }
}
