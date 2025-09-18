package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeQueryException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.AufgabeUpdateException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.TaskApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.CompleteTaskDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.TaskWithAttachmentAndCommentDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.UserIdDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.VariableValueDto;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;

class CamundaTaskApiClientTest {

  private CamundaTaskMapper taskMapperMock;
  private TaskApi taskApiMock;

  private CamundaTaskApiClient classUnderTest;

  @BeforeEach
  void setUp() {
    taskMapperMock = mock(CamundaTaskMapper.class);
    taskApiMock = mock(TaskApi.class);

    classUnderTest = new CamundaTaskApiClient(taskMapperMock, taskApiMock);
  }

  @Nested
  class QueryTask {

    @Test
    void should_query_task_by_id_successfully() throws Exception {
      String taskId = "taskId";
      TaskWithAttachmentAndCommentDto dto = new TaskWithAttachmentAndCommentDto();
      dto.setId(taskId);
      dto.setName("My Task");
      dto.setAssignee("assignee");
      dto.setCreated(new Date());
      dto.setFormKey("FormKey");

      Aufgabe expected = new Aufgabe(taskId, "My Task", "assignee", LocalDateTime.now(), "FormKey");

      when(taskApiMock.getTask(taskId)).thenReturn(dto);
      when(taskMapperMock.toDomain(dto)).thenReturn(expected);

      Aufgabe actual = classUnderTest.queryById(taskId);

      assertThat(actual).isEqualTo(expected);
      verify(taskApiMock).getTask(taskId);
    }

    @Test
    void should_throw_aufgabe_not_found_exception_when_task_does_not_exist() throws Exception {
      String taskId = "taskId";
      ApiException apiException = new ApiException(404, "Not Found");

      when(taskApiMock.getTask(taskId)).thenThrow(apiException);

      assertThatThrownBy(() -> classUnderTest.queryById(taskId))
          .isInstanceOf(AufgabeNotFoundException.class)
          .hasMessageContaining(taskId);

      verify(taskApiMock).getTask(taskId);
      verifyNoInteractions(taskMapperMock);
    }

    @Test
    void should_throw_aufgabe_query_exception_on_request_error() throws Exception {
      String taskId = "taskId";
      ApiException apiException = new ApiException(400, "Bad Request");

      when(taskApiMock.getTask(taskId)).thenThrow(apiException);

      assertThatThrownBy(() -> classUnderTest.queryById(taskId))
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining(taskId);

      verify(taskApiMock).getTask(taskId);
      verifyNoInteractions(taskMapperMock);
    }
  }

  @Nested
  class QueryAllTasksByVorgang {

    @Test
    void should_query_all_tasks_successfully() throws Exception {

      String vorgangId = "vorgangId";

      TaskWithAttachmentAndCommentDto dto1 = new TaskWithAttachmentAndCommentDto();
      dto1.setId("task1");
      TaskWithAttachmentAndCommentDto dto2 = new TaskWithAttachmentAndCommentDto();
      dto2.setId("task2");

      Aufgabe mapped1 = new Aufgabe("task1", "name1", "assignee1", LocalDateTime.now(), "form1");
      Aufgabe mapped2 = new Aufgabe("task2", "name2", "assignee2", LocalDateTime.now(), "form2");

      when(taskApiMock.getTasks(
              null, null, null, null, vorgangId, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null))
          .thenReturn(List.of(dto1, dto2));

      when(taskMapperMock.toDomain(dto1)).thenReturn(mapped1);
      when(taskMapperMock.toDomain(dto2)).thenReturn(mapped2);

      List<Aufgabe> actual = classUnderTest.queryAllByVorgang(vorgangId);

      assertThat(actual).containsExactly(mapped1, mapped2);
    }

    @Test
    void should_throw_aufgabe_query_exception_on_request_error() throws Exception {

      String vorgangId = "vorgangId";

      ApiException apiException = new ApiException(400, "Bad Request");
      when(taskApiMock.getTasks(
              null, null, null, null, vorgangId, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null, null, null, null, null, null, null, null, null, null, null, null, null,
              null, null))
          .thenThrow(apiException);

      assertThatThrownBy(() -> classUnderTest.queryAllByVorgang(vorgangId))
          .isInstanceOf(AufgabeQueryException.class)
          .hasMessageContaining("Aufgaben konnten nicht abgerufen werden.");
    }
  }

  @Nested
  class ClaimTask {

    @Test
    void should_claim_successfully() throws Exception {
      String taskId = "taskId";
      String userId = "user1";

      classUnderTest.claim(taskId, userId);

      ArgumentCaptor<UserIdDto> captor = ArgumentCaptor.forClass(UserIdDto.class);
      verify(taskApiMock).claim(eq(taskId), captor.capture());
      assertThat(captor.getValue().getUserId()).isEqualTo(userId);
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() throws Exception {
      String taskId = "taskId";
      String userId = "user1";
      ApiException apiException = new ApiException(400, "Bad Request");

      doThrow(apiException).when(taskApiMock).claim(eq(taskId), any(UserIdDto.class));

      assertThatThrownBy(() -> classUnderTest.claim(taskId, userId))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(taskId)
          .hasMessageContaining(userId);
    }
  }

  @Nested
  class UnclaimTask {

    @Test
    void should_unclaim_successfully() throws Exception {
      String taskId = "taskId";

      classUnderTest.unclaim(taskId);

      verify(taskApiMock).unclaim(taskId);
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() throws Exception {
      String taskId = "taskId";
      ApiException apiException = new ApiException(400, "Bad Request");

      doThrow(apiException).when(taskApiMock).unclaim(taskId);

      assertThatThrownBy(() -> classUnderTest.unclaim(taskId))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(taskId);
    }
  }

  @Nested
  class CompleteTask {

    @Test
    void should_complete_successfully_with_empty_variables() throws Exception {
      String taskId = "taskId";
      Map<String, Object> variables = Map.of();

      classUnderTest.completeWithVariables(taskId, variables);

      ArgumentCaptor<CompleteTaskDto> captor = ArgumentCaptor.forClass(CompleteTaskDto.class);
      verify(taskApiMock).complete(eq(taskId), captor.capture());

      CompleteTaskDto capturedDto = captor.getValue();
      assertThat(capturedDto.getVariables()).isEmpty();
    }

    @Test
    void should_complete_successfully_with_variables() throws Exception {
      String taskId = "taskId";
      Map<String, Object> variables = Map.of("Variable1", "Value1", "Variable2", 2);

      classUnderTest.completeWithVariables(taskId, variables);

      ArgumentCaptor<CompleteTaskDto> captor = ArgumentCaptor.forClass(CompleteTaskDto.class);
      verify(taskApiMock).complete(eq(taskId), captor.capture());

      CompleteTaskDto capturedDto = captor.getValue();
      assertThat(capturedDto.getVariables())
          .containsEntry("Variable1", new VariableValueDto().value("Value1"))
          .containsEntry("Variable2", new VariableValueDto().value(2));
    }

    @Test
    void should_throw_aufgabe_update_exception_on_request_error() throws Exception {
      String taskId = "taskId";
      Map<String, Object> variables = Map.of("Variable1", "Value1");
      ApiException apiException = new ApiException(400, "Bad Request");

      doThrow(apiException).when(taskApiMock).complete(eq(taskId), any(CompleteTaskDto.class));

      assertThatThrownBy(() -> classUnderTest.completeWithVariables(taskId, variables))
          .isInstanceOf(AufgabeUpdateException.class)
          .hasMessageContaining(taskId);
    }
  }
}
