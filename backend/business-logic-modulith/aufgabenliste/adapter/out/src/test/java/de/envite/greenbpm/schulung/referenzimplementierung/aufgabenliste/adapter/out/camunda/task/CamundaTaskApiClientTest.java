package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiClient;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.TaskApi;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

@WireMockTest
class CamundaTaskApiClientTest {

  private static final String CAMUNDA_BASE_PATH = "/engine-rest";

  private CamundaTaskApiClient classUnderTest;

  @BeforeEach
  void setUp(WireMockRuntimeInfo wmInfo) {
    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(wmInfo.getHttpBaseUrl() + CAMUNDA_BASE_PATH);

    TaskApi taskApi = new TaskApi(apiClient);
    CamundaTaskMapper taskMapper = Mappers.getMapper(CamundaTaskMapper.class);
    CamundaVariableMapper variableMapper = Mappers.getMapper(CamundaVariableMapper.class);

    classUnderTest = new CamundaTaskApiClient(taskMapper, taskApi, variableMapper);
  }

  @Nested
  class QueryTask {

    @Test
    void should_query_task_by_id_successfully() {
      String taskId = "taskId";

      LocalDateTime expectedErstelldatum = LocalDateTime.of(2023, 9, 18, 20, 40);
      ZonedDateTime zonedDateTime = expectedErstelldatum.atZone(ZoneId.systemDefault());
      String createdDate = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime);

      stubFor(
          get(urlEqualTo("%s/task/%s".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(
                  ok().withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                          {
                            "id": "taskId",
                            "name": "My Task",
                            "assignee": "assignee",
                            "created": "%s",
                            "formKey": "FormKey"
                          }
                          """
                              .formatted(createdDate))));

      Aufgabe actual = classUnderTest.queryById(taskId);

      assertThat(actual).isNotNull();
      assertThat(actual.getId()).isEqualTo("taskId");
      assertThat(actual.getName()).isEqualTo("My Task");
      assertThat(actual.getBearbeiter()).isEqualTo("assignee");
      assertThat(actual.getErstelldatum()).isEqualTo(expectedErstelldatum);
      assertThat(actual.getFormularreferenz()).isEqualTo("FormKey");

      verify(getRequestedFor(urlEqualTo("%s/task/%s".formatted(CAMUNDA_BASE_PATH, taskId))));
    }

    @Test
    void should_throw_aufgabe_not_found_exception_when_task_does_not_exist() {
      String taskId = "taskId";

      stubFor(
          get(urlEqualTo("%s/task/%s".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(
                  notFound()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                            {
                              "type": "InvalidRequestException",
                              "message": "No task found"
                            }
                            """)));

      assertThatThrownBy(() -> classUnderTest.queryById(taskId))
          .isInstanceOf(AufgabeNotFoundException.class)
          .hasMessageContaining(taskId);

      verify(getRequestedFor(urlEqualTo("%s/task/%s".formatted(CAMUNDA_BASE_PATH, taskId))));
    }

