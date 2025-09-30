package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.process;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.ProzessstartException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiClient;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessDefinitionApi;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

@WireMockTest
class CamundaProcessApiClientTest {

  private static final String CAMUNDA_BASE_PATH = "/engine-rest";

  private CamundaProcessApiClient classUnderTest;

  @BeforeEach
  void setUp(com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo wmInfo) {

    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(wmInfo.getHttpBaseUrl() + CAMUNDA_BASE_PATH);

    ProcessDefinitionApi processDefinitionApi = new ProcessDefinitionApi(apiClient);
    CamundaVariableMapper variableMapper = Mappers.getMapper(CamundaVariableMapper.class);
    classUnderTest = new CamundaProcessApiClient(processDefinitionApi, variableMapper);
  }

  @Nested
  class StartProcess {

    @Test
    void should_start_process_successfully_with_variables_and_business_key() {
      String processDefinitionKey = "testProcess";
      String businessKey = "BK-123";
      Map<String, Object> variables = Map.of("Variable1", "Value1", "Variable2", 42);

      stubFor(
          post(urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .willReturn(
                  ok().withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                          {
                            "id": "instance-123"
                          }
                          """)));

      classUnderTest.start(processDefinitionKey, businessKey, variables);

      verify(
          postRequestedFor(
                  urlEqualTo(
                      "%s/process-definition/key/%s/start"
                          .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
              .withRequestBody(matchingJsonPath("$.businessKey", equalTo(businessKey)))
              .withRequestBody(matchingJsonPath("$.variables.Variable1.value", equalTo("Value1")))
              .withRequestBody(matchingJsonPath("$.variables.Variable2.value", equalTo("42"))));
    }

    @Test
    void should_start_process_successfully_with_empty_variables() {
      String processDefinitionKey = "testProcess";
      String businessKey = "BK-456";
      Map<String, Object> emptyVariables = Map.of();

      stubFor(
          post(urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .willReturn(
                  ok().withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                          {
                            "id": "instance-456"
                          }
                          """)));

      classUnderTest.start(processDefinitionKey, businessKey, emptyVariables);

      verify(
          postRequestedFor(
                  urlEqualTo(
                      "%s/process-definition/key/%s/start"
                          .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
              .withRequestBody(matchingJsonPath("$.businessKey", equalTo(businessKey)))
              .withRequestBody(matchingJsonPath("$.variables[?(@.length() == 0)]")));
    }

    @Test
    void should_throw_prozessstart_exception_on_404_not_found() {
      String processDefinitionKey = "unknownProcess";
      String businessKey = "BK-999";
      Map<String, Object> variables = Map.of();

      stubFor(
          post(urlEqualTo(
                  CAMUNDA_BASE_PATH + "/process-definition/key/" + processDefinitionKey + "/start"))
              .willReturn(
                  notFound()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                          {
                            "type": "InvalidRequestException",
                            "message": "No matching process definition with key: unknownProcess"
                          }
                          """)));

      assertThatThrownBy(() -> classUnderTest.start(processDefinitionKey, businessKey, variables))
          .isInstanceOf(ProzessstartException.class)
          .hasMessageContaining(
              "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
                  .formatted(processDefinitionKey))
          .hasCauseInstanceOf(ApiException.class);

      verify(
          postRequestedFor(
              urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey))));
    }

    @Test
    void should_throw_prozessstart_exception_on_400_bad_request() {
      String processDefinitionKey = "testProcess";
      String businessKey = "BK-BAD";
      Map<String, Object> variables = Map.of("invalidVar", "value");

      stubFor(
          post(urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .willReturn(
                  badRequest()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                          {
                            "type": "RestException",
                            "message": "Cannot instantiate process definition: Invalid variable format"
                          }
                          """)));

      assertThatThrownBy(() -> classUnderTest.start(processDefinitionKey, businessKey, variables))
          .isInstanceOf(ProzessstartException.class)
          .hasMessageContaining(
              "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
                  .formatted(processDefinitionKey));

      verify(
          postRequestedFor(
              urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey))));
    }

    @Test
    void should_throw_prozessstart_exception_on_500_internal_server_error() {
      String processDefinitionKey = "testProcess";
      String businessKey = "BK-ERROR";
      Map<String, Object> variables = Map.of();

      stubFor(
          post(urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .willReturn(
                  serverError()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                          {
                            "type": "ProcessEngineException",
                            "message": "Internal server error occurred"
                          }
                          """)));

      assertThatThrownBy(() -> classUnderTest.start(processDefinitionKey, businessKey, variables))
          .isInstanceOf(ProzessstartException.class)
          .hasMessageContaining(
              "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
                  .formatted(processDefinitionKey));

      verify(
          postRequestedFor(
              urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey))));
    }

    @Test
    void should_send_correct_json_structure() {
      String processDefinitionKey = "structureTest";
      String businessKey = "BK-STRUCT";
      Map<String, Object> variables = Map.of("testVar", "testValue");

      stubFor(
          post(urlEqualTo(
                  "%s/process-definition/key/%s/start"
                      .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .willReturn(ok().withBody("{}")));

      classUnderTest.start(processDefinitionKey, businessKey, variables);

      verify(
          postRequestedFor(
                  urlEqualTo(
                      "%s/process-definition/key/%s/start"
                          .formatted(CAMUNDA_BASE_PATH, processDefinitionKey)))
              .withRequestBody(
                  equalToJson(
                      """
                              {
                                "businessKey": "BK-STRUCT",
                                "variables": {
                                  "testVar": {
                                    "value": "testValue",
                                    "valueInfo" : { }
                                  }
                                }
                              }
                              """,
                      true,
                      false)));
    }
  }
}
