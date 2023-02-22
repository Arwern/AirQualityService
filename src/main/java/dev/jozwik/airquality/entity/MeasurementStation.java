package dev.jozwik.airquality.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.GeoIndexed;

import java.util.Map;


@RedisHash
@JsonDeserialize
public class MeasurementStation {
    @Id
    private Long id;
    private String stationName;
    @GeoIndexed
    private Point location;
    private Map<String, Measurement> measurements;

    public MeasurementStation() {
    }

    @JsonCreator
    public MeasurementStation(final Long id, final String stationName, final Double geoLat, final Double geoLon,
            final Map<String, Measurement> measurements) {
        this.id = id;
        this.stationName = stationName;
        this.location = new Point(geoLon, geoLat);
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
    public Map<String, Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(final Map<String, Measurement> measurements) {
        this.measurements = measurements;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(final Point location) {
        this.location = location;
    }
}
