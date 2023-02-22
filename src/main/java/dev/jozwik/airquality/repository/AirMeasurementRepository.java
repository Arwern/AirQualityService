package dev.jozwik.airquality.repository;

import dev.jozwik.airquality.entity.MeasurementStation;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.List;
import java.util.Optional;


public interface AirMeasurementRepository {
    MeasurementStation insertAirMeasurement(MeasurementStation measurementStation);

    Iterable<MeasurementStation> getAllMeasurements();

    Optional<MeasurementStation> getMeasurementStation(Long id);

    List<MeasurementStation> getAllMeasurementStationsNear(Point point, Distance distance);

    List<MeasurementStation> getAllMeasurementStationsWithinBox(Box box);
}
