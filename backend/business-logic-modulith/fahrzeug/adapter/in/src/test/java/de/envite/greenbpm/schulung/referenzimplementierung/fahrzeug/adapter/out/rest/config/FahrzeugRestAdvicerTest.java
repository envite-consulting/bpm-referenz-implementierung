package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest.config;

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
@Import(FahrzeugRestAdvicer.class)
class FahrzeugRestAdvicerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void should_convert_fahrzeugNotFoundException_to_404() throws Exception {
    mockMvc
        .perform(get("/error/fahrzeugNotFoundException"))
        .andExpect(status().isNotFound())
        .andExpect(
            content()
                .json(
                    """
                        {
                            "name": "FahrzeugNotFoundException",
                            "errorMessage":"Das ist ein Test"
                        }
                        """));
  }

  @Test
  void should_convert_InvariantException_to_400() throws Exception {
    mockMvc
        .perform(get("/error/invariantException"))
        .andExpect(status().isBadRequest())
        .andExpect(
            content()
                .json(
                    """
                                    {
                                        "name": "InvariantException",
                                        "errorMessage":"Value of Test is not valid: Test should not be null."
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
