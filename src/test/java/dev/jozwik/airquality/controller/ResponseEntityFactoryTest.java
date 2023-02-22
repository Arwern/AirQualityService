package dev.jozwik.airquality.controller;

import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.repository.status.AirMeasurementResponseStatus;
import dev.jozwik.airquality.repository.status.AirMeasurementServiceStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;


class ResponseEntityFactoryTest {

    private final ResponseEntityFactory responseEntityFactory = new ResponseEntityFactory();

    @Test
    public void responseEntityFactory_shouldReturnMeasurementStationWithStatusCreated_whenNewMeasurementStationIsGiven() {
        final ResponseEntity<MeasurementStation> result = this.responseEntityFactory.createResponseForAddingOperation(
                new AirMeasurementServiceStatus(
                        AirMeasurementResponseStatus.ADD, new MeasurementStation()));
        Assertions.assertEquals(201, result.getStatusCode().value());
        Assertions.assertTrue( result.hasBody());
    }

    @Test
    public void responseEntityFactory_shouldReturnMeasurementStationWithStatusOk_whenMeasurementIsUpdated() {
        final ResponseEntity<MeasurementStation> result = this.responseEntityFactory.createResponseForAddingOperation(
                new AirMeasurementServiceStatus(
                        AirMeasurementResponseStatus.UPDATE, new MeasurementStation()));
        Assertions.assertEquals(200, result.getStatusCode().value());
        Assertions.assertTrue( result.hasBody());
    }

    @Test
    public void responseEntityFactory_shouldReturnBadRequestStatus_whenThereIsAnErrorInServiceMethod() {
        final ResponseEntity<MeasurementStation> result = this.responseEntityFactory.createResponseForAddingOperation(
                new AirMeasurementServiceStatus(
                        AirMeasurementResponseStatus.ERROR, new MeasurementStation()));
        Assertions.assertEquals(400, result.getStatusCode().value());
        Assertions.assertFalse( result.hasBody());
    }

}