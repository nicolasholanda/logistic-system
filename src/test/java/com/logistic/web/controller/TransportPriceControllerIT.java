package com.logistic.web.controller;

import com.logistic.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class TransportPriceControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnAllThreeSeededPrices() throws Exception {
        mockMvc.perform(get("/api/v1/prices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void shouldReturnPriceByTransportType() throws Exception {
        mockMvc.perform(get("/api/v1/prices/BOAT"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transportType").value("BOAT"))
                .andExpect(jsonPath("$.pricePerKg").isNotEmpty())
                .andExpect(jsonPath("$.pricePerCubicMeter").isNotEmpty());
    }

    @Test
    void shouldUpdatePriceAndReturnUpdatedValues() throws Exception {
        mockMvc.perform(put("/api/v1/prices/TRUCK")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "pricePerKg": 0.1500,
                                    "pricePerCubicMeter": 60.0000
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transportType").value("TRUCK"))
                .andExpect(jsonPath("$.pricePerKg").value(0.15))
                .andExpect(jsonPath("$.pricePerCubicMeter").value(60.0));
    }

    @Test
    void shouldReturn401WhenUpdatingWithoutCredentials() throws Exception {
        mockMvc.perform(put("/api/v1/prices/TRUCK")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "pricePerKg": 0.1500,
                                    "pricePerCubicMeter": 60.0000
                                }
                                """))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn400ForInvalidTransportType() throws Exception {
        mockMvc.perform(get("/api/v1/prices/PLANE"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenPricePerKgIsNegative() throws Exception {
        mockMvc.perform(put("/api/v1/prices/RAIL")
                        .with(httpBasic("admin", "admin"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "pricePerKg": -1,
                                    "pricePerCubicMeter": 25.0000
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
