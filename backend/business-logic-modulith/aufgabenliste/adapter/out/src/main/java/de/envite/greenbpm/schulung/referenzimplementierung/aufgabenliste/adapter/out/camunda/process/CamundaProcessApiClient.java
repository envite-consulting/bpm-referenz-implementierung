package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.process;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.ProzessstartException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.ProzessCommand;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
class CamundaProcessApiClient implements ProzessCommand {

  private final WebClient webClient;
  private final CamundaVariableMapper variableMapper;

  public CamundaProcessApiClient(
      WebClient.Builder builder,
      @Value("${camunda.bpm.client.base-url:http://localhost:8081/engine-rest}") String baseUrl,
      CamundaVariableMapper variableMapper) {
    this.webClient = builder.baseUrl(baseUrl).build();
    this.variableMapper = variableMapper;
  }

  @Override
  public void start(String processDefinitionId, Map<String, Object> variables) {
    try {
      webClient
          .post()
          .uri("/process-definition/key/{id}/start", processDefinitionId)
          .body(
              BodyInserters.fromValue(
                  new CamundaProcessStartRequest(variableMapper.toCamundaFormat(variables))))
          .retrieve()
          .bodyToMono(Void.class)
          .block();
    } catch (WebClientResponseException | WebClientRequestException e) {
      throw new ProzessstartException(
          "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
              .formatted(processDefinitionId),
          e);
    }
  }
}
