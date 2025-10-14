package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.file;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentFilesQuery;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProcessFileReader implements DeploymentFilesQuery {

  private final List<Resource> resources;

  public ProcessFileReader(
      @Value("${process-artifacts.base-path:classpath*:/**/*}") List<Resource> resources) {
    this.resources = resources;
  }

  @Override
  public List<Resource> getDeploymentFiles() {

    return resources.stream()
        .filter(
            r ->
                r.getFilename() != null
                    && (r.getFilename().endsWith(".bpmn")
                        || r.getFilename().endsWith(".dmn")
                        || r.getFilename().endsWith(".form")))
        .toList();
  }
}
