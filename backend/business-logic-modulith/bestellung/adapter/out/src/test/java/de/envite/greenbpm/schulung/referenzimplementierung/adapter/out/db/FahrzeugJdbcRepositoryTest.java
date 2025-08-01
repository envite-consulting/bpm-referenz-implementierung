package de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db;

import de.envite.greenbpm.schulung.referenzimplementierung.adapter.out.db.entity.FahrzeugEntity;
import de.envite.greenbpm.schulung.referenzimplementierung.uuidgenerator.UUIDGenerator;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.context.annotation.Import;

@DataJdbcTest
@Import(UUIDGenerator.class)
class FahrzeugJdbcRepositoryTest {

    @Autowired
    private FahrzeugJdbcRepository classUnderTest;

    @Test
    void should_save_with_uuid() {
        FahrzeugEntity entity =  new FahrzeugEntity();
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
}