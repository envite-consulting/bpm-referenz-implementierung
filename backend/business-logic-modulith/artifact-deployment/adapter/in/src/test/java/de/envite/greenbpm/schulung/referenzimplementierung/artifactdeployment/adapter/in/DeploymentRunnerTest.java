package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.in.ProzessartifaktDeployment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

class DeploymentRunnerTest {

  private final ProzessartifaktDeployment prozessartifaktDeploymentMock =
      mock(ProzessartifaktDeployment.class);
  private final ListAppender<ILoggingEvent> logWatcher = new ListAppender<>();

  private DeploymentRunner classUnderTest;

  @BeforeEach
  void setUp() {
    logWatcher.start();
    ((Logger) LoggerFactory.getLogger(DeploymentRunner.class)).addAppender(logWatcher);

    classUnderTest = new DeploymentRunner(prozessartifaktDeploymentMock);
  }

  @AfterEach
  void tearDown() {
    logWatcher.stop();
  }

  @Test
  void should_call_deploy_on_run() {

    classUnderTest.run();

    verify(prozessartifaktDeploymentMock).deploy();

    assertThat(logWatcher.list).isEmpty();
  }

  @Test
  void should_log_error_when_deploy_throws_exception() throws Exception {
    final RuntimeException exception = mock(RuntimeException.class);
    doThrow(exception).when(prozessartifaktDeploymentMock).deploy();

    classUnderTest.run();

    assertThat(logWatcher.list)
        .hasSize(1)
        .first()
        .satisfies(
            event -> {
              assertThat(event.getLevel()).isEqualTo(Level.ERROR);
              assertThat(event.getFormattedMessage())
                  .contains("Prozessartefakte konnten nicht deployt werden");
              assertThat(event.getThrowableProxy().getClassName())
                  .isEqualTo(RuntimeException.class.getName());
            });
  }
}
