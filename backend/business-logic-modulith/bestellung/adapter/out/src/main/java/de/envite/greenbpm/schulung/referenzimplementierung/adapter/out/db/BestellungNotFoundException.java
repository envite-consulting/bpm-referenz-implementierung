package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

class BestellungNotFoundException extends RuntimeException {
    public BestellungNotFoundException(String message) {
        super(message);
    }
}
