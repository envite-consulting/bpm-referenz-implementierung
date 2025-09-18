package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception;

public class VorgangNotFoundException extends RuntimeException {
  public VorgangNotFoundException(String message) {
    super(message);
  }

  public VorgangNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
