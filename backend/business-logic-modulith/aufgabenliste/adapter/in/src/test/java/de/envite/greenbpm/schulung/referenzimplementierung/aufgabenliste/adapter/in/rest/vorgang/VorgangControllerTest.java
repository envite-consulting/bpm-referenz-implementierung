package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Vorgangabfrage;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(VorgangController.class)
class VorgangControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private Vorgangabfrage vorgangabfrageMock;
  @MockitoBean private VorgangabfrageRestMapper vorgangabfrageMapperMock;

  @Nested
  class Anzeige {

    @Test
    void should_anzeigen() throws Exception {
      String vorgangId = "id123";

      final Vorgang abgefragterVorgang = mock(Vorgang.class);

      final VorgangabfrageResource responseResource =
          new VorgangabfrageResource(
              vorgangId,
              "businessKey",
              new FachdatenabfrageResource("Vorname", "Nachname", "Hersteller", "Modell"));

      when(vorgangabfrageMock.abfragen(vorgangId)).thenReturn(abgefragterVorgang);
      when(vorgangabfrageMapperMock.toResource(abgefragterVorgang)).thenReturn(responseResource);

      mockMvc
          .perform(get("/vorgang/%s".formatted(vorgangId)))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .json(
                      """
                              {"id":"id123","fachlicherSchluessel":"businessKey","fachdaten":{"antragstellerVorname":"Vorname","antragstellerNachname":"Nachname","fahrzeugHersteller":"Hersteller","fahrzeugModell":"Modell"}}
                              """));
    }

    @Test
    void should_anzeigen_alle() throws Exception {

      final Vorgang abgefragteVorgang1 = mock(Vorgang.class);
      final Vorgang abgefragteVorgang2 = mock(Vorgang.class);

      final VorgangabfrageResource responseResource1 =
          new VorgangabfrageResource(
              "ID1",
              "businessKey1",
              new FachdatenabfrageResource("Vorname1", "Nachname1", "Hersteller1", "Modell1"));
      final VorgangabfrageResource responseResource2 =
          new VorgangabfrageResource(
              "ID2",
              "businessKey2",
              new FachdatenabfrageResource("Vorname2", "Nachname2", "Hersteller2", "Modell2"));

      when(vorgangabfrageMock.abfragenAlle())
          .thenReturn(List.of(abgefragteVorgang1, abgefragteVorgang2));
      when(vorgangabfrageMapperMock.toResource(abgefragteVorgang1)).thenReturn(responseResource1);
      when(vorgangabfrageMapperMock.toResource(abgefragteVorgang2)).thenReturn(responseResource2);

      mockMvc
          .perform(get("/vorgang"))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .json(
                      """
                              [{"id":"ID1","fachlicherSchluessel":"businessKey1","fachdaten":{"antragstellerVorname":"Vorname1","antragstellerNachname":"Nachname1","fahrzeugHersteller":"Hersteller1","fahrzeugModell":"Modell1"}},
                              {"id":"ID2","fachlicherSchluessel":"businessKey2","fachdaten":{"antragstellerVorname":"Vorname2","antragstellerNachname":"Nachname2","fahrzeugHersteller":"Hersteller2","fahrzeugModell":"Modell2"}}]"""));
    }
  }
}
