package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Hersteller;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Jahr;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Modell;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-05T17:19:19+0200",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.3 (Eclipse Adoptium)"
)
@Component
class FahrzeugDbMapperImpl implements FahrzeugDbMapper {

    @Override
    public FahrzeugEntity toEntity(Fahrzeug fahrzeug) {
        if ( fahrzeug == null ) {
            return null;
        }

        FahrzeugEntity fahrzeugEntity = new FahrzeugEntity();

        fahrzeugEntity.setId( fahrzeugFahrzeugIdValue( fahrzeug ) );
        fahrzeugEntity.setHersteller( fahrzeugHerstellerValue( fahrzeug ) );
        fahrzeugEntity.setModell( fahrzeugModellValue( fahrzeug ) );
        fahrzeugEntity.setJahr( fahrzeugJahrValue( fahrzeug ) );

        return fahrzeugEntity;
    }

    @Override
    public Fahrzeug toDomain(FahrzeugEntity fahrzeugResource) {
        if ( fahrzeugResource == null ) {
            return null;
        }

        Hersteller hersteller = null;
        Modell modell = null;
        Jahr jahr = null;

        hersteller = fahrzeugEntityToHersteller( fahrzeugResource );
        modell = fahrzeugEntityToModell( fahrzeugResource );
        jahr = fahrzeugEntityToJahr( fahrzeugResource );

        Fahrzeug fahrzeug = new Fahrzeug( hersteller, modell, jahr );

        fahrzeug.setFahrzeugId( fahrzeugEntityToFahrzeugId( fahrzeugResource ) );

        return fahrzeug;
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

    protected FahrzeugId fahrzeugEntityToFahrzeugId(FahrzeugEntity fahrzeugEntity) {
        if ( fahrzeugEntity == null ) {
            return null;
        }

        String value = null;

        value = fahrzeugEntity.getId();

        FahrzeugId fahrzeugId = new FahrzeugId( value );

        return fahrzeugId;
    }

    protected Hersteller fahrzeugEntityToHersteller(FahrzeugEntity fahrzeugEntity) {
        if ( fahrzeugEntity == null ) {
            return null;
        }

        String value = null;

        value = fahrzeugEntity.getHersteller();

        Hersteller hersteller = new Hersteller( value );

        return hersteller;
    }

    protected Modell fahrzeugEntityToModell(FahrzeugEntity fahrzeugEntity) {
        if ( fahrzeugEntity == null ) {
            return null;
        }

        String value = null;

        value = fahrzeugEntity.getModell();

        Modell modell = new Modell( value );

        return modell;
    }

    protected Jahr fahrzeugEntityToJahr(FahrzeugEntity fahrzeugEntity) {
        if ( fahrzeugEntity == null ) {
            return null;
        }

        Integer value = null;

        value = fahrzeugEntity.getJahr();

        Jahr jahr = new Jahr( value );

        return jahr;
    }
}
