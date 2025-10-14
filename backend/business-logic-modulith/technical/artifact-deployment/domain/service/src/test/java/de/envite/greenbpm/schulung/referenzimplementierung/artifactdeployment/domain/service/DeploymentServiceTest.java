package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.domain.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.exception.DeploymentException;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentFilesQuery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DeploymentServiceTest {
  private final DeploymentFilesQuery deploymentFilesQueryMock = mock(DeploymentFilesQuery.class);
  private final DeploymentCommand deploymentCommandMock = mock(DeploymentCommand.class);
  private final ListAppender<ILoggingEvent> logWatcher = new ListAppender<>();
  private DeploymentService classUnderTest;

  @BeforeEach
  void setUp() {
    logWatcher.start();
    ((Logger) LoggerFactory.getLogger(DeploymentService.class)).addAppender(logWatcher);
    classUnderTest = new DeploymentService(deploymentFilesQueryMock, deploymentCommandMock);
  }

  @AfterEach
  void tearDown() {
    logWatcher.stop();
  }

  @Test
  void should_deploy_files() {

    Resource deployment1 = mock(Resource.class);
    Resource deployment2 = mock(Resource.class);
    List<Resource> deployments = List.of(deployment1, deployment2);

    when(deploymentFilesQueryMock.getDeploymentFiles()).thenReturn(deployments);

    classUnderTest.deploy();

    assertThat(logWatcher.list).isEmpty();
    verify(deploymentCommandMock).deploy(deployment1);
    verify(deploymentCommandMock).deploy(deployment2);
  }

  @Test
  void should_log_error_and_continue_deployment_on_exception() {

    Resource failedDeployment = mock(Resource.class);
    when(failedDeployment.getFilename()).thenReturn("process1.bpmn");
    Resource successfulDeployment = mock(Resource.class);

    List<Resource> deployments = List.of(failedDeployment, successfulDeployment);

    when(deploymentFilesQueryMock.getDeploymentFiles()).thenReturn(deployments);

    doThrow(new DeploymentException("Test Exception", new RuntimeException()))
        .when(deploymentCommandMock)
        .deploy(failedDeployment);

    classUnderTest.deploy();

    assertThat(logWatcher.list)
        .hasSize(1)
        .first()
        .satisfies(
            event -> {
              assertThat(event.getLevel()).isEqualTo(Level.ERROR);
              assertThat(event.getFormattedMessage())
                  .contains("Fehler beim Deployment des Prozessartefakts process1.bpmn");
              assertThat(event.getThrowableProxy().getClassName())
                  .isEqualTo(DeploymentException.class.getName());
            });

    InOrder inOrder = inOrder(deploymentCommandMock);
    inOrder.verify(deploymentCommandMock).deploy(failedDeployment);
    inOrder.verify(deploymentCommandMock).deploy(successfulDeployment);
  }
}
