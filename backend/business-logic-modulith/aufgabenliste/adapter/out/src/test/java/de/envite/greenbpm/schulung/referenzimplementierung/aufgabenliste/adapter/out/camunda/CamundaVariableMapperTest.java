package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda;

import static org.assertj.core.api.Assertions.assertThat;

import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.VariableValueDto;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CamundaVariableMapperTest {

  private final CamundaVariableMapper mapper = Mappers.getMapper(CamundaVariableMapper.class);

  @Test
  void should_map_variables_to_variableValueDto() {

    Map<String, Object> input = Map.of("varString", "hello", "varInt", 42, "varBoolean", true);

    Map<String, VariableValueDto> result = mapper.toDto(input);

    assertThat(result).hasSize(3);

    VariableValueDto stringVar = result.get("varString");
    assertThat(stringVar).isNotNull();
    assertThat(stringVar.getValue()).isEqualTo("hello");

    VariableValueDto intVar = result.get("varInt");
    assertThat(intVar).isNotNull();
    assertThat(intVar.getValue()).isEqualTo(42);

    VariableValueDto booleanVar = result.get("varBoolean");
    assertThat(booleanVar).isNotNull();
    assertThat(booleanVar.getValue()).isEqualTo(true);
  }

  @Test
  void should_return_empty_map_when_input_is_empty() {

    Map<String, Object> input = Map.of();

    Map<String, VariableValueDto> result = mapper.toDto(input);

    assertThat(result).isEmpty();
  }
}
