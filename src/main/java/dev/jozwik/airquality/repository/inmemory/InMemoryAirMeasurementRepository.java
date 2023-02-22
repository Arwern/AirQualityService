package dev.jozwik.airquality.repository.inmemory;

import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.repository.AirMeasurementRepository;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


//@Repository
public class InMemoryAirMeasurementRepository implements AirMeasurementRepository {

    private static final Map<Long, MeasurementStation> repository = new HashMap<>();

    @Override
    public MeasurementStation insertAirMeasurement(final MeasurementStation measurementStation) {
        repository.put(measurementStation.getId(), measurementStation);
        return measurementStation;
    }

    @Override
    public Iterable<MeasurementStation> getAllMeasurements() {
        return repository.values();
    }

    @Override
    public Optional<MeasurementStation> getMeasurementStation(final Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public List<MeasurementStation> getAllMeasurementStationsNear(final Point point, final Distance distance) {
        //not supported
        return List.of();
    }

    @Override
    public List<MeasurementStation> getAllMeasurementStationsWithinBox(final Box box) {
        //not supported
        return List.of();
    }
}
