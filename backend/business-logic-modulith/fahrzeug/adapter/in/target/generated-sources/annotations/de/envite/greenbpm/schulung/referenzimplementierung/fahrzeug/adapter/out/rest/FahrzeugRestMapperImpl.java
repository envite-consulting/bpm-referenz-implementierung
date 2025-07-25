package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Hersteller;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Jahr;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Modell;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T17:19:00+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
class FahrzeugRestMapperImpl implements FahrzeugRestMapper {

    @Override
    public FahrzeugResource toResource(Fahrzeug fahrzeug) {
        if ( fahrzeug == null ) {
            return null;
        }

        String id = null;
        String hersteller = null;
        String modell = null;
        Integer jahr = null;

        id = fahrzeugFahrzeugIdValue( fahrzeug );
        hersteller = fahrzeugHerstellerValue( fahrzeug );
        modell = fahrzeugModellValue( fahrzeug );
        jahr = fahrzeugJahrValue( fahrzeug );

        FahrzeugResource fahrzeugResource = new FahrzeugResource( id, hersteller, modell, jahr );

        return fahrzeugResource;
    }

    private String fahrzeugFahrzeugIdValue(Fahrzeug fahrzeug) {
        FahrzeugId fahrzeugId = fahrzeug.getFahrzeugId();
        if ( fahrzeugId == null ) {
            return null;
        }
        return fahrzeugId.getValue();
    }

    private String fahrzeugHerstellerValue(Fahrzeug fahrzeug) {
        Hersteller hersteller = fahrzeug.getHersteller();
        if ( hersteller == null ) {
            return null;
        }
        return hersteller.getValue();
    }

    private String fahrzeugModellValue(Fahrzeug fahrzeug) {
        Modell modell = fahrzeug.getModell();
        if ( modell == null ) {
            return null;
        }
        return modell.getValue();
    }

    private Integer fahrzeugJahrValue(Fahrzeug fahrzeug) {
        Jahr jahr = fahrzeug.getJahr();
        if ( jahr == null ) {
            return null;
        }
        return jahr.getValue();
    }
}
