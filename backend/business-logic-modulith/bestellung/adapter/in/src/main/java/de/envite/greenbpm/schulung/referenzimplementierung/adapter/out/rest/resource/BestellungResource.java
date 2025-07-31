package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.rest.resource;

import java.time.LocalDateTime;

public record BestellungResource(
    Long bestellungId,
    Long antragstellerId,
    FahrzeugResource fahrzeug,
    LocalDateTime bestelldatum,
    String status) {}
