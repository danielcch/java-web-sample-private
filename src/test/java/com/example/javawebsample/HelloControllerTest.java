package com.example.javawebsample;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFormEndpoint() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Conversor de Dólares a Euros")));
    }

    @Test
    public void testConvertEndpoint() throws Exception {
        mockMvc.perform(get("/convert").param("amount", "a"))
                .andExpect(status().isOk())
                .andExpect(content().string("La cantidad de 100.0 dólares equivale a 85.00 euros."));
    }

    @Test
    public void testConvertEndpointWithZero() throws Exception {
        mockMvc.perform(get("/convert").param("amount", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("La cantidad de 0.0 dólares equivale a 0.00 euros."));
    }

    @Test
    public void testConvertEndpointWithInvalidParameter() throws Exception {
        mockMvc.perform(get("/convert").param("amount", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testConvertEndpointWithDecimalAmount() throws Exception {
        mockMvc.perform(get("/convert").param("amount", "123.45"))
                .andExpect(status().isOk())
                .andExpect(content().string("La cantidad de 123.45 dólares equivale a 104.93 euros."));
    }

    @Test
    public void testConvertEndpointWithLargeAmount() throws Exception {
        mockMvc.perform(get("/convert").param("amount", "1000000"))
                .andExpect(status().isOk())
                .andExpect(content().string("La cantidad de 1000000.0 dólares equivale a 850000.00 euros."));
    }

    @Test
    public void testConvertEndpointWithNegativeAmount() throws Exception {
        mockMvc.perform(get("/convert").param("amount", "-50"))
                .andExpect(status().isOk())
                .andExpect(content().string("La cantidad de -50.0 dólares equivale a -42.50 euros."));
    }
}
