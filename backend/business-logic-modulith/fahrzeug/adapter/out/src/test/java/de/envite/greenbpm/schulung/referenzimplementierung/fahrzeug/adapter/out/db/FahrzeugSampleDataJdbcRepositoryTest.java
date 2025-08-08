package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.db;

import static org.assertj.core.api.Assertions.tuple;

import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@DataJdbcTest
@ActiveProfiles("testdata")
@Import(UUIDGenerator.class)
public class FahrzeugSampleDataJdbcRepositoryTest {

  @Autowired private FahrzeugJdbcRepository classUnderTest;

  @Test
  void should_find_all_testdata() {
    FahrzeugEntity savedEntity = saveFahrzeug("Hersteller", "Modell", 1920);

    List<FahrzeugEntity> result = classUnderTest.findAll();

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result).isNotEmpty();
    softAssertions
        .assertThat(result)
        .hasSize(7)
        .extracting(
            FahrzeugEntity::getId,
            FahrzeugEntity::getHersteller,
            FahrzeugEntity::getModell,
            FahrzeugEntity::getJahr)
        .containsExactlyInAnyOrder(
            tuple(savedEntity.getId(), "Hersteller", "Modell", 1920),
            tuple("b6122856-f08a-4454-b5bd-a3d232065b91", "Tesla", "Model 3 Long Range  AWD", 2023),
            tuple("5cd7f4db-686e-48e8-a5f8-595e11414ebf", "Tesla", "Model Y AWD", 2022),
            tuple(
                "36cfdbc5-3355-456e-9197-56b31fc750f3",
                "BMW",
                "i4 eDrive40 Gran Coupe (19in Wheels)",
                2025),
            tuple("350e4921-1b52-4967-b10a-0488dedf3e08", "Audi", "Q5 Hybrid", 2025),
            tuple("009da2f3-02fe-49f9-91ba-ddd439a46d8f", "Audi", "A6", 2021),
            tuple("e0009b46-0418-41fa-a12e-3b4ab5be6b58", "Volkswagen", "Golf", 2023));
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
