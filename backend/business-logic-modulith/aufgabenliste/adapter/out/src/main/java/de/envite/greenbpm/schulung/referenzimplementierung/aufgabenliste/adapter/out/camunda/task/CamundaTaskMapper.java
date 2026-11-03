package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.TaskWithAttachmentAndCommentDto;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
interface CamundaTaskMapper {

  @Mapping(source = "camundaTaskDto.assignee", target = "bearbeiter")
  @Mapping(source = "camundaTaskDto.created", target = "erstelldatum")
  @Mapping(source = "camundaTaskDto.formKey", target = "formularreferenz")
  @Mapping(source = "camundaTaskDto.priority", target = "prioritaet")
  @Mapping(source = "camundaTaskDto.processInstanceId", target = "vorgangId")
  Aufgabe toDomain(TaskWithAttachmentAndCommentDto camundaTaskDto, String arbeitspaket);

  default LocalDateTime map(Date date) {
    return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
