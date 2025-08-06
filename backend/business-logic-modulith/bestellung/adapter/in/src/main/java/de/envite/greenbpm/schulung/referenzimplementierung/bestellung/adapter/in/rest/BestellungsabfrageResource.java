package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import java.time.LocalDateTime;

record BestellungsabfrageResource(
    String id,
    String antragstellerreferenz,
    String fahrzeugreferenz,
    LocalDateTime bestelldatum,
    String status) {}
