package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.domain.model.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.Fahrzeugabfrage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FahrzeugController.class)
class FahrzeugControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private Fahrzeugabfrage fahrzeugabfrageMock;

  @MockitoBean private FahrzeugRestMapper fahrzeugMapperMock;

  @Test
  void should_query() throws Exception {
    final String fahrzeugId = "d370e9d6-03f6-4b4c-b71d-aba53b83f341";
    final FahrzeugResource resource =
        new FahrzeugResource(fahrzeugId, "Test Hersteller", "Test Modell", 1901);
    final Fahrzeug fahrzeugMock = mock(Fahrzeug.class);
    when(fahrzeugabfrageMock.abfragen(new FahrzeugId(fahrzeugId))).thenReturn(fahrzeugMock);
    when(fahrzeugMapperMock.toResource(fahrzeugMock)).thenReturn(resource);

    mockMvc
        .perform(get("/fahrzeug/" + fahrzeugId))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    """
                        {
                            "id":"d370e9d6-03f6-4b4c-b71d-aba53b83f341",
                            "hersteller":"Test Hersteller",
                            "modell":"Test Modell",
                            "jahr":1901
                        }
                        """));

    verify(fahrzeugabfrageMock).abfragen(new FahrzeugId(fahrzeugId));
    verify(fahrzeugMapperMock).toResource(fahrzeugMock);
  }

  @Test
  void should_query_all() throws Exception {
    final FahrzeugResource resource1 =
            new FahrzeugResource("faaba7ab-a50d-42aa-a936-bedc5e8dfcd0", "Test Hersteller", "Test Modell", 1901);
    final FahrzeugResource resource2 =
            new FahrzeugResource("037f9ecc-a3a7-403e-8663-0a918c9c4ac6", "Test Hersteller 2", "Test Modell 2", 1902);
    final Fahrzeug fahrzeugMock1 = mock(Fahrzeug.class);
    final Fahrzeug fahrzeugMock2 = mock(Fahrzeug.class);
    when(fahrzeugabfrageMock.abfragenAlle()).thenReturn(List.of(fahrzeugMock1, fahrzeugMock2));
    when(fahrzeugMapperMock.toResource(fahrzeugMock1)).thenReturn(resource1);
    when(fahrzeugMapperMock.toResource(fahrzeugMock2)).thenReturn(resource2);

    mockMvc
        .perform(get("/fahrzeug"))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(
                    """
                            [
                             {
                                  "id":"faaba7ab-a50d-42aa-a936-bedc5e8dfcd0",
                                  "hersteller":"Test Hersteller",
                                  "modell":"Test Modell",
                                  "jahr":1901
                              },
                               {
                                  "id":"037f9ecc-a3a7-403e-8663-0a918c9c4ac6",
                                  "hersteller":"Test Hersteller 2",
                                  "modell":"Test Modell 2",
                                  "jahr":1902
                              }
                            ]
                            """));

    verify(fahrzeugabfrageMock).abfragenAlle();
    verify(fahrzeugMapperMock).toResource(fahrzeugMock1);
    verify(fahrzeugMapperMock).toResource(fahrzeugMock2);
  }
}
