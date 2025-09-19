package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.processinstance;

import static org.assertj.core.api.Assertions.assertThat;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.ProcessInstanceDto;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CamundaProcessInstanceMapperTest {

  private final CamundaProcessInstanceMapper classUnderTest =
      Mappers.getMapper(CamundaProcessInstanceMapper.class);

  @Test
  void should_map_all_fields_to_domain_without_fachdaten() {

    ProcessInstanceDto camundaProcessInstanceDto = new ProcessInstanceDto();
    camundaProcessInstanceDto.setId("ID123");
    camundaProcessInstanceDto.setBusinessKey("BK-123");

    Vorgang result = classUnderTest.toDomain(camundaProcessInstanceDto);

    SoftAssertions softly = new SoftAssertions();
    softly.assertThat(result.getId()).isNotNull().isEqualTo("ID123");
    softly.assertThat(result.getFachlicherSchluessel()).isNotNull().isEqualTo("BK-123");
    softly.assertThat(result.getFachdaten()).isNull();
    softly.assertAll();
  }

  @Test
  void should_return_null_when_source_is_null() {

    Vorgang result = classUnderTest.toDomain(null);

    assertThat(result).isNull();
  }
}
