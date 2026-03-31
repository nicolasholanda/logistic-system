package com.logistic.web.controller;

import com.logistic.AbstractIT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FreightControllerIT extends AbstractIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldCalculateFreightAndReturn201() throws Exception {
        mockMvc.perform(post("/api/v1/freight/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "transportType": "BOAT",
                                    "weightKg": 100,
                                    "volumeCubicMeters": 2
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.transportType").value("BOAT"))
                .andExpect(jsonPath("$.totalPrice").isNotEmpty());
    }

    @Test
    void shouldReturn400WhenTransportTypeIsMissing() throws Exception {
        mockMvc.perform(post("/api/v1/freight/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "weightKg": 100,
                                    "volumeCubicMeters": 2
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenWeightIsNegative() throws Exception {
        mockMvc.perform(post("/api/v1/freight/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "transportType": "TRUCK",
                                    "weightKg": -10,
                                    "volumeCubicMeters": 2
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400WhenVolumeIsZero() throws Exception {
        mockMvc.perform(post("/api/v1/freight/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "transportType": "RAIL",
                                    "weightKg": 50,
                                    "volumeCubicMeters": 0
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturn400ForUnknownTransportType() throws Exception {
        mockMvc.perform(post("/api/v1/freight/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "transportType": "PLANE",
                                    "weightKg": 100,
                                    "volumeCubicMeters": 2
                                }
                                """))
                .andExpect(status().isBadRequest());
    }
}
