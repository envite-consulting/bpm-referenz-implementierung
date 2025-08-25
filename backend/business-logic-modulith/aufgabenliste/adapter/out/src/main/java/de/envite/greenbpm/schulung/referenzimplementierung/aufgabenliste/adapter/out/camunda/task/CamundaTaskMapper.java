package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
interface CamundaTaskMapper {

  @Mapping(source = "assignee", target = "bearbeiter")
  @Mapping(source = "created", target = "erstelldatum", qualifiedByName = "stringToLocalDateTime")
  @Mapping(source = "formKey", target = "formularreferenz")
  Aufgabe toDomain(CamundaTaskResource camundaTaskResource);

  @Named("stringToLocalDateTime")
  default LocalDateTime stringToLocalDateTime(String timestamp) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    OffsetDateTime odt = OffsetDateTime.parse(timestamp, formatter);
    return odt.toLocalDateTime();
  }
}
