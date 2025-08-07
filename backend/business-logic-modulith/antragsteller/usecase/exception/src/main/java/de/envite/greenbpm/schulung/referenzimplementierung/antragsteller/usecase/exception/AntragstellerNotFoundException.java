package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.exception;

public class AntragstellerNotFoundException extends RuntimeException {
  public AntragstellerNotFoundException(String message) {
    super(message);
  }
}
