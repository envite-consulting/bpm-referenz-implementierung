package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.processinstance;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.VorgangQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiClient;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessInstanceApi;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@WireMockTest
class CamundaProcessInstanceApiClientTest {

  private static final String CAMUNDA_BASE_PATH = "/engine-rest";

  private CamundaProcessInstanceApiClient classUnderTest;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmInfo) {

    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(wmInfo.getHttpBaseUrl() + CAMUNDA_BASE_PATH);

    ProcessInstanceApi processInstanceApi = new ProcessInstanceApi(apiClient);
    CamundaProcessInstanceMapper processInstanceMapper =
        Mappers.getMapper(CamundaProcessInstanceMapper.class);
    classUnderTest = new CamundaProcessInstanceApiClient(processInstanceApi, processInstanceMapper);
  }

  @Nested
  class QueryProcessInstance {

    @Test
    void should_query_process_instance_by_id_successfully() {
      String vorgangId = "vorgangId";
      String businessKey = "BK-123";

      stubFor(
          get(urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId)))
              .willReturn(
                  ok().withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                        {
                            "id": "%s",
                            "businessKey": "%s"
                        }
                        """
                              .formatted(vorgangId, businessKey))));

      Vorgang result = classUnderTest.queryById(vorgangId);

      assertThat(result).isNotNull();
      assertThat(result.getId()).isEqualTo(vorgangId);
      assertThat(result.getFachlicherSchluessel()).isEqualTo(businessKey);

      verify(
          getRequestedFor(
              urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId))));
    }

    @Test
    void should_throw_vorgang_not_found_exception_when_instance_does_not_exist() throws Exception {
      String vorgangId = "vorgangId";

      stubFor(
          get(urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId)))
              .willReturn(
                  notFound()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                            {
                              "type": "InvalidRequestException",
                              "message": "Process instance with id %s does not exist"
                            }
                            """
                              .formatted(vorgangId))));
      assertThatThrownBy(() -> classUnderTest.queryById(vorgangId))
          .isInstanceOf(VorgangNotFoundException.class)
          .hasMessageContaining(vorgangId);

      verify(
          getRequestedFor(
              urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId))));
    }

    @Test
    void should_throw_vorgang_query_exception_on_400_bad_request_error() throws Exception {
      String vorgangId = "vorgangId";

      stubFor(
          get(urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId)))
              .willReturn(
                  badRequest()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                            {
                              "type": "RestException",
                              "message": "Invalid process instance id format"
                            }
                            """)));

      assertThatThrownBy(() -> classUnderTest.queryById(vorgangId))
          .isInstanceOf(VorgangQueryException.class)
          .hasMessageContaining(vorgangId);

      verify(
          getRequestedFor(
              urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId))));
    }

    @Test
    void should_throw_vorgang_query_exception_on_500_internal_server_error() {
      String vorgangId = "vorgangId";

      stubFor(
          get(urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId)))
              .willReturn(
                  serverError()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                        {
                          "type": "ProcessEngineException",
                          "message": "Internal server error"
                        }
                        """)));

      assertThatThrownBy(() -> classUnderTest.queryById(vorgangId))
          .isInstanceOf(VorgangQueryException.class)
          .hasMessageContaining(vorgangId);

      verify(
          getRequestedFor(
              urlEqualTo("%s/process-instance/%s".formatted(CAMUNDA_BASE_PATH, vorgangId))));
    }
  }

  @Nested
  class QueryAllProcessInstances {

    @Test
    void should_query_all_process_instances_successfully() throws Exception {

      stubFor(
          get(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH)))
              .willReturn(
                  ok().withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                        [
                          {
                            "id": "pi-1",
                            "businessKey": "BK-001"
                          },
                          {
                            "id": "pi-2",
                            "businessKey": "BK-002"
                          }
                        ]
                        """)));

      List<Vorgang> result = classUnderTest.queryAll();

      assertThat(result).hasSize(2);
      assertThat(result.get(0).getId()).isEqualTo("pi-1");
      assertThat(result.get(0).getFachlicherSchluessel()).isEqualTo("BK-001");
      assertThat(result.get(1).getId()).isEqualTo("pi-2");
      assertThat(result.get(1).getFachlicherSchluessel()).isEqualTo("BK-002");

      verify(getRequestedFor(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH))));
    }

    @Test
    void should_query_all_process_instances_with_empty_result() {
      stubFor(
          get(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH)))
              .willReturn(ok().withHeader("Content-Type", "application/json").withBody("[]")));

      List<Vorgang> result = classUnderTest.queryAll();

      assertThat(result).isEmpty();

      verify(getRequestedFor(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH))));
    }

    @Test
    void should_throw_vorgang_query_exception_on_500_server_error() {
      stubFor(
          get(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH)))
              .willReturn(
                  serverError()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                        {
                          "type": "ProcessEngineException",
                          "message": "Database connection failed"
                        }
                        """)));

      assertThatThrownBy(() -> classUnderTest.queryAll())
          .isInstanceOf(VorgangQueryException.class)
          .hasMessageContaining("Vorgaenge konnten nicht abgerufen werden.");

      verify(getRequestedFor(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH))));
    }

    @Test
    void should_throw_vorgang_query_exception_on_400_bad_request() {
      stubFor(
          get(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH)))
              .willReturn(
                  badRequest()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                            {
                              "type": "RestException",
                              "message": "Invalid query parameters"
                            }
                            """)));

      assertThatThrownBy(() -> classUnderTest.queryAll())
          .isInstanceOf(VorgangQueryException.class)
          .hasMessageContaining("Vorgaenge konnten nicht abgerufen werden.");

      verify(getRequestedFor(urlEqualTo("%s/process-instance".formatted(CAMUNDA_BASE_PATH))));
    }
  }
}
