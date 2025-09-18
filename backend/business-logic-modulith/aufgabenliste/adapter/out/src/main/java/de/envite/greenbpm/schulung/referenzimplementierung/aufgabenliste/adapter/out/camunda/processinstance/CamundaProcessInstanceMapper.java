package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.processinstance;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.ProcessInstanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper(componentModel = "spring")
interface CamundaProcessInstanceMapper {

  Vorgang toDomain(ProcessInstanceDto dto);

  @ObjectFactory
  default Vorgang createVorgang(ProcessInstanceDto dto) {
    return new Vorgang(dto.getId(), dto.getBusinessKey());
  }
}
