package de.envite.greenbpm.schulung.referenzimplementierung.antragsteller.adapter.out.rest.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RestAdviceSampleController.class)
@Import(AntragstellerRestAdvicer.class)
class AntragstellerRestAdvicerTest {

  @Autowired private MockMvc mockMvc;

  @Test
  void should_convert_antragstellerNotFoundException_to_404() throws Exception {
    mockMvc
        .perform(get("/error/antragstellerNotFoundException"))
        .andExpect(status().isNotFound())
        .andExpect(
            content()
                .json(
                    """
                        {
                            "name": "AntragstellerNotFoundException",
                            "errorMessage":"Das ist ein Test"
                        }
                        """));
  }
}
