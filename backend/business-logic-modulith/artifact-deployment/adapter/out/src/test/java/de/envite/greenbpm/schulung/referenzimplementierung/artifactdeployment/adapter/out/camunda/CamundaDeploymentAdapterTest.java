package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.camunda;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.exception.DeploymentException;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;

class CamundaDeploymentAdapterTest {

  private WireMockServer wireMockServer;
  private CamundaDeploymentAdapter classUnderTest;

  @BeforeEach
  void setUp() {
    // HTTP/2 mit einem InputStream als Body ist nur für HTTPS supportet. Daher wird HTTP/2
    // deaktiviert (siehe: https://github.com/wiremock/wiremock/issues/2637).
    wireMockServer =
        new WireMockServer(WireMockConfiguration.options().dynamicPort().http2PlainDisabled(true));
    wireMockServer.start();
    WireMock.configureFor("localhost", wireMockServer.port());

    WebClient webClient =
        WebClient.builder().baseUrl("http://localhost:" + wireMockServer.port()).build();

    classUnderTest = new CamundaDeploymentAdapter(webClient);
  }

  @AfterEach
  void tearDown() {
    wireMockServer.stop();
  }

  @Test
  void should_deploy_artefact_successfully() {

    stubFor(
        post(urlEqualTo("/deployment/create"))
            .willReturn(
                aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody("{\"id\": \"aDeploymentId\"}")));

    assertDoesNotThrow(() -> classUnderTest.deploy(createTestResource()));

    verify(
        postRequestedFor(urlEqualTo("/deployment/create"))
            .withRequestBody(containing("name=\"deployment-name\""))
            .withRequestBody(containing("process"))
            .withRequestBody(containing("name=\"deploy-changed-only\""))
            .withRequestBody(containing("true"))
            .withRequestBody(containing("name=\"data\""))
            .withRequestBody(containing("filename=\"process.bpmn\""))
            .withRequestBody(containing("Test Content")));
  }

  @Test
  void should_throw_deployment_exception_on_request_error() {
    Resource file = createTestResource();

    stubFor(
        post(urlEqualTo("/deployment/create"))
            .willReturn(
                aResponse().withStatus(HttpStatus.BAD_REQUEST.value()).withBody("Bad Request")));

    assertThatThrownBy(() -> classUnderTest.deploy(file))
        .isInstanceOf(DeploymentException.class)
        .hasMessageContaining("Prozessartefakt (process.bpmn) konnte nicht deployt werden.");
  }

  @Test
  void should_throw_deployment_exception_on_server_error() {
    Resource file = createTestResource();

    stubFor(
        post(urlEqualTo("/deployment/create"))
            .willReturn(
                aResponse()
                    .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .withBody("Internal Server Error")));

    assertThatThrownBy(() -> classUnderTest.deploy(file))
        .isInstanceOf(DeploymentException.class)
        .hasMessageContaining("Prozessartefakt (process.bpmn) konnte nicht deployt werden.");
  }

  private Resource createTestResource() {
    return new ByteArrayResource("Test Content".getBytes()) {
      @Override
      public String getFilename() {
        return "process.bpmn";
      }
    };
  }
}
