package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception;

public class AufgabeUpdateException extends RuntimeException {
  public AufgabeUpdateException(String message, Throwable cause) {
    super(message, cause);
  }
}
