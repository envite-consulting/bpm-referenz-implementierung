package de.envite.greenbpm.schulung.referenzimplementierung.fahrzeug.adapter.out.rest.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestAdviceSampleController.class)
@Import(FahrzeugRestAdvicer.class)
class FahrzeugRestAdvicerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void should_convert_fahrzeugNotFoundException_to_404() throws Exception {
        mockMvc.perform(get("/error/fahrzeugNotFoundException"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                        {
                            "name": "FahrzeugNotFoundException",
                            "errorMessage":"Das ist ein Test"
                        }
                        """));
    }
}