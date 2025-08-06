package de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = UuidGeneratorTestApplication.class)
class UUIDGeneratorTest {

  @Autowired private UuidTestEntityRepository repository;

  @Test
  void should_generate_uuid_automatically_on_save() {
    UuidTestEntity entity = new UuidTestEntity();

    UuidTestEntity saved = repository.save(entity);

    assertThat(saved.getId()).isNotNull();
    assertThat(saved.getId()).hasSize(36);
  }

  @Test
  void should_not_override_existing_id() {
    UuidTestEntity entity = new UuidTestEntity();
    entity.setId("custom-id-123");

    UuidTestEntity saved = repository.save(entity);

    assertThat(saved.getId()).isEqualTo("custom-id-123");
  }
}
