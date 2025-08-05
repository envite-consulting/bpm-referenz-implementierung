package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;

@DataJdbcTest
@Import(UUIDGenerator.class)
class FahrzeugJdbcRepositoryTest {

  @Autowired private FahrzeugJdbcRepository classUnderTest;

  @Test
  void should_save_with_uuid() {
    FahrzeugEntity entity = new FahrzeugEntity();
    entity.setModell("Test Modell");
    entity.setHersteller("Test Hersteller");
    entity.setJahr(2022);

    FahrzeugEntity result = classUnderTest.save(entity);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.getId()).isNotNull();
    softAssertions.assertThat(result.getHersteller()).isEqualTo(entity.getHersteller());
    softAssertions.assertThat(result.getModell()).isEqualTo(entity.getModell());
    softAssertions.assertThat(result.getJahr()).isEqualTo(entity.getJahr());
    softAssertions.assertAll();
  }

  @Test
  void should_find_by_id_after_save() {
    FahrzeugEntity entity = saveFahrzeug("Hersteller", "Modell", 1900);

    FahrzeugEntity saved = classUnderTest.save(entity);
    Optional<FahrzeugEntity> result = classUnderTest.findById(saved.getId());

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result).isPresent();
    softAssertions.assertThat(result.get()).usingRecursiveComparison().isEqualTo(saved);
    softAssertions.assertAll();
  }

  @Test
  void should_find_all_after_save() {
    FahrzeugEntity entity1 = saveFahrzeug("Hersteller1", "Modell1", 1901);
    FahrzeugEntity entity2 = saveFahrzeug("Hersteller2", "Modell2", 1902);

    FahrzeugEntity saved1 = classUnderTest.save(entity1);
    FahrzeugEntity saved2 = classUnderTest.save(entity2);
    Optional<FahrzeugEntity> result1 = classUnderTest.findById(saved1.getId());
    Optional<FahrzeugEntity> result2 = classUnderTest.findById(saved2.getId());

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result1).isPresent();
    softAssertions.assertThat(result1.get()).usingRecursiveComparison().isEqualTo(saved1);
    softAssertions.assertThat(result2).isPresent();
    softAssertions.assertThat(result2.get()).usingRecursiveComparison().isEqualTo(saved2);
    softAssertions.assertAll();
  }

  private FahrzeugEntity saveFahrzeug(String hersteller, String modell, Integer jahr) {
    FahrzeugEntity entity = new FahrzeugEntity();
    entity.setHersteller(hersteller);
    entity.setModell(modell);
    entity.setJahr(jahr);
    return classUnderTest.save(entity);
  }
}
