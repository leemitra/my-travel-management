package com.travelmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelmanagement.model.Trip;
import com.travelmanagement.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TripRepository tripRepository;

    private Trip testTrip;

    @BeforeEach
    void setUp() {
        tripRepository.deleteAll();

        testTrip = Trip.builder()
                .destination("Paris")
                .description("Vacation in Paris")
                .startDate(LocalDate.of(2026, 7, 1))
                .endDate(LocalDate.of(2026, 7, 15))
                .travelerName("John Doe")
                .budget(5000.0)
                .status("PLANNED")
                .build();
    }

    @Test
    void testGetAllTrips() throws Exception {
        tripRepository.save(testTrip);

        mockMvc.perform(get("/api/trips")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].destination", is("Paris")));
    }

    @Test
    void testCreateTrip() throws Exception {
        mockMvc.perform(post("/api/trips")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTrip)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.destination", is("Paris")))
                .andExpect(jsonPath("$.travelerName", is("John Doe")));
    }

    @Test
    void testGetTripById() throws Exception {
        Trip savedTrip = tripRepository.save(testTrip);

        mockMvc.perform(get("/api/trips/{id}", savedTrip.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedTrip.getId().intValue())))
                .andExpect(jsonPath("$.destination", is("Paris")));
    }

    @Test
    void testUpdateTrip() throws Exception {
        Trip savedTrip = tripRepository.save(testTrip);

        Trip updatedTrip = Trip.builder()
                .destination("London")
                .description("Updated vacation")
                .startDate(LocalDate.of(2026, 8, 1))
                .endDate(LocalDate.of(2026, 8, 10))
                .travelerName("John Doe")
                .budget(6000.0)
                .status("CONFIRMED")
                .build();

        mockMvc.perform(put("/api/trips/{id}", savedTrip.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTrip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.destination", is("London")))
                .andExpect(jsonPath("$.status", is("CONFIRMED")));
    }

    @Test
    void testDeleteTrip() throws Exception {
        Trip savedTrip = tripRepository.save(testTrip);

        mockMvc.perform(delete("/api/trips/{id}", savedTrip.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/trips/{id}", savedTrip.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetTripsByTravelerName() throws Exception {
        tripRepository.save(testTrip);

        mockMvc.perform(get("/api/trips/traveler/{travelerName}", "John Doe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].travelerName", is("John Doe")));
    }

    @Test
    void testGetTripsByStatus() throws Exception {
        tripRepository.save(testTrip);

        mockMvc.perform(get("/api/trips/status/{status}", "PLANNED")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].status", is("PLANNED")));
    }

    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/trips/health/status")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("running")));
    }

}
