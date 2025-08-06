package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import java.time.LocalDateTime;

record BestellungserfassungResource(
    String antragstellerreferenz,
    String fahrzeugreferenz,
    LocalDateTime bestelldatum,
    String status) {}
