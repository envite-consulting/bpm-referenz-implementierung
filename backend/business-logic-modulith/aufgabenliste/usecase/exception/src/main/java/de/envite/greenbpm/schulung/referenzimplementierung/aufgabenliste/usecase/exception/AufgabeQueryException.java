package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception;

public class AufgabeQueryException extends RuntimeException {

  public AufgabeQueryException(String message, Throwable cause) {
    super(message, cause);
  }
}
