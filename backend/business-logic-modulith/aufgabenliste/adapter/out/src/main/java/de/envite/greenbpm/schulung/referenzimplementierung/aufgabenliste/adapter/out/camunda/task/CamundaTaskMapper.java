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

  @Mapping(source = "assignee", target = "bearbeiter")
  @Mapping(source = "created", target = "erstelldatum")
  @Mapping(source = "formKey", target = "formularreferenz")
  Aufgabe toDomain(TaskWithAttachmentAndCommentDto camundaTaskDto);

  default LocalDateTime map(Date date) {
    return date == null ? null : LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
  }
}
