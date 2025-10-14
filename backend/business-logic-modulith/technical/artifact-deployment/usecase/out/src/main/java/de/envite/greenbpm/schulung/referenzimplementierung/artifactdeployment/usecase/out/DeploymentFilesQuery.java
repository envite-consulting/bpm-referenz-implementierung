package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.usecase.out;

import org.springframework.core.io.Resource;

import java.util.List;

public interface DeploymentFilesQuery {

  List<Resource> getDeploymentFiles();
}
