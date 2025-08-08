package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest;

import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.Bestellung;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.domain.model.BestellungId;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungsabfrage;
import de.envite.greenbpm.schulung.referenzimplementierung.bestellung.usecase.in.Bestellungserfassung;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BestellungController.class)
public class BestellungControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockitoBean private Bestellungsabfrage bestellungsabfrageMock;
  @MockitoBean private Bestellungserfassung bestellungserfassungMock;
  @MockitoBean private BestellungserfassungRestMapper bestellungserfassungMapperMock;
  @MockitoBean private BestellungsabfrageRestMapper bestellungsabfrageMapperMock;

  @Nested
  class Erfassung {

    @Test
    void should_erfassen() throws Exception {
      final String bestellungId = "d370e9d6-03f6-4b4c-b71d-aba53b83f341";

      final BestellungserfassungResource requestResource =
          new BestellungserfassungResource(
              "76a9edf1-6570-4d7e-b34b-e0dc3e73527e",
              "bf76eacc-dbcf-4bf2-ad9f-8f52a48c2709",
              LocalDateTime.of(2023, 1, 2, 0, 0),
              "ANGELEGT");

      final Bestellung mappedDomain = mock(Bestellung.class);
      final Bestellung erfassteBestellung = mock(Bestellung.class);

      final BestellungsabfrageResource responseResource =
          new BestellungsabfrageResource(
              bestellungId,
              requestResource.antragstellerreferenz(),
              requestResource.fahrzeugreferenz(),
              requestResource.bestelldatum(),
              requestResource.status());

      when(bestellungserfassungMapperMock.toDomain(requestResource)).thenReturn(mappedDomain);
      when(bestellungserfassungMock.erfassen(mappedDomain)).thenReturn(erfassteBestellung);
      when(bestellungsabfrageMapperMock.toResource(erfassteBestellung))
          .thenReturn(responseResource);

      mockMvc
          .perform(
              post("/bestellung")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content(
                      """
                                                          {
                                                            "antragstellerreferenz":"76a9edf1-6570-4d7e-b34b-e0dc3e73527e",
                                                            "fahrzeugreferenz":"bf76eacc-dbcf-4bf2-ad9f-8f52a48c2709",
                                                            "bestelldatum":"2023-01-02T00:00:00",
                                                            "status":"ANGELEGT"
                                                          }
                                                          """))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .json(
                      """
                                                          {
                                                            "id":"d370e9d6-03f6-4b4c-b71d-aba53b83f341",
                                                            "antragstellerreferenz":"76a9edf1-6570-4d7e-b34b-e0dc3e73527e",
                                                            "fahrzeugreferenz":"bf76eacc-dbcf-4bf2-ad9f-8f52a48c2709",
                                                            "bestelldatum":"2023-01-02T00:00:00",
                                                            "status":"ANGELEGT"
                                                          }
                                                          """));
    }
  }

  @Nested
  class Anzeige {

    @Test
    void should_anzeigen() throws Exception {
      String bestellungId = "d370e9d6-03f6-4b4c-b71d-aba53b83f341";

      final Bestellung abgefragteBestellung = mock(Bestellung.class);

      final BestellungsabfrageResource responseResource =
          new BestellungsabfrageResource(
              bestellungId,
              "76a9edf1-6570-4d7e-b34b-e0dc3e73527e",
              "bf76eacc-dbcf-4bf2-ad9f-8f52a48c2709",
              LocalDateTime.of(2023, 1, 2, 0, 0),
              "ANGELEGT");

      when(bestellungsabfrageMock.abfragen(new BestellungId(bestellungId)))
          .thenReturn(abgefragteBestellung);
      when(bestellungsabfrageMapperMock.toResource(abgefragteBestellung))
          .thenReturn(responseResource);

      mockMvc
          .perform(get("/bestellung/%s".formatted(bestellungId)))
          .andExpect(status().isOk())
          .andExpect(
              content()
                  .json(
                      """
                                            {
                                              "id":"d370e9d6-03f6-4b4c-b71d-aba53b83f341",
                                              "antragstellerreferenz":"76a9edf1-6570-4d7e-b34b-e0dc3e73527e",
                                              "fahrzeugreferenz":"bf76eacc-dbcf-4bf2-ad9f-8f52a48c2709",
                                              "bestelldatum":"2023-01-02T00:00:00",
                                              "status":"ANGELEGT"}
                                            """));
    }
  }
}
