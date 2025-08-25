package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.process;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.ProzessstartException;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

class CamundaProcessApiClientTest {

  private static WireMockServer wireMockServer;
  private CamundaVariableMapper variableMapper;

  private CamundaProcessApiClient classUnderTest;

  @BeforeEach
  void setUpBeforeClass() {

    variableMapper = mock(CamundaVariableMapper.class);

    wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    wireMockServer.start();
    WireMock.configureFor("localhost", wireMockServer.port());

    String baseUrl = "http://localhost:%s".formatted(wireMockServer.port());

    classUnderTest = new CamundaProcessApiClient(WebClient.builder(), baseUrl, variableMapper);
  }

  @AfterEach
  void tearDownAfterClass() {
    wireMockServer.stop();
  }

  @Nested
  class StartProcess {

    @Test
    void should_start_process_successfully_with_variables() {
      String processDefinitionId = "processId";
      Map<String, Object> variables = Map.of("Variable1", "Value1", "Variable2", 2);

      Map<String, Object> mappedVariables =
          Map.of(
              "Variable1", Map.of("value", "Value1", "type", "String"),
              "Variable2", Map.of("value", 2, "type", "Integer"));

      when(variableMapper.toCamundaFormat(variables)).thenReturn(mappedVariables);

      String expectedRequestBody =
          """
                  {
                    "variables": {
                      "Variable1": { "value": "Value1", "type": "String" },
                      "Variable2": { "value": 2, "type": "Integer" }
                    }
                  }
                  """;

      stubFor(
          post(urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson(expectedRequestBody, true, true))
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody("{\"id\": \"anId\"}")));

      classUnderTest.start(processDefinitionId, variables);

      verify(
          postRequestedFor(
                  urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson(expectedRequestBody, true, true)));
    }

    @Test
    void should_start_process_successfully_with_empty_variables() {
      String processDefinitionId = "processId";
      Map<String, Object> emptyVariables = Map.of();

      when(variableMapper.toCamundaFormat(emptyVariables)).thenReturn(Map.of());

      String expectedRequestBody =
          """
                  {
                    "variables": {}
                  }
                  """;

      stubFor(
          post(urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson(expectedRequestBody, true, true))
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody("{\"id\": \"anId\"}")));

      classUnderTest.start(processDefinitionId, emptyVariables);

      verify(
          postRequestedFor(
                  urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson(expectedRequestBody, true, true)));
    }

    @Test
    void should_throw_prozessstart_exception_on_request_error() {
      String processDefinitionId = "processId";
      Map<String, Object> emptyVariables = Map.of();

      when(variableMapper.toCamundaFormat(emptyVariables)).thenReturn(Map.of());

      stubFor(
          post(urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson("{\"variables\": {}}", true, true))
              .willReturn(aResponse().withStatus(400)));

      assertThatThrownBy(() -> classUnderTest.start(processDefinitionId, emptyVariables))
          .isInstanceOf(ProzessstartException.class)
          .hasMessageContaining(
              "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
                  .formatted(processDefinitionId));

      verify(
          postRequestedFor(
                  urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson("{\"variables\":{}}")));
    }

    @Test
    void should_throw_prozessstart_exception_on_response_error() {
      String processDefinitionId = "processId";
      Map<String, Object> emptyVariables = Map.of();

      when(variableMapper.toCamundaFormat(emptyVariables)).thenReturn(Map.of());

      stubFor(
          post(urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson("{\"variables\": {}}", true, true))
              .willReturn(aResponse().withStatus(500)));

      assertThatThrownBy(() -> classUnderTest.start(processDefinitionId, emptyVariables))
          .isInstanceOf(ProzessstartException.class)
          .hasMessageContaining(
              "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
                  .formatted(processDefinitionId));

      verify(
          postRequestedFor(
                  urlEqualTo("/process-definition/key/%s/start".formatted(processDefinitionId)))
              .withRequestBody(equalToJson("{\"variables\":{}}")));
    }
  }
}
