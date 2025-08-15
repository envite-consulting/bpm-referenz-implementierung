package de.envite.greenbpm.schulung.referenzimplementierung.artifactdeployment.adapter.out.file;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

class ProcessFileReaderTest {

  private ProcessFileReader classUnderTest;

  @Test
  void should_return_only_files_with_valid_extension() {

    Resource bpmnResource = mock(Resource.class);
    when(bpmnResource.getFilename()).thenReturn("process.bpmn");
    Resource dmnResource = mock(Resource.class);
    when(dmnResource.getFilename()).thenReturn("decision.dmn");
    Resource formResource = mock(Resource.class);
    when(formResource.getFilename()).thenReturn("form.form");
    Resource invalidResource = mock(Resource.class);
    when(invalidResource.getFilename()).thenReturn("invalid.txt");

    List<Resource> resources = List.of(bpmnResource, dmnResource, formResource, invalidResource);

    classUnderTest = new ProcessFileReader(resources);

    List<Resource> result = classUnderTest.getDeploymentFiles();

    assertThat(result).hasSize(3).containsOnly(bpmnResource, dmnResource, formResource);
  }
}
