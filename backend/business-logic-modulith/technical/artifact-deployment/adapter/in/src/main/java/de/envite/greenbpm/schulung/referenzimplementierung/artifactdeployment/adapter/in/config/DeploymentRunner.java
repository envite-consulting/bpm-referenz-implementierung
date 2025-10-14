package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.in.config;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.in.ProzessartifaktDeployment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
class DeploymentRunner implements CommandLineRunner {

  private final ProzessartifaktDeployment prozessartifaktDeployment;

  @Override
  public void run(String... args) {
    try {
      prozessartifaktDeployment.deploy();
    } catch (Exception e) {
      log.error("Prozessartefakte konnten nicht deployt werden: ", e);
    }
  }
}
