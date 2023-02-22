package dev.jozwik.airquality.repository.status;

import dev.jozwik.airquality.entity.MeasurementStation;


public class AirMeasurementServiceStatus {
    private final AirMeasurementResponseStatus status;
    private final MeasurementStation measurementStation;

    public AirMeasurementServiceStatus(final AirMeasurementResponseStatus status,
            final MeasurementStation measurementStation) {
        this.status = status;
        this.measurementStation = measurementStation;
    }

    public AirMeasurementResponseStatus getStatus() {
        return status;
    }

    public MeasurementStation getMeasurementStation() {
        return measurementStation;
    }
}
