package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest;

import java.time.LocalDateTime;

record AufgabenabfrageResource(
    String id, String name, String bearbeiter, LocalDateTime erstelldatum, String formularreferenz) {}
