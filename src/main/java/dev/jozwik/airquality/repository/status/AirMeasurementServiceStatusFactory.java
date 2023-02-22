package dev.jozwik.airquality.repository.status;

import dev.jozwik.airquality.entity.MeasurementStation;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AirMeasurementServiceStatusFactory {

    public AirMeasurementServiceStatus createAddStatus(final MeasurementStation returnedValue) {
        return new AirMeasurementServiceStatus(AirMeasurementResponseStatus.ADD, returnedValue);
    }

    public AirMeasurementServiceStatus createUpdateStatus(final MeasurementStation returnedValue) {
        return new AirMeasurementServiceStatus(AirMeasurementResponseStatus.UPDATE, returnedValue);
    }
}
