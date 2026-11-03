package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception;

public class FolgeaufgabeNotFoundException extends RuntimeException {
  public FolgeaufgabeNotFoundException(String message) {
    super(message);
  }
}
