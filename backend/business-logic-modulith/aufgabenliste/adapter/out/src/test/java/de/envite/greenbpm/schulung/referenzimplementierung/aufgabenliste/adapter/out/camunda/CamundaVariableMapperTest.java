package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CamundaVariableMapperTest {

  private CamundaVariableMapper mapper;

  @BeforeEach
  void setUp() {
    mapper = new CamundaVariableMapper();
  }

  @Test
  void should_map_all_supported_types_correctly() {
    Map<String, Object> input =
        Map.of(
            "stringVar",
            "text",
            "intVar",
            42,
            "longVar",
            123456789L,
            "boolVar",
            true,
            "doubleVar",
            3.14,
            "floatVar",
            2.71f,
            "objectVar",
            new Object());

    Map<String, Object> result = mapper.toCamundaFormat(input);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions
        .assertThat(result)
        .containsKeys(
            "stringVar", "intVar", "longVar", "boolVar", "doubleVar", "floatVar", "objectVar");

    softAssertions
        .assertThat(((Map<?, ?>) result.get("stringVar")).get("type"))
        .isEqualTo("String");
    softAssertions.assertThat(((Map<?, ?>) result.get("stringVar")).get("value")).isEqualTo("text");

    softAssertions.assertThat(((Map<?, ?>) result.get("intVar")).get("type")).isEqualTo("Integer");
    softAssertions.assertThat(((Map<?, ?>) result.get("intVar")).get("value")).isEqualTo(42);

    softAssertions.assertThat(((Map<?, ?>) result.get("longVar")).get("type")).isEqualTo("Long");
    softAssertions
        .assertThat(((Map<?, ?>) result.get("longVar")).get("value"))
        .isEqualTo(123456789L);

    softAssertions.assertThat(((Map<?, ?>) result.get("boolVar")).get("type")).isEqualTo("Boolean");
    softAssertions.assertThat(((Map<?, ?>) result.get("boolVar")).get("value")).isEqualTo(true);

    softAssertions
        .assertThat(((Map<?, ?>) result.get("doubleVar")).get("type"))
        .isEqualTo("Double");
    softAssertions.assertThat(((Map<?, ?>) result.get("doubleVar")).get("value")).isEqualTo(3.14);

    softAssertions.assertThat(((Map<?, ?>) result.get("floatVar")).get("type")).isEqualTo("Double");
    softAssertions.assertThat(((Map<?, ?>) result.get("floatVar")).get("value")).isEqualTo(2.71f);

    softAssertions
        .assertThat(((Map<?, ?>) result.get("objectVar")).get("type"))
        .isEqualTo("Object");
    softAssertions
        .assertThat(((Map<?, ?>) result.get("objectVar")).get("value"))
        .isInstanceOf(Object.class);

    softAssertions.assertAll();
  }

  @Test
  void should_return_empty_map_for_empty_input() {
    Map<String, Object> result = mapper.toCamundaFormat(Map.of());
    assertThat(result).isEmpty();
  }
}
