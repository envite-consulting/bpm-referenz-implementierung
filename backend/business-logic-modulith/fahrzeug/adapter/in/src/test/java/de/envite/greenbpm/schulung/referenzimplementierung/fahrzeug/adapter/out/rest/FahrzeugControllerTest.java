package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.Fahrzeug;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.fahrzeug.FahrzeugId;
import de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.usecase.in.FahrzeugAbfrage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FahrzeugController.class)
class FahrzeugControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private FahrzeugAbfrage fahrzeugAbfrageMock;

  @MockitoBean private FahrzeugRestMapper fahrzeugMapperMock;

  @Test
  void should_query() throws Exception {
    final String fahrzeugId = "d370e9d6-03f6-4b4c-b71d-aba53b83f341";
    final FahrzeugResource resource =
        new FahrzeugResource(fahrzeugId, "Test Hersteller", "Test Modell", 1901);
    final Fahrzeug fahrzeugMock = mock(Fahrzeug.class);
    when(fahrzeugAbfrageMock.abfragen(new FahrzeugId(fahrzeugId))).thenReturn(fahrzeugMock);
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

    verify(fahrzeugAbfrageMock).abfragen(new FahrzeugId(fahrzeugId));
    verify(fahrzeugMapperMock).toResource(fahrzeugMock);
  }
}
