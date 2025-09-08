package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.camunda;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.exception.DeploymentException;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class CamundaDeploymentAdapter implements DeploymentCommand {

  private final WebClient webClient;

  public CamundaDeploymentAdapter(
      @Value("${camunda.bpm.client.base-url:http://localhost:8081/engine-rest}")
          String camundaEngineBaseUrl) {
    this.webClient = WebClient.builder().baseUrl(camundaEngineBaseUrl).build();
  }

  @Override
  public void deploy(Resource file) throws DeploymentException {
    MultipartBodyBuilder body = new MultipartBodyBuilder();
    body.part(
        "deployment-name", file.getFilename().substring(0, file.getFilename().lastIndexOf(".")));
    body.part("deploy-changed-only", "true");
    body.part("data", file);

    try {
      webClient
          .post()
          .uri("/deployment/create")
          .body(BodyInserters.fromMultipartData(body.build()))
          .retrieve()
          .bodyToMono(String.class)
          .block();
    } catch (WebClientRequestException | WebClientResponseException e) {
      throw new DeploymentException(
              "Prozessartefakt (%s) konnte nicht deployt werden.".formatted(file.getFilename()), e);
    }
  }
}
