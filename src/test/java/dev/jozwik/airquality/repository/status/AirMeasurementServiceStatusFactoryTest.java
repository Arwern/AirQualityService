package dev.jozwik.airquality.repository.status;

import dev.jozwik.airquality.entity.MeasurementStation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class AirMeasurementServiceStatusFactoryTest {

    private final AirMeasurementServiceStatusFactory airMeasurementServiceStatusFactory = new AirMeasurementServiceStatusFactory();

    @Test
    public void airMeasurementServiceStatusFactory_shouldCreateAirMeasurementServiceStatusWithAddStatus_whenNewMeasurementStationIsGiven() {
        final AirMeasurementServiceStatus addStatus = this.airMeasurementServiceStatusFactory.createAddStatus(
                new MeasurementStation());
        assertEquals(AirMeasurementResponseStatus.ADD, addStatus.getStatus());
        assertNotNull(addStatus.getMeasurementStation());
    }

    @Test
    public void airMeasurementServiceStatusFactory_shouldCreateAirMeasurementServiceStatusWithAddStatus_whenMeasurementIsUpdated() {
        final AirMeasurementServiceStatus updateStatus = this.airMeasurementServiceStatusFactory.createUpdateStatus(
                new MeasurementStation());
        assertEquals(AirMeasurementResponseStatus.UPDATE, updateStatus.getStatus());
        assertNotNull(updateStatus.getMeasurementStation());
    }

}