package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.antragsteller.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.AntragstellerAbfrage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AntragstellerController.class)
class AntragstellerControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private AntragstellerAbfrage antragstellerAbfrageMock;

  @MockitoBean private AntragstellerRestMapper antragstellerMapperMock;

  @Test
  void should_query() throws Exception {
    final String antragstellerId = "3f8f4c61-bce2-4ed9-bb6d-3dd678a9f936";
    final AntragstellerResource resource =
        new AntragstellerResource(antragstellerId, "Test", "Name", "Abteilung");
    final Antragsteller antragstellerMock = mock(Antragsteller.class);
    when(antragstellerAbfrageMock.abfragen(new AntragstellerId(antragstellerId)))
        .thenReturn(antragstellerMock);
    when(antragstellerMapperMock.toResource(antragstellerMock)).thenReturn(resource);

    mockMvc
        .perform(get("/antragsteller/" + antragstellerId))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    """
                            {
                                "id":"3f8f4c61-bce2-4ed9-bb6d-3dd678a9f936",
                                "vorname":"Test",
                                "nachname":"Name",
                                "abteilung":"Abteilung"
                            }
                            """));

    verify(antragstellerAbfrageMock).abfragen(new AntragstellerId(antragstellerId));
    verify(antragstellerMapperMock).toResource(antragstellerMock);
  }
}
