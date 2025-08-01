package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.exception;

public class FahrzeugNotFoundException extends RuntimeException {
  public FahrzeugNotFoundException(String message) {
    super(message);
  }
}
