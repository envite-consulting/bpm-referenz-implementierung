package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.process;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.ProzessstartException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.out.ProzessCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessDefinitionApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.StartProcessInstanceDto;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CamundaProcessApiClient implements ProzessCommand {

  private final ProcessDefinitionApi processDefinitionApi;
  private final CamundaVariableMapper camundaVariableMapper;

  @Override
  public void start(String processDefinitionId, String businessKey, Map<String, Object> variables)
      throws ProzessstartException {
    try {
      StartProcessInstanceDto startProcessInstanceDto = new StartProcessInstanceDto();
      startProcessInstanceDto.setVariables(camundaVariableMapper.toDto(variables));
      startProcessInstanceDto.setBusinessKey(businessKey);
      processDefinitionApi.startProcessInstanceByKey(processDefinitionId, startProcessInstanceDto);

    } catch (ApiException e) {
      throw new ProzessstartException(
          "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
              .formatted(processDefinitionId),
          e);
    }
  }
}
