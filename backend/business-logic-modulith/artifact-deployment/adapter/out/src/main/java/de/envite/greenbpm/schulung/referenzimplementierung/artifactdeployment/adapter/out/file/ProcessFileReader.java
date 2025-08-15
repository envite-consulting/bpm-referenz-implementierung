package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.file;

import de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out.DeploymentFilesQuery;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;

@RequiredArgsConstructor
public class ProcessFileReader implements DeploymentFilesQuery {

  private final List<Resource> resources;

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
