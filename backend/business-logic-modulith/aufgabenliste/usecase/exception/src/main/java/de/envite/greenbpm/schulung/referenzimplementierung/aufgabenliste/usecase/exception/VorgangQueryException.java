package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception;

public class VorgangQueryException extends RuntimeException {

  public VorgangQueryException(String message, Throwable cause) {
    super(message, cause);
  }
}
