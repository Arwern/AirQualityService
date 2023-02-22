package dev.jozwik.airquality;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jozwik.airquality.entity.Measurement;
import dev.jozwik.airquality.utils.MeasurementStationJson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AirQualityApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    int randomServerPort;

    private URI uri;
    Random random = new Random();

    /*
    Integration test
    to run make sure that Redis is working in local environment
    docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack:latest
     */
    @Test
    void integrationTests() throws URISyntaxException, JsonProcessingException {
        this.uri = new URI("http://localhost:" + this.randomServerPort + "/api/v1/measurement");
        //insert 100 random measurements stations within 40-60 geo loc
        AddMeasurementStations();
        //try to get all stations
        getAllStationsTest();
        //get some data within a circle
        getStationsFromCircle();
        //get data within circe with increased radius
        getStationsFromCircleWithIncreasedRadiusWithLimit();
        //remove limit and try to get more data
        getStationsFromCircleWithIncreasedRadiusWithoutLimit();
    }

    private void getStationsFromCircleWithIncreasedRadiusWithoutLimit() throws JsonProcessingException {
        final URI uriSetting = UriComponentsBuilder.fromHttpUrl(
                        "http://localhost:" + this.randomServerPort + "/api/v1/settings")
                .queryParam("setting", "limit").queryParam("value", 1000000)
                .build().toUri();
        this.restTemplate.postForEntity(uriSetting, null, String.class);
        final URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + this.randomServerPort + "/api/v1/report")
                .queryParam("x", "50").queryParam("y", "50")
                .queryParam("distance", "500000").build().toUri();
        final List<MeasurementStationDeserialized> measurementStations = getMeasurementsFromUri(uri);
        assertFalse(measurementStations.isEmpty());
        //final String limit = restTemplate.getForObject("http://localhost:" + randomServerPort + "/api/v1/settings?setting=limit", String.class);
        assertTrue(measurementStations.size() >= 100);
    }

    private void getStationsFromCircleWithIncreasedRadiusWithLimit() throws JsonProcessingException {
        final URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + this.randomServerPort + "/api/v1/report")
                .queryParam("x", "50").queryParam("y", "50")
                .queryParam("distance", "500000").build().toUri();
        final List<MeasurementStationDeserialized> measurementStations = getMeasurementsFromUri(uri);
        assertFalse(measurementStations.isEmpty());
        final String limit = restTemplate.getForObject(
                "http://localhost:" + this.randomServerPort + "/api/v1/settings?setting=limit", String.class);
        assertTrue(measurementStations.size() <= Integer.parseInt(limit));
    }

    private void getStationsFromCircle() throws URISyntaxException, JsonProcessingException {
        final URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + this.randomServerPort + "/api/v1/report")
                .queryParam("x", "50").queryParam("y", "50")
                .queryParam("distance", "500").build().toUri();
        final List<MeasurementStationDeserialized> measurementStations = getMeasurementsFromUri(uri);
        assertFalse(measurementStations.isEmpty());
    }

    private void getAllStationsTest() throws URISyntaxException, JsonProcessingException {
        final List<MeasurementStationDeserialized> measurementStations = getMeasurementsFromUri(
                new URI("http://localhost:" + randomServerPort + "/api/v1/report/all"));
        assertEquals(100, measurementStations.size());
    }

    private List<MeasurementStationDeserialized> getMeasurementsFromUri(final URI uri) throws JsonProcessingException {
        final String result = this.restTemplate.getForObject(uri, String.class);
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(result, ArrayList.class);
    }

    private void AddMeasurementStations() throws URISyntaxException {
        for (int i = 0; i < 100; i++) {
            postMeasurementStation(createMeasurementStation(i));
        }
    }

    private MeasurementStationJson createMeasurementStation(final int i) {

        return new MeasurementStationJson((long) i, "test" + i, 40D + this.random.nextDouble(20D),
                40D + this.random.nextDouble(20D),
                createMeasurements());
    }

    private Map<String, Measurement> createMeasurements() {
        return Map.of("pm1",
                new Measurement("pm1", "Pm 1", Integer.toString(this.random.nextInt(100)), LocalDateTime.now().toString()));
    }

    private void postMeasurementStation(final MeasurementStationJson measurementStation) {
        this.restTemplate.postForEntity(uri, measurementStation, String.class);
    }

    private class MeasurementStationDeserialized {
        private Long id;
        private String stationName;
        private Point location;
        private Map<String, Measurement> measurements;

        public MeasurementStationDeserialized() {
        }

        public MeasurementStationDeserialized(final Long id, final String stationName, final Point location,
                final Map<String, Measurement> measurements) {
            this.id = id;
            this.stationName = stationName;
            this.location = location;
            this.measurements = measurements;
        }

        public Long getId() {
            return id;
        }

        public void setId(final Long id) {
            this.id = id;
        }

        public String getStationName() {
            return stationName;
        }

        public void setStationName(final String stationName) {
            this.stationName = stationName;
        }

        public Point getLocation() {
            return location;
        }

        public void setLocation(final Point location) {
            this.location = location;
        }

        public Map<String, Measurement> getMeasurements() {
            return measurements;
        }

        public void setMeasurements(final Map<String, Measurement> measurements) {
            this.measurements = measurements;
        }
    }

    private class Point {
        private Double x;
        private Double y;

        public Point() {
        }

        public Point(final Double x, final Double y) {
            this.x = x;
            this.y = y;
        }

        public Double getX() {
            return x;
        }

        public void setX(final Double x) {
            this.x = x;
        }

        public Double getY() {
            return y;
        }

        public void setY(final Double y) {
            this.y = y;
        }
    }
}
