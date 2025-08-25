package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenQuery;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
class CamundaTaskApiClient implements AufgabenCommand, AufgabenQuery {

  private final WebClient webClient;
  private final CamundaTaskMapper taskMapper;
  private final CamundaVariableMapper camundaVariableMapper;

  public CamundaTaskApiClient(
      WebClient.Builder builder,
      CamundaTaskMapper taskMapper,
      @Value("${camunda.bpm.client.base-url:http://localhost:8081/engine-rest}") String baseUrl,
      CamundaVariableMapper camundaVariableMapper) {
    this.taskMapper = taskMapper;
    this.webClient = builder.baseUrl(baseUrl).build();
    this.camundaVariableMapper = camundaVariableMapper;
  }

  @Override
  public Aufgabe queryById(String taskId) throws AufgabeQueryException {
    try {
      CamundaTaskResource resource =
          webClient
              .get()
              .uri("/task/{id}", taskId)
              .retrieve()
              .bodyToMono(CamundaTaskResource.class)
              .block();

      return taskMapper.toDomain(resource);
    } catch (WebClientResponseException e) {
        if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
            throw new AufgabeNotFoundException(
                    "Aufgabe mit ID %s konnte nicht gefunden werden.".formatted(taskId), e
            );
        }
        throw new AufgabeQueryException(
                "Aufgabe mit ID %s konnte nicht abgerufen werden.".formatted(taskId), e
        );
    } catch (WebClientRequestException e) {
        throw new AufgabeQueryException(
                "Aufgabe mit ID %s konnte nicht abgerufen werden.".formatted(taskId), e
        );
    }
  }

  @Override
  public List<Aufgabe> queryAll() throws AufgabeQueryException {
    try {
      List<CamundaTaskResource> resources =
          webClient
              .get()
              .uri("/task")
              .retrieve()
              .bodyToFlux(CamundaTaskResource.class)
              .collectList()
              .block();

      assert resources != null;
      return resources.stream().map(taskMapper::toDomain).toList();
    } catch (WebClientResponseException | WebClientRequestException e) {
      throw new AufgabeQueryException("Aufgaben konnten nicht abgerufen werden.", e);
    }
  }

  @Override
  public void claim(String taskId, String userId) throws AufgabeUpdateException {
    try {
      webClient
          .post()
          .uri("/task/{id}/claim", taskId)
          .body(BodyInserters.fromValue(new CamundaTaskClaimRequest(userId)))
          .retrieve()
          .bodyToMono(Void.class)
          .block();
    } catch (WebClientResponseException | WebClientRequestException e) {
      throw new AufgabeUpdateException(
          "Aufgabe mit ID %s konnte dem Benutzer %s nicht zugewiesen werden."
              .formatted(taskId, userId),
          e);
    }
  }

  @Override
  public void completeWithVariables(String taskId, Map<String, Object> variables)
      throws AufgabeUpdateException {
    try {
      webClient
          .post()
          .uri("/task/{id}/complete", taskId)
          .body(
              BodyInserters.fromValue(
                  new CamundaTaskCompleteRequest(camundaVariableMapper.toCamundaFormat(variables))))
          .retrieve()
          .bodyToMono(Void.class)
          .block();
    } catch (WebClientResponseException | WebClientRequestException e) {
      throw new AufgabeUpdateException(
          "Aufgabe mit ID %s konnte nicht mit den Variablen %s abgeschlossen werden."
              .formatted(taskId, variables),
          e);
    }
  }

  @Override
  public void unclaim(String taskId) throws AufgabeUpdateException {
    try {
      webClient.post().uri("/task/{id}/unclaim", taskId).retrieve().bodyToMono(Void.class).block();
    } catch (WebClientResponseException | WebClientRequestException e) {
      throw new AufgabeUpdateException(
          "Aufgabe mit ID %s konnte nicht abgegeben werden.".formatted(taskId), e);
    }
  }
}
