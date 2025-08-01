package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.out.rest;

import java.time.LocalDateTime;

record BestellungResource(
    String bestellungId,
    Long antragstellerId,
    String fahrzeugreferenz,
    LocalDateTime bestelldatum,
    String status) {}
