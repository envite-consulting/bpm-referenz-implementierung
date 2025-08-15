package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.exception.DeploymentException;
import org.springframework.core.io.Resource;

public interface DeploymentCommand {

  void deploy(Resource file) throws DeploymentException;
}
