package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception;

public class AufgabeNotFoundException extends RuntimeException {
  public AufgabeNotFoundException(String message) {
    super(message);
  }

  public AufgabeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
