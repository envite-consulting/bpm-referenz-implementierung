package de.envite.greenbpm.schulung.referenzimplementierung.aufgabenliste.adapter.in.rest.config;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RestAdviceSampleController.class)
@Import(AufgabenlisteRestAdvicer.class)
class AufgabenlisteRestAdvicerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void should_convert_VorgangNotFoundException_to_404() throws Exception {
    mockMvc
        .perform(get("/error/vorgangNotFoundException"))
        .andExpect(status().isNotFound())
        .andExpect(
            content()
                .json(
                    """
                              {
                                "name": "VorgangNotFoundException",
                                "errorMessage": "Das ist ein Test"
                              }
                              """));
  }

  @Test
  void should_convert_VorgangQueryException_to_500() throws Exception {
    mockMvc
        .perform(get("/error/vorgangQueryException"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            content()
                .json(
                    """
                            {
                              "name": "VorgangQueryException",
                              "errorMessage": "Fehler beim Abfragen",
                              "cause": "Ursache"
                            }
                            """));
  }

  @Test
  void should_convert_AufgabeNotFoundException_to_404() throws Exception {
    mockMvc
        .perform(get("/error/aufgabeNotFoundException"))
        .andExpect(status().isNotFound())
        .andExpect(
            content()
                .json(
                    """
                                {
                                "name": "AufgabeNotFoundException",
                                "errorMessage": "Das ist ein Test"
                                }
                            """));
  }

  @Test
  void should_convert_AufgabeQueryException_to_500() throws Exception {
    mockMvc
        .perform(get("/error/aufgabeQueryException"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            content()
                .json(
                    """
                                {
                                  "name": "AufgabeQueryException",
                                  "errorMessage": "Fehler beim Abfragen",
                                  "cause": "Ursache"
                                }
                                """));
  }

  @Test
  void should_convert_AufgabeUpdateException_to_500() throws Exception {
    mockMvc
        .perform(get("/error/aufgabeUpdateException"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            content()
                .json(
                    """
                                {
                                  "name": "AufgabeUpdateException",
                                  "errorMessage": "Fehler beim Aktualisieren",
                                  "cause": "Ursache"
                                }
                                """));
  }

  @Test
  void should_convert_ProzessstartException_to_500() throws Exception {
    mockMvc
        .perform(get("/error/prozessstartException"))
        .andExpect(status().isInternalServerError())
        .andExpect(
            content()
                .json(
                    """
                              {
                                "name": "ProzessstartException",
                                "errorMessage": "Fehler beim Prozessstart",
                                "cause": "Ursache"
                              }
                              """));
  }

  @Test
  void should_not_convert_unhandled_RuntimeException() {
    assertThatThrownBy(() -> mockMvc.perform(get("/error/runtimeException")))
        .isInstanceOf(ServletException.class)
        .hasRootCauseInstanceOf(RuntimeException.class)
        .hasMessageContaining("Nicht gefangene Runtime Exception");
  }
}
