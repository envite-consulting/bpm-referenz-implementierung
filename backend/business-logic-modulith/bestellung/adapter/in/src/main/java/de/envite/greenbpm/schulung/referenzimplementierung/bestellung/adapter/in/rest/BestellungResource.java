package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import java.time.LocalDateTime;

public record BestellungResource(
    String id,
    String antragstellerreferenz,
    String fahrzeugreferenz,
    LocalDateTime bestelldatum,
    String status) {}
