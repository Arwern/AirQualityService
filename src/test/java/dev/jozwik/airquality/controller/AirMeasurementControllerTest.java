package dev.jozwik.airquality.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jozwik.airquality.entity.Measurement;
import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.repository.status.AirMeasurementResponseStatus;
import dev.jozwik.airquality.repository.status.AirMeasurementServiceStatus;
import dev.jozwik.airquality.service.AirMeasurementService;
import dev.jozwik.airquality.utils.MeasurementStationJson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest({AirMeasurementController.class, ResponseEntityFactory.class})
class AirMeasurementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirMeasurementService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void airMeasurementController_shouldReturnMeasurementStation_whenSuccessfullAdded()
            throws Exception {
        Measurement measurement = new Measurement("pm1", "Pm 1", "10", "1999-01-01 15:20");
        MeasurementStation measurementStation = new MeasurementStation(1L, "testStation", 50D, 50D, Map.of("pm1", measurement));
        MeasurementStationJson measurementStationJson = new MeasurementStationJson(1L, "testStation", 50D, 50D, Map.of("pm1", measurement));
        Mockito.when(this.service.addAirMeasurement(Mockito.any())).thenReturn(new AirMeasurementServiceStatus(
                AirMeasurementResponseStatus.ADD, measurementStation));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/measurement")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(measurementStationJson)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(measurementStation)));
    }

    @Test
    public void airMeasurementController_shouldReturnMeasurementStation_whenSuccessfullUpdated()
            throws Exception {
        Measurement measurement = new Measurement("pm1", "Pm 1", "10", "1999-01-01 15:20");
        MeasurementStation measurementStation = new MeasurementStation(1L, "testStation", 50D, 50D, Map.of("pm1", measurement));
        MeasurementStationJson measurementStationJson = new MeasurementStationJson(1L, "testStation", 50D, 50D, Map.of("pm1", measurement));
        Mockito.when(this.service.addAirMeasurement(Mockito.any())).thenReturn(new AirMeasurementServiceStatus(
                AirMeasurementResponseStatus.UPDATE, measurementStation));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/measurement")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(measurementStationJson)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(measurementStation)));
    }

}