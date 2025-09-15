package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.AufgabenQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.TaskApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.CompleteTaskDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.TaskWithAttachmentAndCommentDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.UserIdDto;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CamundaTaskApiClient implements AufgabenCommand, AufgabenQuery {

  private final CamundaTaskMapper taskMapper;
  private final TaskApi taskApi;

  @Override
  public Aufgabe queryById(String taskId) throws AufgabeQueryException {
    try {
      TaskWithAttachmentAndCommentDto taskDto = taskApi.getTask(taskId);

      return taskMapper.toDomain(taskDto);
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
  public List<Aufgabe> queryAll() throws AufgabeQueryException {
    try {

      List<TaskWithAttachmentAndCommentDto> tasksDto =
          taskApi.getTasks(
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null);

      return tasksDto.stream().map(taskMapper::toDomain).toList();
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
      completeTaskDto.setVariables(CamundaVariableMapper.toDto(variables));
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
}
