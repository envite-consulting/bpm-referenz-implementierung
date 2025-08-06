package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception;

public class BestellungNotFoundException extends RuntimeException {
  public BestellungNotFoundException(String message) {
    super(message);
  }
}
