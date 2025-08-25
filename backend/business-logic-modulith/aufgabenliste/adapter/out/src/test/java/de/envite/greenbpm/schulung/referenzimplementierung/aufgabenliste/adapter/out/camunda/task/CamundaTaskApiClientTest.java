package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.springframework.web.reactive.function.client.WebClient;

class CamundaTaskApiClientTest {

  private static WireMockServer wireMockServer;
  private static CamundaTaskMapper taskMapperMock;
  private static CamundaVariableMapper variableMapper;

  private CamundaTaskApiClient classUnderTest;

  @BeforeEach
  void setUpBeforeClass() {
    taskMapperMock = mock(CamundaTaskMapper.class);
    variableMapper = mock(CamundaVariableMapper.class);

    wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    wireMockServer.start();
    WireMock.configureFor("localhost", wireMockServer.port());
    String baseUrl = "http://localhost:%s".formatted(wireMockServer.port());

    classUnderTest =
        new CamundaTaskApiClient(WebClient.builder(), taskMapperMock, baseUrl, variableMapper);
  }

  @AfterEach
  void tearDownAfterClass() {
    wireMockServer.stop();
  }

  @Nested
  class QueryTask {

    @Test
    void should_query_task_by_id_successfully() {

      String taskId = "taskId";
      String name = "My Task";
      String assignee = "assignee";
      String created = "2023-01-01T00:00:00.000+0200";
      String formKey = "FormKey";

      Aufgabe mappedExpectedResult =
          new Aufgabe(taskId, name, assignee, LocalDateTime.of(2023, 1, 1, 0, 0), formKey);
      CamundaTaskResource expectedResult =
          new CamundaTaskResource(taskId, name, assignee, created, formKey);
      when(taskMapperMock.toDomain(expectedResult)).thenReturn(mappedExpectedResult);

      String jsonResponse =
          """
                    {
                      "id": "%s",
                      "name": "%s",
                      "assignee": "%s",
                      "created": "%s",
                      "formKey": "%s"
                    }
                    """
              .formatted(taskId, name, assignee, created, formKey);

      stubFor(
          get(urlEqualTo("/task/%s".formatted(taskId)))
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(jsonResponse)));

      Aufgabe actualTask = assertDoesNotThrow(() -> classUnderTest.queryById(taskId));

      assertThat(actualTask).isNotNull().isEqualTo(mappedExpectedResult);

      verify(getRequestedFor(urlEqualTo("/task/%s".formatted(taskId))));
    }

    @Test
    void should_throw_aufgabe_not_found_exception_when_task_does_not_exist() {
      String taskId = "taskId";

      stubFor(
          get(urlEqualTo("/task/%s".formatted(taskId)))
              .willReturn(aResponse().withStatus(404).withBody("Not Found")));

      assertThatThrownBy(() -> classUnderTest.queryById(taskId))
          .isInstanceOf(AufgabeNotFoundException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte nicht gefunden werden.".formatted(taskId));

      verify(getRequestedFor(urlEqualTo("/task/%s".formatted(taskId))));
      verifyNoInteractions(taskMapperMock);
    }

    @Test
    void should_throw_aufgabe_query_exception_on_request_error() {
      String taskId = "taskId";
      stubFor(
          get(urlEqualTo("/task/%s".formatted(taskId)))
              .willReturn(aResponse().withStatus(400).withBody("Bad Request")));

      assertThatThrownBy(() -> classUnderTest.queryById(taskId))
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte nicht abgerufen werden.".formatted(taskId));

      verify(getRequestedFor(urlEqualTo("/task/%s".formatted(taskId))));
      verifyNoInteractions(taskMapperMock);
    }

    @Test
    void should_throw_aufgabe_query_exception_on_server_error() {
      String taskId = "taskId";
      stubFor(
          get(urlEqualTo("/task/%s".formatted(taskId)))
              .willReturn(aResponse().withStatus(500).withBody("Internal Server Error")));

      assertThatThrownBy(() -> classUnderTest.queryById(taskId))
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte nicht abgerufen werden.".formatted(taskId));

      verify(getRequestedFor(urlEqualTo("/task/%s".formatted(taskId))));
      verifyNoInteractions(taskMapperMock);
    }
  }

  @Nested
  class QueryAllTasks {