    @Test
    void should_throw_aufgabe_query_exception_on_request_error() {
      String taskId = "taskId";

      stubFor(
          get(urlEqualTo("%s/task/%s".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(
                  badRequest()
                      .withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                            {
                              "type": "RestException",
                              "message": "Bad Request"
                            }
                            """)));

      assertThatThrownBy(() -> classUnderTest.queryById(taskId))
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining(taskId);

      verify(getRequestedFor(urlEqualTo("%s/task/%s".formatted(CAMUNDA_BASE_PATH, taskId))));
    }
  }

  @Nested
  class QueryAllTasksByVorgang {

    @Test
    void should_query_all_tasks_successfully() {

      String vorgangId = "vorgangId";

      LocalDateTime expectedErstelldatum1 = LocalDateTime.of(2023, 9, 18, 20, 40);
      ZonedDateTime zonedDateTime1 = expectedErstelldatum1.atZone(ZoneId.systemDefault());
      String createdDate1 = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime1);
      LocalDateTime expectedErstelldatum2 = LocalDateTime.of(2024, 9, 18, 20, 40);
      ZonedDateTime zonedDateTime2 = expectedErstelldatum2.atZone(ZoneId.systemDefault());
      String createdDate2 = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(zonedDateTime2);

      stubFor(
          get(urlPathEqualTo("%s/task".formatted(CAMUNDA_BASE_PATH)))
              .withQueryParam("processInstanceBusinessKey", equalTo(vorgangId))
              .willReturn(
                  ok().withHeader("Content-Type", "application/json")
                      .withBody(
                          """
                            [
                              { "id": "task1", "name": "name1", "assignee": "assignee1", "created": "%s", "formKey": "form1" },
                              { "id": "task2", "name": "name2", "assignee": "assignee2", "created": "%s", "formKey": "form2" }
                            ]
                            """
                              .formatted(createdDate1, createdDate2))));

      List<Aufgabe> actual = classUnderTest.queryAllByVorgang(vorgangId);

      assertThat(actual).hasSize(2);
      assertThat(actual.get(0).getId()).isEqualTo("task1");
      assertThat(actual.get(0).getName()).isEqualTo("name1");
      assertThat(actual.get(0).getBearbeiter()).isEqualTo("assignee1");
      assertThat(actual.get(0).getErstelldatum()).isEqualTo(expectedErstelldatum1);
      assertThat(actual.get(0).getFormularreferenz()).isEqualTo("form1");

      assertThat(actual.get(1).getId()).isEqualTo("task2");
      assertThat(actual.get(1).getName()).isEqualTo("name2");
      assertThat(actual.get(1).getBearbeiter()).isEqualTo("assignee2");
      assertThat(actual.get(1).getErstelldatum()).isEqualTo(expectedErstelldatum2);
      assertThat(actual.get(1).getFormularreferenz()).isEqualTo("form2");

      verify(
          getRequestedFor(urlPathEqualTo("%s/task".formatted(CAMUNDA_BASE_PATH)))
              .withQueryParam("processInstanceBusinessKey", equalTo(vorgangId)));
    }

    @Test
    void should_throw_aufgabe_query_exception_on_request_error() {

      String vorgangId = "vorgangId";

      stubFor(
          get(urlPathEqualTo("%s/task".formatted(CAMUNDA_BASE_PATH)))
              .withQueryParam("processInstanceBusinessKey", equalTo(vorgangId))
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

      assertThatThrownBy(() -> classUnderTest.queryAllByVorgang(vorgangId))
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining("Aufgaben konnten nicht abgerufen werden.");

      verify(
          getRequestedFor(urlPathEqualTo("%s/task".formatted(CAMUNDA_BASE_PATH)))
              .withQueryParam("processInstanceBusinessKey", equalTo(vorgangId)));
    }
  }

  @Nested
  class ClaimTask {

    @Test
    void should_claim_successfully() {
      String taskId = "taskId";
      String userId = "user1";

      stubFor(
          post(urlEqualTo("%s/task/%s/claim".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(noContent()));

      classUnderTest.claim(taskId, userId);

      verify(
          postRequestedFor(urlEqualTo("%s/task/%s/claim".formatted(CAMUNDA_BASE_PATH, taskId)))
              .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
              .withRequestBody(matchingJsonPath("$.userId", equalTo(userId))));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() {
      String taskId = "taskId";
      String userId = "user1";

      stubFor(
          post(urlEqualTo("%s/task/%s/claim".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(
                  badRequest()
                      .withHeader("Content-Type", "application/json")
                      .withBody("{\"message\":\"Bad Request\"}")));

      assertThatThrownBy(() -> classUnderTest.claim(taskId, userId))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(taskId)
          .hasMessageContaining(userId);

      verify(postRequestedFor(urlEqualTo("%s/task/%s/claim".formatted(CAMUNDA_BASE_PATH, taskId))));
    }
  }

  @Nested
  class UnclaimTask {

    @Test
    void should_unclaim_successfully() {
      String taskId = "taskId";

      stubFor(
          post(urlEqualTo("%s/task/%s/unclaim".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(noContent()));

      classUnderTest.unclaim(taskId);

      verify(
          postRequestedFor(urlEqualTo("%s/task/%s/unclaim".formatted(CAMUNDA_BASE_PATH, taskId))));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() throws Exception {
      String taskId = "taskId";

      stubFor(
          post(urlEqualTo("%s/task/%s/unclaim".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(
                  badRequest()
                      .withHeader("Content-Type", "application/json")
                      .withBody("{\"message\":\"Bad Request\"}")));

      assertThatThrownBy(() -> classUnderTest.unclaim(taskId))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(taskId);

      verify(
          postRequestedFor(urlEqualTo("%s/task/%s/unclaim".formatted(CAMUNDA_BASE_PATH, taskId))));
    }
  }

  @Nested
  class CompleteTask {

    @Test
    void should_complete_successfully_with_empty_variables() {
      String taskId = "taskId";
      Map<String, Object> variables = Map.of();

      stubFor(
          post(urlEqualTo("%s/task/%s/complete".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(noContent()));

      classUnderTest.completeWithVariables(taskId, variables);

      verify(
          postRequestedFor(urlEqualTo("%s/task/%s/complete".formatted(CAMUNDA_BASE_PATH, taskId)))
              .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
              .withRequestBody(matchingJsonPath("$.variables[?(@.length() == 0)]")));
    }

    @Test
    void should_complete_successfully_with_variables() {
      String taskId = "taskId";
      Map<String, Object> variables = Map.of("Variable1", "Value1", "Variable2", 2);

      stubFor(
          post(urlEqualTo("%s/task/%s/complete".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(noContent()));

      classUnderTest.completeWithVariables(taskId, variables);

      verify(
          postRequestedFor(urlEqualTo("%s/task/%s/complete".formatted(CAMUNDA_BASE_PATH, taskId)))
              .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
              .withRequestBody(matchingJsonPath("$.variables.Variable1.value", equalTo("Value1")))
              .withRequestBody(matchingJsonPath("$.variables.Variable2.value", equalTo("2"))));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() {
      String taskId = "taskId";
      Map<String, Object> variables = Map.of("Variable1", "Value1");

      stubFor(
          post(urlEqualTo("%s/task/%s/complete".formatted(CAMUNDA_BASE_PATH, taskId)))
              .willReturn(
                  badRequest()
                      .withHeader("Content-Type", "application/json")
                      .withBody("{\"message\":\"Bad Request\"}")));

      assertThatThrownBy(() -> classUnderTest.completeWithVariables(taskId, variables))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(taskId);

      verify(
          postRequestedFor(urlEqualTo("%s/task/%s/complete".formatted(CAMUNDA_BASE_PATH, taskId))));
    }
  }
}
