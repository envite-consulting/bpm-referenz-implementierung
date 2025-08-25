package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception;

public class ProzessstartException extends RuntimeException {
  public ProzessstartException(String message, Throwable cause) {
    super(message, cause);
  }
}
