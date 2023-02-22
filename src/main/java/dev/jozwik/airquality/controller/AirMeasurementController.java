package dev.jozwik.airquality.controller;

import dev.jozwik.airquality.service.AirMeasurementService;
import dev.jozwik.airquality.entity.MeasurementStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/measurement")
public class AirMeasurementController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirMeasurementController.class);

    @Autowired
    private AirMeasurementService airMeasurementService;

    @Autowired
    private ResponseEntityFactory responseEntityFactory;

    @GetMapping()
    public ResponseEntity<String> test() {
        return new ResponseEntity<>("measurement test ok!", HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MeasurementStation> addMeasurement(@RequestBody final MeasurementStation measurementStation) {
        LOGGER.debug("received post mapping for station id: {}", measurementStation.getId());
        return this.responseEntityFactory.createResponseForAddingOperation(this.airMeasurementService.addAirMeasurement(measurementStation));
    }
}
