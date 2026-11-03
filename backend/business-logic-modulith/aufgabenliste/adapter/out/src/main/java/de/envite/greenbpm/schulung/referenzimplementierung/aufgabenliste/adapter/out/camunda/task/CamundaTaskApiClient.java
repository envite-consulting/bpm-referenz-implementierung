package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.TaskApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.TaskVariableApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.CompleteTaskDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.TaskWithAttachmentAndCommentDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.UserIdDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CamundaTaskApiClient implements AufgabenCommand, AufgabenQuery {

  private static final String ARBEITSPAKET_VARIABLE = "arbeitspaket";

  private final CamundaTaskMapper taskMapper;
  private final TaskApi taskApi;
  private final TaskVariableApi taskVariableApi;
  private final CamundaVariableMapper camundaVariableMapper;

  @Override
  public Aufgabe queryById(String taskId) throws AufgabeNotFoundException, AufgabeQueryException {
    try {
      TaskWithAttachmentAndCommentDto taskDto = taskApi.getTask(taskId);

      return toDomain(taskDto);
    } catch (ApiException e) {

      if (e.getCode() == HttpStatus.NOT_FOUND.value()) {
        throw new AufgabeNotFoundException(
            "Aufgabe mit ID %s konnte nicht gefunden werden.".formatted(taskId), e);
      }
      throw new AufgabeQueryException(
          "Aufgabe mit ID %s konnte nicht abgerufen werden.".formatted(taskId), e);
    }
  }

  @Override
  public List<Aufgabe> queryAllByVorgang(String businessId) throws AufgabeQueryException {
    return queryAll(null, businessId);
  }

  @Override
  public List<Aufgabe> queryAllByProzessinstanz(String prozessinstanzId)
      throws AufgabeQueryException {
    return queryAll(prozessinstanzId, null);
  }

  private List<Aufgabe> queryAll(String prozessinstanzId, String businessId)
      throws AufgabeQueryException {
    try {

      List<TaskWithAttachmentAndCommentDto> tasksDto =
          taskApi.getTasks(
              null, null, prozessinstanzId, null, businessId, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null);

      List<Aufgabe> aufgaben = new ArrayList<>();
      for (TaskWithAttachmentAndCommentDto taskDto : tasksDto) {
        aufgaben.add(toDomain(taskDto));
      }
      return aufgaben;
    } catch (ApiException e) {
      throw new AufgabeQueryException("Aufgaben konnten nicht abgerufen werden.", e);
    }
  }

  @Override
  public void claim(String taskId, String userId) throws AufgabeUpdateException {
    try {
      UserIdDto userIdDto = new UserIdDto();
      userIdDto.setUserId(userId);
      taskApi.claim(taskId, userIdDto);

    } catch (ApiException e) {
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
      CompleteTaskDto completeTaskDto = new CompleteTaskDto();
      completeTaskDto.setVariables(camundaVariableMapper.toDto(variables));
      taskApi.complete(taskId, completeTaskDto);

    } catch (ApiException e) {
      throw new AufgabeUpdateException(
          "Aufgabe mit ID %s konnte nicht mit den Variablen %s abgeschlossen werden."
              .formatted(taskId, variables),
          e);
    }
  }

  @Override
  public void unclaim(String taskId) throws AufgabeUpdateException {
    try {
      taskApi.unclaim(taskId);

    } catch (ApiException e) {
      throw new AufgabeUpdateException(
          "Aufgabe mit ID %s konnte nicht abgegeben werden.".formatted(taskId), e);
    }
  }

  private Aufgabe toDomain(TaskWithAttachmentAndCommentDto taskDto) throws ApiException {
    return taskMapper.toDomain(taskDto, queryArbeitspaket(taskDto.getId()));
  }

  private String queryArbeitspaket(String taskId) throws ApiException {
    try {
      return Objects.toString(
          taskVariableApi.getTaskVariable(taskId, ARBEITSPAKET_VARIABLE, false).getValue(), null);
    } catch (ApiException e) {
      if (e.getCode() == HttpStatus.NOT_FOUND.value()) {
        return null;
      }
      throw e;
    }
  }
}
