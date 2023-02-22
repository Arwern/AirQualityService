package dev.jozwik.airquality.controller;

import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.repository.status.AirMeasurementServiceStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseEntityFactory {

    public ResponseEntity<MeasurementStation> createResponseForAddingOperation(final AirMeasurementServiceStatus airMeasurementServiceStatus) {
        switch (airMeasurementServiceStatus.getStatus()) {
            case ADD -> {return ResponseEntity.status(HttpStatus.CREATED).body(airMeasurementServiceStatus.getMeasurementStation());}
            case ERROR -> {return ResponseEntity.badRequest().build();}
            default -> {return ResponseEntity.ok(airMeasurementServiceStatus.getMeasurementStation());}
        }
    }
}
