package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.processinstance;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.VorgangQuery;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessInstanceApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.ProcessInstanceDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CamundaProcessInstanceApiClient implements VorgangQuery {

  private final ProcessInstanceApi processInstanceApi;
  private final CamundaProcessInstanceMapper processInstanceMapper;

  @Override
  public Vorgang queryById(String processInstanceId)
      throws VorgangNotFoundException, VorgangQueryException {
    try {
      ProcessInstanceDto processInstanceDto =
          processInstanceApi.getProcessInstance(processInstanceId);

      return processInstanceMapper.toDomain(processInstanceDto);
    } catch (ApiException e) {

      if (e.getCode() == HttpStatus.NOT_FOUND.value()) {
        throw new VorgangNotFoundException(
            "Vorgang mit ID %s konnte nicht gefunden werden.".formatted(processInstanceId), e);
      }
      throw new VorgangQueryException(
          "Vorgang mit ID %s konnte nicht abgerufen werden.".formatted(processInstanceId), e);
    }
  }

  @Override
  public List<Vorgang> queryAll() throws VorgangQueryException {

    try {

      List<ProcessInstanceDto> processInstancesDto =
          processInstanceApi.getProcessInstances(
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null);

      return processInstancesDto.stream().map(processInstanceMapper::toDomain).toList();
    } catch (ApiException e) {
      throw new VorgangQueryException("Vorgaenge konnten nicht abgerufen werden.", e);
    }
  }
}
