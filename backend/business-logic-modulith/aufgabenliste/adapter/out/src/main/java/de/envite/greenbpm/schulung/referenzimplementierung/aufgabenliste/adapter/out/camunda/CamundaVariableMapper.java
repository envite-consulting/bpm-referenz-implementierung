package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CamundaVariableMapper {

  public Map<String, Object> toCamundaFormat(Map<String, Object> variables) {

    Map<String, Object> camundaVariables = new HashMap<>();
    variables.forEach(
        (key, value) -> {
          Map<String, Object> varSpec = new HashMap<>();
          varSpec.put("value", value);
          varSpec.put("type", mapJavaTypeToCamundaType(value));
          camundaVariables.put(key, varSpec);
        });

    return camundaVariables;
  }

  private static String mapJavaTypeToCamundaType(Object value) {
    if (value instanceof String) return "String";
    if (value instanceof Integer) return "Integer";
    if (value instanceof Long) return "Long";
    if (value instanceof Boolean) return "Boolean";
    if (value instanceof Double || value instanceof Float) return "Double";
    return "Object";
  }
}
