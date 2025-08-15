package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out;

import java.util.List;
import org.springframework.core.io.Resource;

public interface DeploymentFilesQuery {

  List<Resource> getDeploymentFiles();
}
