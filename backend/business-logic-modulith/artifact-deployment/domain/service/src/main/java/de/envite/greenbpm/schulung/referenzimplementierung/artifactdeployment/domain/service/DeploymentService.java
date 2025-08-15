package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.domain.service;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.exception.DeploymentException;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.in.ProzessartifaktDeployment;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentFilesQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

@RequiredArgsConstructor
@Slf4j
public class DeploymentService implements ProzessartifaktDeployment {

  private final DeploymentFilesQuery deploymentFilesQuery;
  private final DeploymentCommand deploymentCommand;

  @Override
  public void deploy() {

    List<Resource> deployments = deploymentFilesQuery.getDeploymentFiles();

    for (Resource deployment : deployments) {

      try {
        deploymentCommand.deploy(deployment);
      } catch (DeploymentException e) {
        log.error("Fehler beim Deployment des Prozessartefakts {}: ", deployment.getFilename(), e);
      }
    }
  }
}
