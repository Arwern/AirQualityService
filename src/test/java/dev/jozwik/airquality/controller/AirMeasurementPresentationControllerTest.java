package dev.jozwik.airquality.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jozwik.airquality.entity.Measurement;
import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.service.AirMeasurementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Map;

import static dev.jozwik.airquality.utils.TestUtils.createCollectionOfMeasurements;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest({AirMeasurementPresentationController.class, ResponseEntityFactory.class})
class AirMeasurementPresentationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirMeasurementService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void airMeasurementPresentationController_shouldReturnMeasurementStations_whenMeasurementsExistsInMemory()
            throws Exception {
        final List<MeasurementStation> collectionOfMeasurements = createCollectionOfMeasurements();
        Mockito.when(this.service.getAllMeasurements()).thenReturn(collectionOfMeasurements);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/report/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(collectionOfMeasurements)));
    }

    @Test
    public void airMeasurementPresentationController_shouldReturnEmptyBody_whenMeasurementsDoesNotExistsInMemory()
            throws Exception {
        Mockito.when(this.service.getAllMeasurements()).thenReturn(List.of());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/report/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }

    @Test
    public void airMeasurementPresentationController_shouldReturnMeasurementStations_whenThereAreMeasurementsExistingWithinGivenCircleInMemory()
            throws Exception {
        final List<MeasurementStation> collectionOfMeasurements = createCollectionOfMeasurements();
        Mockito.when(this.service.getAllMeasurementsNear(50D, 50D, 100D)).thenReturn(collectionOfMeasurements);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/report").param("x","50")
                        .param("y","50").param("distance", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(this.objectMapper.writeValueAsString(collectionOfMeasurements)));
    }

    @Test
    public void airMeasurementPresentationController_shouldReturnEmptyCollection_whenThereNoMeasurementsExistingWithinGivenCircleInMemory()
            throws Exception {
        Mockito.when(this.service.getAllMeasurementsNear(50D, 50D, 100D)).thenReturn(List.of());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/report").param("x","50")
                        .param("y","50").param("distance", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
    }
}