package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.aufgabe;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.domain.model.aufgabe.Aufgabe;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Aufgabenabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.usecase.in.Aufgabenverwaltung;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AufgabenController.class)
class AufgabenControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private Aufgabenabfrage aufgabenabfrageMock;
  @MockitoBean private Aufgabenverwaltung aufgabenverwaltungMock;
  @MockitoBean private AufgabenabfrageRestMapper aufgabenabfrageMapperMock;

  @Nested
  class Anzeige {

    @Test
    void should_anzeigen() throws Exception {
      String aufgabenId = "id123";

      final Aufgabe abgefragteAufgabe = mock(Aufgabe.class);

      final AufgabenabfrageResource responseResource =
          new AufgabenabfrageResource(
              aufgabenId, "My Task", "Test User", LocalDateTime.of(2023, 1, 1, 0, 0), "Ref1");

      when(aufgabenabfrageMock.abfragen(aufgabenId)).thenReturn(abgefragteAufgabe);
      when(aufgabenabfrageMapperMock.toResource(abgefragteAufgabe)).thenReturn(responseResource);

      mockMvc
          .perform(get("/aufgabe/%s".formatted(aufgabenId)))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .json(
                      """
                              {"id":"id123","name":"My Task","bearbeiter":"Test User","erstelldatum":"2023-01-01T00:00:00","formularreferenz":"Ref1"}
                              """));
    }

    @Test
    void should_anzeigen_alle_zu_vorgang() throws Exception {

      String vorgangId = "id123";

      final Aufgabe abgefragteAufgabe1 = mock(Aufgabe.class);
      final Aufgabe abgefragteAufgabe2 = mock(Aufgabe.class);

      final AufgabenabfrageResource responseResource1 =
          new AufgabenabfrageResource(
              "ID1", "My Task 1", "Test User1", LocalDateTime.of(2023, 1, 1, 0, 0), "Ref1");
      final AufgabenabfrageResource responseResource2 =
          new AufgabenabfrageResource(
              "ID2", "My Task ", "Test User2", LocalDateTime.of(2024, 1, 1, 0, 0), "Ref2");

      when(aufgabenabfrageMock.abfragenAlleZuVorgang(vorgangId))
          .thenReturn(List.of(abgefragteAufgabe1, abgefragteAufgabe2));
      when(aufgabenabfrageMapperMock.toResource(abgefragteAufgabe1)).thenReturn(responseResource1);
      when(aufgabenabfrageMapperMock.toResource(abgefragteAufgabe2)).thenReturn(responseResource2);

      mockMvc
          .perform(get("/aufgabe?vorgangId=%s".formatted(vorgangId)))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .json(
                      """
                              [{"id":"ID1","name":"My Task 1","bearbeiter":"Test User1","erstelldatum":"2023-01-01T00:00:00","formularreferenz":"Ref1"},{"id":"ID2","name":"My Task ","bearbeiter":"Test User2","erstelldatum":"2024-01-01T00:00:00","formularreferenz":"Ref2"}]
                              """));
    }
  }

  @Nested
  class Verwaltung {

    @Test
    void should_uebernehmen() throws Exception {
      String aufgabenId = "id123";
      String userId = "user123";

      String requestJson = objectMapper.writeValueAsString(Map.of("userId", userId));

      mockMvc
          .perform(
              put("/aufgabe/%s/uebernehmen".formatted(aufgabenId))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(requestJson))
          .andExpect(status().isNoContent());
      verify(aufgabenverwaltungMock).uebernehmen(aufgabenId, userId);
    }

    @Test
    void should_abschliessen() throws Exception {

      String aufgabenId = "id123";
      Map<String, Object> variables = Map.of("Variable 1", "Value 1");

      String requestJson = objectMapper.writeValueAsString(Map.of("variables", variables));

      mockMvc
          .perform(
              put("/aufgabe/%s/abschliessenMitVariablen".formatted(aufgabenId))
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(requestJson))
          .andExpect(status().isNoContent());
      verify(aufgabenverwaltungMock).abschliessenMitVariablen(aufgabenId, variables);
    }

    @Test
    void should_abgeben() throws Exception {
      String aufgabenId = "id123";

      mockMvc
          .perform(
              put("/aufgabe/%s/abgeben".formatted(aufgabenId))
                  .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
      verify(aufgabenverwaltungMock).abgeben(aufgabenId);
    }
  }
}
