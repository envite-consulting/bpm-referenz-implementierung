package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.process;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.out.camunda.CamundaVariableMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.ProzessstartException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiException;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessDefinitionApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.StartProcessInstanceDto;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.model.VariableValueDto;
import java.util.Map;
import org.junit.jupiter.api.*;
import org.mockito.MockedStatic;

class CamundaProcessApiClientTest {

  private ProcessDefinitionApi processDefinitionApi;
  private CamundaProcessApiClient classUnderTest;
  private static MockedStatic<CamundaVariableMapper> variableMapperMock;

  @BeforeAll
  static void setUpBeforeClass() {
    variableMapperMock = mockStatic(CamundaVariableMapper.class);
  }

  @AfterAll
  static void tearDownAfterClass() {
    variableMapperMock.close();
  }

  @BeforeEach
  void setUp() {
    processDefinitionApi = mock(ProcessDefinitionApi.class);
    classUnderTest = new CamundaProcessApiClient(processDefinitionApi);
  }

  @AfterEach
  void tearDown() {
    variableMapperMock.reset();
  }

  @Nested
  class StartProcess {

    @Test
    void should_start_process_successfully_with_variables_and_business_key() throws Exception {

      String processDefinitionId = "processId";
      String businessKey = "BK-123";
      Map<String, Object> variables = Map.of("Variable1", "Value1", "Variable2", 2);
      Map<String, VariableValueDto> mappedVariables =
          Map.of(
              "Variable1", createVariableValueDto("Value1"),
              "Variable2", createVariableValueDto(2));

      variableMapperMock
          .when(() -> CamundaVariableMapper.toDto(variables))
          .thenReturn(mappedVariables);

      classUnderTest.start(processDefinitionId, businessKey, variables);

      variableMapperMock.verify(() -> CamundaVariableMapper.toDto(variables));

      verify(processDefinitionApi)
          .startProcessInstanceByKey(
              eq(processDefinitionId),
              argThat(
                  dto -> {
                    assertNotNull(dto.getVariables());
                    assertNotNull(dto.getBusinessKey());
                    return dto.getVariables().equals(mappedVariables)
                        && dto.getBusinessKey().equals(businessKey);
                  }));
    }

    @Test
    void should_start_process_successfully_with_empty_variables() throws Exception {

      String processDefinitionId = "processId";
      String businessKey = "BK-123";
      Map<String, Object> emptyVariables = Map.of();
      Map<String, VariableValueDto> emptyMappedVariables = Map.of();

      variableMapperMock
          .when(() -> CamundaVariableMapper.toDto(emptyVariables))
          .thenReturn(emptyMappedVariables);

      classUnderTest.start(processDefinitionId, businessKey, emptyVariables);

      verify(processDefinitionApi)
          .startProcessInstanceByKey(
              eq(processDefinitionId),
              argThat(
                  dto -> {
                    assertNotNull(dto.getVariables());
                    assertNotNull(dto.getBusinessKey());
                    return dto.getVariables().isEmpty() && dto.getBusinessKey().equals(businessKey);
                  }));
    }

    @Test
    void should_throw_prozessstart_exception_on_api_exception() throws Exception {

      String processDefinitionId = "processId";
      String businessKey = "BK-123";
      Map<String, Object> emptyVariables = Map.of();
      String apiErrorMessage = "Fehler";

      doThrow(new ApiException(apiErrorMessage))
          .when(processDefinitionApi)
          .startProcessInstanceByKey(anyString(), any(StartProcessInstanceDto.class));

      assertThatThrownBy(
              () -> classUnderTest.start(processDefinitionId, businessKey, emptyVariables))
          .isInstanceOf(ProzessstartException.class)
          .hasMessageContaining(
              "Prozess mit Process Definition ID %s konnte nicht gestartet werden."
                  .formatted(processDefinitionId));
    }
  }

  private VariableValueDto createVariableValueDto(Object value) {
    VariableValueDto dto = new VariableValueDto();
    dto.setValue(value);
    return dto;
  }
}
