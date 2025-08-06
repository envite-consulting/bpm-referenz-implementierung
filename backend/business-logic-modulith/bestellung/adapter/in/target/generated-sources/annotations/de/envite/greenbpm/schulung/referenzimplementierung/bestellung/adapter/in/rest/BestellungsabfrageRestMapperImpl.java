package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Antragstellerreferenz;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestelldatum;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Fahrzeugreferenz;
import java.time.LocalDateTime;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T18:07:39+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
class BestellungsabfrageRestMapperImpl implements BestellungsabfrageRestMapper {

    @Override
    public BestellungsabfrageResource toResource(Bestellung bestellung) {
        if ( bestellung == null ) {
            return null;
        }

        String antragstellerreferenz = null;
        String fahrzeugreferenz = null;
        LocalDateTime bestelldatum = null;
        String status = null;
        String id = null;

        antragstellerreferenz = bestellungAntragstellerreferenzValue( bestellung );
        fahrzeugreferenz = bestellungFahrzeugreferenzValue( bestellung );
        bestelldatum = bestellungBestelldatumValue( bestellung );
        if ( bestellung.getStatus() != null ) {
            status = bestellung.getStatus().name();
        }
        id = bestellungBestellungIdValue( bestellung );

        BestellungsabfrageResource bestellungsabfrageResource = new BestellungsabfrageResource( id, antragstellerreferenz, fahrzeugreferenz, bestelldatum, status );

        return bestellungsabfrageResource;
    }

    private String bestellungAntragstellerreferenzValue(Bestellung bestellung) {
        Antragstellerreferenz antragstellerreferenz = bestellung.getAntragstellerreferenz();
        if ( antragstellerreferenz == null ) {
            return null;
        }
        return antragstellerreferenz.getValue();
    }

    private String bestellungFahrzeugreferenzValue(Bestellung bestellung) {
        Fahrzeugreferenz fahrzeugreferenz = bestellung.getFahrzeugreferenz();
        if ( fahrzeugreferenz == null ) {
            return null;
        }
        return fahrzeugreferenz.getValue();
    }

    private LocalDateTime bestellungBestelldatumValue(Bestellung bestellung) {
        Bestelldatum bestelldatum = bestellung.getBestelldatum();
        if ( bestelldatum == null ) {
            return null;
        }
        return bestelldatum.getValue();
    }

    private String bestellungBestellungIdValue(Bestellung bestellung) {
        BestellungId bestellungId = bestellung.getBestellungId();
        if ( bestellungId == null ) {
            return null;
        }
        return bestellungId.getValue();
    }
}