    @Test
    void should_query_all_tasks_successfully() {

      String taskId1 = "taskId1";
      String name1 = "My Task1";
      String assignee1 = "assignee1";
      String created1 = "2023-01-01T00:00:00.000+0200";
      String formKey1 = "formKey1";

      Aufgabe mappedExpectedResult1 =
          new Aufgabe(taskId1, name1, assignee1, LocalDateTime.of(2023, 1, 1, 0, 0), formKey1);

      String taskId2 = "taskId2";
      String name2 = "My Task2";
      String assignee2 = "assignee2";
      String created2 = "2024-01-01T00:00:00.000+0200";
      String formKey2 = "formKey1";

      Aufgabe mappedExpectedResult2 =
          new Aufgabe(taskId2, name2, assignee2, LocalDateTime.of(2024, 1, 1, 0, 0), formKey2);

      CamundaTaskResource expectedResult1 =
          new CamundaTaskResource(taskId1, name1, assignee1, created1, formKey1);
      when(taskMapperMock.toDomain(expectedResult1)).thenReturn(mappedExpectedResult1);
      CamundaTaskResource expectedResult2 =
          new CamundaTaskResource(taskId2, name2, assignee2, created2, formKey2);
      when(taskMapperMock.toDomain(expectedResult2)).thenReturn(mappedExpectedResult2);

      String jsonResponse =
          """
                    [
                        {
                          "id": "%s",
                          "name": "%s",
                          "assignee": "%s",
                          "created": "%s",
                          "formKey": "%s"
                        },
                        {
                          "id": "%s",
                          "name": "%s",
                          "assignee": "%s",
                          "created": "%s",
                          "formKey": "%s"
                        }
                    ]
                    """
              .formatted(
                  taskId1, name1, assignee1, created1, formKey1, taskId2, name2, assignee2,
                  created2, formKey2);

      stubFor(
          get(urlEqualTo("/task"))
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(jsonResponse)));

      List<Aufgabe> actualTasks = assertDoesNotThrow(() -> classUnderTest.queryAll());

      assertThat(actualTasks)
          .isNotNull()
          .containsExactly(mappedExpectedResult1, mappedExpectedResult2);

      verify(getRequestedFor(urlEqualTo("/task")));
    }

    @Test
    void should_throw_aufgabe_query_exception_on_request_error() {
      stubFor(
          get(urlEqualTo("/task")).willReturn(aResponse().withStatus(400).withBody("Bad Request")));

      assertThatThrownBy(() -> classUnderTest.queryAll())
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining("Aufgaben konnten nicht abgerufen werden.");

      verify(getRequestedFor(urlEqualTo("/task")));
      verifyNoInteractions(taskMapperMock);
    }

    @Test
    void should_throw_aufgabe_query_exception_on_server_error() {
      stubFor(
          get(urlEqualTo("/task"))
              .willReturn(aResponse().withStatus(500).withBody("Internal Server Error")));

      assertThatThrownBy(() -> classUnderTest.queryAll())
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining("Aufgaben konnten nicht abgerufen werden.");

      verify(getRequestedFor(urlEqualTo("/task")));
      verifyNoInteractions(taskMapperMock);
    }

    @Test
    void should_handle_empty_list_successfully() {
      String jsonResponse =
          """
          []
          """;

      stubFor(
          get(urlEqualTo("/task"))
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(jsonResponse)));

      List<Aufgabe> actualTasks = assertDoesNotThrow(() -> classUnderTest.queryAll());

