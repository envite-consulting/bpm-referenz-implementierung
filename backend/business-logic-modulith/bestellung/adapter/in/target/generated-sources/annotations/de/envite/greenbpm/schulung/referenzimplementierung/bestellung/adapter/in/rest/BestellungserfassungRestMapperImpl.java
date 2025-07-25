package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T18:07:39+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
class BestellungserfassungRestMapperImpl implements BestellungserfassungRestMapper {

    @Override
    public Bestellung toDomain(BestellungserfassungResource bestellungserfassungResource) {
        if ( bestellungserfassungResource == null ) {
            return null;
        }

        Bestellung bestellung = createBestellung( bestellungserfassungResource );

        return bestellung;
    }
}
