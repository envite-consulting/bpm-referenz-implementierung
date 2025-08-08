package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.Antragsteller;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.domain.model.AntragstellerId;
import de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.usecase.in.Antragstellerabfrage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AntragstellerController.class)
class AntragstellerControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private Antragstellerabfrage antragstellerabfrageMock;

  @MockitoBean private AntragstellerRestMapper antragstellerMapperMock;

  @Test
  void should_query() throws Exception {
    final String antragstellerId = "3f8f4c61-bce2-4ed9-bb6d-3dd678a9f936";
    final AntragstellerResource resource =
        new AntragstellerResource(antragstellerId, "Test", "Name", "Abteilung");
    final Antragsteller antragstellerMock = mock(Antragsteller.class);
    when(antragstellerabfrageMock.abfragen(new AntragstellerId(antragstellerId)))
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
  }

  @Test
  void should_query_all() throws Exception {
    final AntragstellerResource resource1 =
            new AntragstellerResource("c97b2a48-edff-4f3a-a293-cf50e23a10dc", "Test1", "Name1", "Abteilung1");
    final AntragstellerResource resource2 =
            new AntragstellerResource("ee48199d-afd9-4ae4-b148-0b1e76c7f1f4", "Test2", "Name2", "Abteilung2");
    final Antragsteller antragstellerMock1 = mock(Antragsteller.class);
    final Antragsteller antragstellerMock2 = mock(Antragsteller.class);
    when(antragstellerabfrageMock.abfragenAlle()).thenReturn(List.of(antragstellerMock1, antragstellerMock2));
    when(antragstellerMapperMock.toResource(antragstellerMock1)).thenReturn(resource1);
    when(antragstellerMapperMock.toResource(antragstellerMock2)).thenReturn(resource2);

    mockMvc
            .perform(get("/antragsteller"))
            .andExpect(status().isOk())
            .andExpect(
                    content()
                            .json(
                                    """
                                            [
                                             {
                                                "id":'c97b2a48-edff-4f3a-a293-cf50e23a10dc',
                                                "vorname":"Test1",
                                                "nachname":"Name1",
                                                "abteilung":"Abteilung1"
                                            },
                                             {
                                                "id":'ee48199d-afd9-4ae4-b148-0b1e76c7f1f4',
                                                "vorname":"Test2",
                                                "nachname":"Name2",
                                                "abteilung":"Abteilung2"
                                            }
                                            ]
                                           
                                            """));
  }
}
