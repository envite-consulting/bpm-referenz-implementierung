package de.envite.greenbpm.schulung.referenzimplementierung.camundaclient;

import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.ApiClient;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessDefinitionApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.ProcessInstanceApi;
import de.envite.greenbpm.schulung.referenzimplementierung.camunda.api.api.TaskApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CamundaApiClientConfig {

  @Bean
  public ApiClient camundaApiClient(
      @Value("${camunda.bpm.client.base-url:http://localhost:8081/engine-rest}") String baseUrl) {

    ApiClient apiClient = new ApiClient();
    apiClient.setBasePath(baseUrl);

    return apiClient;
  }

  @Bean
  public ProcessDefinitionApi processDefinitionApi(ApiClient apiClient) {
    ProcessDefinitionApi api = new ProcessDefinitionApi();
    api.setApiClient(apiClient);
    return api;
  }

  @Bean
  public ProcessInstanceApi processInstanceApi(ApiClient apiClient) {
    ProcessInstanceApi api = new ProcessInstanceApi();
    api.setApiClient(apiClient);
    return api;
  }

  @Bean
  public TaskApi taskApi(ApiClient apiClient) {
    TaskApi api = new TaskApi();
    api.setApiClient(apiClient);
    return api;
  }
}
