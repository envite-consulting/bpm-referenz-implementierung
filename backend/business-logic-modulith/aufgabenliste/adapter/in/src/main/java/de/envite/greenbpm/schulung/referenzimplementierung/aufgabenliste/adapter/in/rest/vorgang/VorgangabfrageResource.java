package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

record VorgangabfrageResource(
    String id, String fachlicherSchluessel, FachdatenabfrageResource fachdaten) {}
