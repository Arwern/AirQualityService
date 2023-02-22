package dev.jozwik.airquality.controller;

import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.service.AirMeasurementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/report")
public class AirMeasurementPresentationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AirMeasurementPresentationController.class);
    @Autowired
    private AirMeasurementService airMeasurementService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllReportedAirQualityMeasurements() {
        LOGGER.debug("Retrieve all measurements");
        return ResponseEntity.ok(this.airMeasurementService.getAllMeasurements());
    }

    @GetMapping(params = {"x", "y", "distance"})
    public ResponseEntity<?> getAllReportedAirQualityMeasurementsNearPoint(@RequestParam Double x, @RequestParam Double y, @RequestParam Double distance) {
        LOGGER.debug("Retrieve measurements for circle(x,y,r): {} ", x, y, distance);
        return ResponseEntity.ok(this.airMeasurementService.getAllMeasurementsNear(x, y, distance));
    }

    @GetMapping(params = {"x1", "y1", "x2", "y2"})
    public ResponseEntity<List<MeasurementStation>> getAllReportedAirQualityMeasurementsWhithinBox(@RequestParam Double x1,
            @RequestParam Double y1, @RequestParam Double x2, @RequestParam Double y2) {
        LOGGER.debug("Retrieve measurements for box(x1,y1,x2,y2): {} ", x1, y1, x2, y2);
        return ResponseEntity.ok(this.airMeasurementService.getAllMeasurementsWithinBox(x1, y1, x2, y2));
    }
}
