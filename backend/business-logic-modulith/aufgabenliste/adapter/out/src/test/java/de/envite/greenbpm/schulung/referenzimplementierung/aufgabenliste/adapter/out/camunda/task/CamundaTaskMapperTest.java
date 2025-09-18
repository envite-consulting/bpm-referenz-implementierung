package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.TaskWithAttachmentAndCommentDto;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CamundaTaskMapperTest {

  private final CamundaTaskMapper classUnderTest = Mappers.getMapper(CamundaTaskMapper.class);

  @Test
  void should_map_all_fields_to_domain() {
    TaskWithAttachmentAndCommentDto camundaTaskDto = new TaskWithAttachmentAndCommentDto();

    camundaTaskDto.id("ID123");
    camundaTaskDto.name("My Task");
    camundaTaskDto.assignee("testUser1");
    LocalDate localDate = LocalDate.of(2025, 8, 21);
    Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    camundaTaskDto.created(date);
    camundaTaskDto.formKey("Form Key");

    Aufgabe result = classUnderTest.toDomain(camundaTaskDto);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.getId()).isEqualTo(camundaTaskDto.getId());
    softAssertions.assertThat(result.getName()).isEqualTo(camundaTaskDto.getName());
    softAssertions.assertThat(result.getBearbeiter()).isEqualTo(camundaTaskDto.getAssignee());
    softAssertions
        .assertThat(result.getErstelldatum())
        .isEqualTo(LocalDateTime.of(2025, 8, 21, 0, 0));
    softAssertions.assertThat(result.getFormularreferenz()).isEqualTo(camundaTaskDto.getFormKey());
    softAssertions.assertAll();
  }
}
