package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.exception;

public class DeploymentException extends RuntimeException {
  public DeploymentException(String message, Throwable cause) {
    super(message, cause);
  }
}
