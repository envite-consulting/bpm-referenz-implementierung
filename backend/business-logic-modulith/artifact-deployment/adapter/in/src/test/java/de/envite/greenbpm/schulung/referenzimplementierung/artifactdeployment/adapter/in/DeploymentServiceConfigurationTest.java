package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.in;

import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.camunda.CamundaDeploymentAdapter;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.file.ProcessFileReader;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.domain.service.DeploymentService;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.in.ProzessartifaktDeployment;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentCommand;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentFilesQuery;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(classes = DeploymentServiceConfiguration.class)
class DeploymentServiceConfigurationTest {

  @Nested
  @TestPropertySource(
      properties = {
        "process-artifacts.base-path=classpath*:mock-artifacts/**/*",
        "camunda.bpm.client.base-url=http://test-camunda:9090/engine-rest"
      })
  class WithCustomProperties {

    @Autowired private DeploymentFilesQuery deploymentFilesQuery;

    @Autowired private WebClient webClient;

    @Autowired private DeploymentCommand deploymentCommand;

    @Autowired private ProzessartifaktDeployment prozessartifaktDeployment;

    @Test
    void should_create_bean_with_custom_configuration() {
      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions
          .assertThat(deploymentFilesQuery)
          .isNotNull()
          .isInstanceOf(ProcessFileReader.class);
      softAssertions.assertThat(webClient).isNotNull().isInstanceOf(WebClient.class);
      softAssertions
          .assertThat(deploymentCommand)
          .isNotNull()
          .isInstanceOf(CamundaDeploymentAdapter.class);
      softAssertions
          .assertThat(prozessartifaktDeployment)
          .isNotNull()
          .isInstanceOf(DeploymentService.class);
      softAssertions.assertAll();
    }
  }

  // TODO: Klären, ob solch ein Test wirklich sinnvoll ist und einen Mehrwert liefert.
  @Nested
  class WithDefaultProperties {

    @Autowired private DeploymentFilesQuery deploymentFilesQuery;

    @Autowired private WebClient webClient;

    @Autowired private DeploymentCommand deploymentCommand;

    @Autowired private ProzessartifaktDeployment prozessartifaktDeployment;

    @Test
    void should_create_bean_with_default_configuration() {
      SoftAssertions softAssertions = new SoftAssertions();
      softAssertions
          .assertThat(deploymentFilesQuery)
          .isNotNull()
          .isInstanceOf(ProcessFileReader.class);
      softAssertions.assertThat(webClient).isNotNull().isInstanceOf(WebClient.class);
      softAssertions
          .assertThat(deploymentCommand)
          .isNotNull()
          .isInstanceOf(CamundaDeploymentAdapter.class);
      softAssertions
          .assertThat(prozessartifaktDeployment)
          .isNotNull()
          .isInstanceOf(DeploymentService.class);
      softAssertions.assertAll();
    }
  }
}
