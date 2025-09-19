package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.fachdaten.Fachdaten;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {VorgangabfrageRestMapperImpl.class})
class VorgangabfrageRestMapperTest {

  @MockitoBean private FachdatenabfrageRestMapper fachdatenabfrageRestMapper;

  @Autowired private VorgangabfrageRestMapper classUnderTest;

  @Test
  void should_map_all_fields_to_resource() {

    FachdatenabfrageResource fachdatenResourceMock = mock(FachdatenabfrageResource.class);
    when(fachdatenabfrageRestMapper.toResource(any(Fachdaten.class)))
        .thenReturn(fachdatenResourceMock);

    Fachdaten fachdatenMock = mock(Fachdaten.class);
    Vorgang vorgang = new Vorgang("ID123", "businessKey");
    vorgang.fachdatenErgaenzen(fachdatenMock);

    VorgangabfrageResource result = classUnderTest.toResource(vorgang);

    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(result.id()).isEqualTo(vorgang.getId());
    softAssertions
        .assertThat(result.fachlicherSchluessel())
        .isEqualTo(vorgang.getFachlicherSchluessel());
    softAssertions.assertThat(result.fachdaten()).isEqualTo(fachdatenResourceMock);
    softAssertions.assertAll();

    verify(fachdatenabfrageRestMapper).toResource(fachdatenMock);
  }

  @Test
  void should_return_null_when_source_is_null() {

    VorgangabfrageResource result = classUnderTest.toResource(null);

    assertThat(result).isNull();
  }
}
