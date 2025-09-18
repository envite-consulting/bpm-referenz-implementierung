package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.processinstance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessInstanceApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.ProcessInstanceDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CamundaProcessInstanceApiClientTest {

    private ProcessInstanceApi processInstanceApiMock;
    private CamundaProcessInstanceMapper processInstanceMapperMock;

    private CamundaProcessInstanceApiClient classUnderTest;

    @BeforeEach
    void setUp() {
        processInstanceApiMock = mock(ProcessInstanceApi.class);
        processInstanceMapperMock = mock(CamundaProcessInstanceMapper.class);

        classUnderTest = new CamundaProcessInstanceApiClient(processInstanceApiMock, processInstanceMapperMock);
    }

    @Nested
    class QueryProcessInstance {

        @Test
        void should_query_process_instance_by_id_successfully() throws Exception {
            String vorgangId = "vorgangId";
            ProcessInstanceDto dto = new ProcessInstanceDto();
            dto.setId(vorgangId);
            dto.setBusinessKey("BK-123");

            Vorgang expected = new Vorgang("vorgangId", "BK-123");

            when(processInstanceApiMock.getProcessInstance(vorgangId)).thenReturn(dto);
            when(processInstanceMapperMock.toDomain(dto)).thenReturn(expected);

            Vorgang actual = classUnderTest.queryById(vorgangId);

            assertThat(actual).isEqualTo(expected);
            verify(processInstanceApiMock).getProcessInstance(vorgangId);
            verify(processInstanceMapperMock).toDomain(dto);
        }

        @Test
        void should_throw_vorgang_not_found_exception_when_instance_does_not_exist() throws Exception {
            String vorgangId = "vorgangId";
            ApiException apiException = new ApiException(404, "Not Found");

            when(processInstanceApiMock.getProcessInstance(vorgangId)).thenThrow(apiException);

            assertThatThrownBy(() -> classUnderTest.queryById(vorgangId))
                    .isInstanceOf(VorgangNotFoundException.class)
                    .hasMessageContaining(vorgangId);

            verify(processInstanceApiMock).getProcessInstance(vorgangId);
            verifyNoInteractions(processInstanceMapperMock);
        }

        @Test
        void should_throw_vorgang_query_exception_on_request_error() throws Exception {
            String vorgangId = "vorgangId";
            ApiException apiException = new ApiException(400, "Bad Request");

            when(processInstanceApiMock.getProcessInstance(vorgangId)).thenThrow(apiException);

            assertThatThrownBy(() -> classUnderTest.queryById(vorgangId))
                    .isInstanceOf(VorgangQueryException.class)
                    .hasMessageContaining(vorgangId);

            verify(processInstanceApiMock).getProcessInstance(vorgangId);
            verifyNoInteractions(processInstanceMapperMock);
        }
    }

    @Nested
    class QueryAllProcessInstances {

        @Test
        void should_query_all_process_instances_successfully() throws Exception {
            ProcessInstanceDto dto1 = new ProcessInstanceDto();
            dto1.setId("pi1");
            ProcessInstanceDto dto2 = new ProcessInstanceDto();
            dto2.setId("pi2");

            Vorgang mapped1 = new Vorgang("pi1", "BK1");
            Vorgang mapped2 = new Vorgang("pi2", "BK2");

            when(processInstanceApiMock.getProcessInstances(
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null))
                    .thenReturn(List.of(dto1, dto2));

            when(processInstanceMapperMock.toDomain(dto1)).thenReturn(mapped1);
            when(processInstanceMapperMock.toDomain(dto2)).thenReturn(mapped2);

            List<Vorgang> actual = classUnderTest.queryAll();

            assertThat(actual).containsExactly(mapped1, mapped2);
            verify(processInstanceApiMock).getProcessInstances(
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null);
        }

        @Test
        void should_throw_vorgang_query_exception_on_request_error() throws Exception {
            ApiException apiException = new ApiException(500, "Server Error");

            when(processInstanceApiMock.getProcessInstances(
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null,
                    null, null, null))
                    .thenThrow(apiException);

            assertThatThrownBy(() -> classUnderTest.queryAll())
                    .isInstanceOf(VorgangQueryException.class)
                    .hasMessageContaining("Vorgaenge konnten nicht abgerufen werden.");
        }
    }
}
