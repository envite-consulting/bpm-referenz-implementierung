package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Abteilung;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Nachname;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Vorname;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T14:13:14+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
class AntragstellerRestMapperImpl implements AntragstellerRestMapper {

    @Override
    public AntragstellerResource toResource(Antragsteller antragsteller) {
        if ( antragsteller == null ) {
            return null;
        }

        String id = null;
        String vorname = null;
        String nachname = null;
        String abteilung = null;

        id = antragstellerAntragstellerIdValue( antragsteller );
        vorname = antragstellerVornameValue( antragsteller );
        nachname = antragstellerNachnameValue( antragsteller );
        abteilung = antragstellerAbteilungValue( antragsteller );

        AntragstellerResource antragstellerResource = new AntragstellerResource( id, vorname, nachname, abteilung );

        return antragstellerResource;
    }

    private String antragstellerAntragstellerIdValue(Antragsteller antragsteller) {
        AntragstellerId antragstellerId = antragsteller.getAntragstellerId();
        if ( antragstellerId == null ) {
            return null;
        }
        return antragstellerId.getValue();
    }

    private String antragstellerVornameValue(Antragsteller antragsteller) {
        Vorname vorname = antragsteller.getVorname();
        if ( vorname == null ) {
            return null;
        }
        return vorname.getValue();
    }

    private String antragstellerNachnameValue(Antragsteller antragsteller) {
        Nachname nachname = antragsteller.getNachname();
        if ( nachname == null ) {
            return null;
        }
        return nachname.getValue();
    }

    private String antragstellerAbteilungValue(Antragsteller antragsteller) {
        Abteilung abteilung = antragsteller.getAbteilung();
        if ( abteilung == null ) {
            return null;
        }
        return abteilung.getValue();
    }
}
