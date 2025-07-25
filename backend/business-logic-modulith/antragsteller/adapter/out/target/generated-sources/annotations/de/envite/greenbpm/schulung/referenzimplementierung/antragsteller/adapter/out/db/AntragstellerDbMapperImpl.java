package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Abteilung;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Nachname;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Vorname;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T14:13:34+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
class AntragstellerDbMapperImpl implements AntragstellerDbMapper {

    @Override
    public AntragstellerEntity toEntity(Antragsteller antragsteller) {
        if ( antragsteller == null ) {
            return null;
        }

        AntragstellerEntity antragstellerEntity = new AntragstellerEntity();

        antragstellerEntity.setId( antragstellerAntragstellerIdValue( antragsteller ) );
        antragstellerEntity.setVorname( antragstellerVornameValue( antragsteller ) );
        antragstellerEntity.setNachname( antragstellerNachnameValue( antragsteller ) );
        antragstellerEntity.setAbteilung( antragstellerAbteilungValue( antragsteller ) );

        return antragstellerEntity;
    }

    @Override
    public Antragsteller toDomain(AntragstellerEntity antragstellerResource) {
        if ( antragstellerResource == null ) {
            return null;
        }

        Vorname vorname = null;
        Nachname nachname = null;
        Abteilung abteilung = null;

        vorname = antragstellerEntityToVorname( antragstellerResource );
        nachname = antragstellerEntityToNachname( antragstellerResource );
        abteilung = antragstellerEntityToAbteilung( antragstellerResource );

        Antragsteller antragsteller = new Antragsteller( vorname, nachname, abteilung );

        antragsteller.setAntragstellerId( antragstellerEntityToAntragstellerId( antragstellerResource ) );

        return antragsteller;
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

    protected AntragstellerId antragstellerEntityToAntragstellerId(AntragstellerEntity antragstellerEntity) {
        if ( antragstellerEntity == null ) {
            return null;
        }

        String value = null;

        value = antragstellerEntity.getId();

        AntragstellerId antragstellerId = new AntragstellerId( value );

        return antragstellerId;
    }

    protected Vorname antragstellerEntityToVorname(AntragstellerEntity antragstellerEntity) {
        if ( antragstellerEntity == null ) {
            return null;
        }

        String value = null;

        value = antragstellerEntity.getVorname();

        Vorname vorname = new Vorname( value );

        return vorname;
    }

    protected Nachname antragstellerEntityToNachname(AntragstellerEntity antragstellerEntity) {
        if ( antragstellerEntity == null ) {
            return null;
        }

        String value = null;

        value = antragstellerEntity.getNachname();

        Nachname nachname = new Nachname( value );

        return nachname;
    }

    protected Abteilung antragstellerEntityToAbteilung(AntragstellerEntity antragstellerEntity) {
        if ( antragstellerEntity == null ) {
            return null;
        }

        String value = null;

        value = antragstellerEntity.getAbteilung();

        Abteilung abteilung = new Abteilung( value );

        return abteilung;
    }
}
