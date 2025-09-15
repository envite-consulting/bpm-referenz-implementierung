package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.process;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.ProzessstartException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.ProzessCommand;
import java.util.Map;

import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessDefinitionApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.StartProcessInstanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CamundaProcessApiClient implements ProzessCommand {

  private final ProcessDefinitionApi processDefinitionApi;

  @Override
  public void start(String processDefinitionId, Map<String, Object> variables) {
    try {
      StartProcessInstanceDto startProcessInstanceDto = new StartProcessInstanceDto();
      startProcessInstanceDto.setVariables(CamundaVariableMapper.toDto(variables));
      processDefinitionApi.startProcessInstanceByKey(processDefinitionId, startProcessInstanceDto);

    } catch (ApiException e) {
      throw new ProzessstartException(
          "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
              .formatted(processDefinitionId),
          e);
    }
  }
}
