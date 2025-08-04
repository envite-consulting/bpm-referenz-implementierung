package de.envite.greenbpm.schulung.referenzimplementierung.bestellung.adapter.in.rest.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestAdviceSampleController.class)
@Import(BestellungRestAdvicer.class)
class BestellungRestAdvicerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_convert_bestellungNotFoundException_to_404() throws Exception {

        mockMvc.perform(get("/error/bestellungNotFoundException"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                            "name": "BestellungNotFoundException",
                            "errorMessage":"Das ist ein Test"
                        }
                        """));
    }
}