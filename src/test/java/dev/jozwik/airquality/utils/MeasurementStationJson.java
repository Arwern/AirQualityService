package dev.jozwik.airquality.utils;

import dev.jozwik.airquality.entity.Measurement;

import java.util.Map;


public class MeasurementStationJson {
    private Long id;
    private String stationName;
    private Double geoLat;
    private Double geoLon;
    private Map<String, Measurement> measurements;

    public MeasurementStationJson(final Long id, final String stationName, final Double geoLat, final Double geoLon,
            final Map<String, Measurement> measurements) {
        this.id = id;
        this.stationName = stationName;
        this.geoLat = geoLat;
        this.geoLon = geoLon;
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

    public Double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(final Double geoLat) {
        this.geoLat = geoLat;
    }

    public Double getGeoLon() {
        return geoLon;
    }

    public void setGeoLon(final Double geoLon) {
        this.geoLon = geoLon;
    }

    public Map<String, Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(final Map<String, Measurement> measurements) {
        this.measurements = measurements;
    }
}