      assertThat(actualTasks).isNotNull().isEmpty();
      verify(getRequestedFor(urlEqualTo("/task")));
      verifyNoInteractions(taskMapperMock);
    }
  }

  @Nested
  class ClaimTask {

    @Test
    void should_claim_successfully() {
      String taskId = "taskId";
      String assignee = "assignee";
      stubFor(
          post(urlEqualTo("/task/%s/claim".formatted(taskId)))
              .withRequestBody(equalToJson("{\"userId\":\"%s\"}".formatted(assignee)))
              .willReturn(aResponse().withStatus(204)));

      classUnderTest.claim(taskId, assignee);

      verify(
          postRequestedFor(urlEqualTo("/task/%s/claim".formatted(taskId)))
              .withRequestBody(equalToJson("{\"userId\":\"%s\"}".formatted(assignee))));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() {
      String taskId = "taskId";
      String assignee = "assignee";

      stubFor(
          post(urlEqualTo("/task/%s/claim".formatted(taskId)))
              .willReturn(aResponse().withStatus(400)));

      assertThatThrownBy(() -> classUnderTest.claim(taskId, assignee))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte dem Benutzer %s nicht zugewiesen werden."
                  .formatted(taskId, assignee));

      verify(
          postRequestedFor(urlEqualTo("/task/%s/claim".formatted(taskId)))
              .withRequestBody(equalToJson("{\"userId\":\"%s\"}".formatted(assignee))));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_server_error() {
      String taskId = "taskId";
      String assignee = "assignee";

      stubFor(
          post(urlEqualTo("/task/%s/claim".formatted(taskId)))
              .willReturn(aResponse().withStatus(500)));

      assertThatThrownBy(() -> classUnderTest.claim(taskId, assignee))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte dem Benutzer %s nicht zugewiesen werden."
                  .formatted(taskId, assignee));

      verify(
          postRequestedFor(urlEqualTo("/task/%s/claim".formatted(taskId)))
              .withRequestBody(equalToJson("{\"userId\":\"%s\"}".formatted(assignee))));
    }
  }

  @Nested
  class UnclaimTask {

    @Test
    void should_unclaim_successfully() {
      String taskId = "taskId";

      stubFor(
          post(urlEqualTo("/task/%s/unclaim".formatted(taskId)))
              .willReturn(aResponse().withStatus(204)));

      classUnderTest.unclaim(taskId);

      verify(postRequestedFor(urlEqualTo("/task/%s/unclaim".formatted(taskId))));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() {
      String taskId = "taskId";

      stubFor(
          post(urlEqualTo("/task/%s/unclaim".formatted(taskId)))
              .willReturn(aResponse().withStatus(400)));

      assertThatThrownBy(() -> classUnderTest.unclaim(taskId))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte nicht abgegeben werden.".formatted(taskId));

      verify(postRequestedFor(urlEqualTo("/task/%s/unclaim".formatted(taskId))));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_server_error() {
      String taskId = "taskId";

      stubFor(
          post(urlEqualTo("/task/%s/unclaim".formatted(taskId)))
              .willReturn(aResponse().withStatus(500)));

      assertThatThrownBy(() -> classUnderTest.unclaim(taskId))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte nicht abgegeben werden.".formatted(taskId));

      verify(postRequestedFor(urlEqualTo("/task/%s/unclaim".formatted(taskId))));
    }
  }

  @Nested
  class CompleteTask {

    @Test
    void should_complete_successfully_with_empty_variables() {

      String taskId = "taskId";

      Map<String, Object> emptyVariables = Map.of();

      stubFor(
          post(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .withRequestBody(equalToJson("{\"variables\": {}}", true, true))
              .willReturn(aResponse().withStatus(204)));

      classUnderTest.completeWithVariables(taskId, emptyVariables);

      verify(
          postRequestedFor(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .withRequestBody(equalToJson("{\"variables\": {}}")));
    }

    @Test
    void should_complete_successfully_with_variables() {
      String taskId = "taskId";

      Map<String, Object> variables = Map.of("Variable 1", "Value 1", "Variable 2", 2);

      Map<String, Object> mappedVariables =
          Map.of(
              "Variable1", Map.of("value", "Value1", "type", "String"),
              "Variable2", Map.of("value", 2, "type", "Integer"));

      when(variableMapper.toCamundaFormat(variables)).thenReturn(mappedVariables);

      String jsonResponse =
          """
                              {
                                "variables": {
                                  "Variable1": { "value": "Value1", "type": "String" },
                                  "Variable2": { "value": 2, "type": "Integer" }
                                }
                              }
                              """;
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
          post(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .withRequestBody(
                  equalToJson(
                      " { \"variables\": { \"Variable1\": { \"value\": \"Value1\", \"type\": \"String\" }, \"Variable2\": { \"value\": 2, \"type\": \"Integer\" }}} ",
                      true,
                      true))
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(jsonResponse)));

      classUnderTest.completeWithVariables(taskId, variables);

      verify(
          postRequestedFor(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .withRequestBody(
                  equalToJson(
                      "{\"variables\" : {\"Variable2\" : {\"type\" : \"Integer\", \"value\" : 2},\"Variable1\" : {\"type\" : \"String\", \"value\" : \"Value1\"}}}\n",
                      true,
                      true)));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() {

      String taskId = "taskId";
      Map<String, Object> variables = Map.of("Variable 1", "Value 1");

      Map<String, Object> mappedVariables =
          Map.of("Variable1", Map.of("value", "Value1", "type", "String"));

      when(variableMapper.toCamundaFormat(variables)).thenReturn(mappedVariables);

      stubFor(
          post(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .willReturn(aResponse().withStatus(400)));

      assertThatThrownBy(() -> classUnderTest.completeWithVariables(taskId, variables))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte nicht mit den Variablen %s abgeschlossen werden."
                  .formatted(taskId, variables));

      verify(
          postRequestedFor(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .withRequestBody(
                  equalToJson(
                      "{\"variables\" : {\"Variable1\" : {\"type\" : \"String\",\"value\" : \"Value1\"}}}",
                      true,
                      true)));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_server_error() {

      String taskId = "taskId";
      Map<String, Object> variables = Map.of("Variable 1", "Value 1");

      Map<String, Object> mappedVariables =
          Map.of("Variable1", Map.of("value", "Value1", "type", "String"));

      when(variableMapper.toCamundaFormat(variables)).thenReturn(mappedVariables);

      stubFor(
          post(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .willReturn(aResponse().withStatus(500)));

      assertThatThrownBy(() -> classUnderTest.completeWithVariables(taskId, variables))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(
              "Aufgabe mit ID %s konnte nicht mit den Variablen %s abgeschlossen werden."
                  .formatted(taskId, variables));

      verify(
          postRequestedFor(urlEqualTo("/task/%s/complete".formatted(taskId)))
              .withRequestBody(
                  equalToJson(
                      "{\"variables\" : {\"Variable1\" : {\"type\" : \"String\",\"value\" : \"Value1\"}}}",
                      true,
                      true)));
    }
  }
}
