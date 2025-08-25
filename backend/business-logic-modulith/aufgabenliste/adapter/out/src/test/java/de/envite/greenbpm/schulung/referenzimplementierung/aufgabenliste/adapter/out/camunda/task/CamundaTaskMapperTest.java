package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import java.time.LocalDateTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CamundaTaskMapperTest {

  private final CamundaTaskMapper classUnderTest = Mappers.getMapper(CamundaTaskMapper.class);

  @Test
  void should_map_all_fields_to_domain() {
    CamundaTaskResource camundaTaskResource =
        new CamundaTaskResource(
            "ID123", "My Task", "Test User 1", "2025-08-21T00:00:00.000+0200", "Form Key");

    Aufgabe result = classUnderTest.toDomain(camundaTaskResource);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.getId()).isEqualTo(camundaTaskResource.id());
    softAssertions.assertThat(result.getName()).isEqualTo(camundaTaskResource.name());
    softAssertions.assertThat(result.getBearbeiter()).isEqualTo(camundaTaskResource.assignee());
    softAssertions
        .assertThat(result.getErstelldatum())
        .isEqualTo(LocalDateTime.of(2025, 8, 21, 0, 0));
    softAssertions
        .assertThat(result.getFormularreferenz())
        .isEqualTo(camundaTaskResource.formKey());
    softAssertions.assertAll();
  }
}
