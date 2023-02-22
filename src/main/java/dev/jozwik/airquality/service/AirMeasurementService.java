package dev.jozwik.airquality.service;

import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.repository.AirMeasurementRepository;
import dev.jozwik.airquality.repository.status.AirMeasurementServiceStatus;
import dev.jozwik.airquality.repository.status.AirMeasurementServiceStatusFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.domain.geo.Metrics;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class AirMeasurementService {

    @Autowired
    private AirMeasurementRepository airMeasurementRepository;

    @Autowired
    private AirMeasurementServiceStatusFactory airMeasurementServiceStatusFactory;


    public AirMeasurementServiceStatus addAirMeasurement(final MeasurementStation measurementStation) {
        final Optional<MeasurementStation> station = this.airMeasurementRepository.getMeasurementStation(
                measurementStation.getId());
        if(station.isPresent()) {
            station.get().getMeasurements().putAll(measurementStation.getMeasurements());
            return this.airMeasurementServiceStatusFactory.createUpdateStatus(this.airMeasurementRepository.insertAirMeasurement(station.get()));
        }
        return this.airMeasurementServiceStatusFactory.createAddStatus(this.airMeasurementRepository.insertAirMeasurement(measurementStation));
    }

    public Iterable<MeasurementStation> getAllMeasurements() {
        return this.airMeasurementRepository.getAllMeasurements();
    }

    public List<MeasurementStation> getAllMeasurementsNear(final Double x, final Double y, final Double distance) {
        return this.airMeasurementRepository.getAllMeasurementStationsNear(new Point(x, y), new Distance(distance, Metrics.KILOMETERS));
    }

    public List<MeasurementStation> getAllMeasurementsWithinBox(final Double x1, final Double y1, final Double x2, final Double y2) {
        return this.airMeasurementRepository.getAllMeasurementStationsWithinBox(new Box(new Point(x1, y1), new Point(x2, y2)));
    }
}
