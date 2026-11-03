package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.vorgang;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.FolgeaufgabeVorschlag;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.vorgang.Vorgang;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.exception.FolgeaufgabeNotFoundException;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Folgeaufgabenermittlung;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Vorgangabfrage;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(VorgangController.class)
class VorgangControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private Vorgangabfrage vorgangabfrageMock;
  @MockitoBean private VorgangabfrageRestMapper vorgangabfrageMapperMock;
  @MockitoBean private Folgeaufgabenermittlung folgeaufgabenermittlungMock;
  @MockitoBean private FolgeaufgabeVorschlagRestMapper folgeaufgabeVorschlagMapperMock;

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

  @Nested
  class FolgeaufgabenVorschlag {

    private final String vorgangId = "vorgang1";

    @Test
    void should_return_vorschlag_asynchronously() throws Exception {
      FolgeaufgabeVorschlag vorschlag = new FolgeaufgabeVorschlag("aufgabe1", vorgangId, "FAHRZEUG");

      when(folgeaufgabenermittlungMock.ermitteln(
              vorgangId, Optional.of("FAHRZEUG"), Optional.of(500L)))
          .thenReturn(CompletableFuture.completedFuture(vorschlag));
      when(folgeaufgabeVorschlagMapperMock.toResource(vorschlag))
          .thenReturn(new FolgeaufgabeVorschlagResource("aufgabe1", vorgangId, "FAHRZEUG"));

      MvcResult asyncResult =
          mockMvc
              .perform(
                  get("/vorgang/%s/folgeaufgabe-vorschlag".formatted(vorgangId))
                      .param("letztesArbeitspaket", "FAHRZEUG")
                      .param("timeoutInMillis", "500"))
              .andExpect(request().asyncStarted())
              .andReturn();

      mockMvc
          .perform(asyncDispatch(asyncResult))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .json(
                      """
                      {"aufgabeId":"aufgabe1","vorgangId":"vorgang1","arbeitspaket":"FAHRZEUG"}
                      """));
    }

    @Test
    void should_treat_parameters_as_optional() throws Exception {
      when(folgeaufgabenermittlungMock.ermitteln(vorgangId, Optional.empty(), Optional.empty()))
          .thenReturn(CompletableFuture.failedFuture(new FolgeaufgabeNotFoundException("Keine")));

      MvcResult asyncResult =
          mockMvc
              .perform(get("/vorgang/%s/folgeaufgabe-vorschlag".formatted(vorgangId)))
              .andExpect(request().asyncStarted())
              .andReturn();

      mockMvc.perform(asyncDispatch(asyncResult)).andExpect(status().isNotFound());
    }
  }
}
