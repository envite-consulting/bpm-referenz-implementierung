package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.in;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.camunda.CamundaDeploymentAdapter;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.file.ProcessFileReader;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.domain.service.DeploymentService;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.in.ProzessartifaktDeployment;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentFilesQuery;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class DeploymentServiceConfiguration {

  @Bean
  public DeploymentFilesQuery readDeploymentFilesCommand(
      @Value("${process-artifacts.base-path:classpath*:/**/*}") List<Resource> resources) {

    return new ProcessFileReader(resources);
  }

  @Bean
  public WebClient webClient(
      @Value("${camunda.bpm.client.base-url:http://localhost:8081/engine-rest}")
          String camundaEngineBaseUrl) {
    return WebClient.builder().baseUrl(camundaEngineBaseUrl).build();
  }

  @Bean
  public DeploymentCommand deploymentCommand(WebClient webClient) {
    return new CamundaDeploymentAdapter(webClient);
  }

  @Bean
  public ProzessartifaktDeployment processArtefactDeployment(
      DeploymentFilesQuery readDeploymentFilesCommand, DeploymentCommand deploymentCommand) {
    return new DeploymentService(readDeploymentFilesCommand, deploymentCommand);
  }
}
