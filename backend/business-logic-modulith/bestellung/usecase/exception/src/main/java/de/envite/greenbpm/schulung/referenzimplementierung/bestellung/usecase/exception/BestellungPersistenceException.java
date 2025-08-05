package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.exception;

public class BestellungPersistenceException extends RuntimeException {
  public BestellungPersistenceException(String message, Throwable cause) {
    super(message, cause);
  }
}
