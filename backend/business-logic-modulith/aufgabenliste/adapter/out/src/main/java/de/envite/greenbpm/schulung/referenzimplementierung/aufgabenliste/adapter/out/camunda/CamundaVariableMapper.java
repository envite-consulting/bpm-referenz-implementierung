package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda;

import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.VariableValueDto;
import java.util.Map;
import java.util.stream.Collectors;

public final class CamundaVariableMapper {

  public static Map<String, VariableValueDto> toDto(Map<String, Object> variables) {

    return variables.entrySet().stream()
        .collect(
            Collectors.toMap(
                Map.Entry::getKey, entry -> new VariableValueDto().value(entry.getValue())));
  }

  private CamundaVariableMapper() {}
}
