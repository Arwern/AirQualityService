package dev.jozwik.airquality.service;

import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.repository.AirMeasurementRepository;
import dev.jozwik.airquality.repository.status.AirMeasurementResponseStatus;
import dev.jozwik.airquality.repository.status.AirMeasurementServiceStatus;
import dev.jozwik.airquality.repository.status.AirMeasurementServiceStatusFactory;
import dev.jozwik.airquality.utils.TestUtils;
import org.assertj.core.internal.Iterables;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AirMeasurementServiceTest {

    @InjectMocks
    private AirMeasurementService airMeasurementService = new AirMeasurementService();
    @Mock
    private AirMeasurementRepository airMeasurementRepositoryMock;

    @Mock
    private AirMeasurementServiceStatusFactory airMeasurementServiceStatusFactory;

    @Test
    public void airMeasurementService_shouldCreatedAddStatus_whenMeasurementStationDoesNotExistInMemory() {
        final MeasurementStation measurementStation = new MeasurementStation();
        Mockito.when(this.airMeasurementRepositoryMock.getMeasurementStation(1L)).thenReturn(Optional.empty());
        Mockito.when(this.airMeasurementRepositoryMock.insertAirMeasurement(measurementStation)).thenReturn(measurementStation);
        Mockito.when(this.airMeasurementServiceStatusFactory.createAddStatus(measurementStation))
                .thenReturn(new AirMeasurementServiceStatus(AirMeasurementResponseStatus.ADD, measurementStation));
        final AirMeasurementServiceStatus airMeasurementServiceStatus = this.airMeasurementService.addAirMeasurement(
                measurementStation);
        assertEquals(AirMeasurementResponseStatus.ADD, airMeasurementServiceStatus.getStatus());
        assertNotNull(airMeasurementServiceStatus.getMeasurementStation());
    }

    @Test
    public void airMeasurementService_shouldCreatedUpdateStatus_whenMeasurementStationExistInMemory() {
        final MeasurementStation measurementStation = new MeasurementStation();
        Mockito.when(this.airMeasurementRepositoryMock.getMeasurementStation(1L)).thenReturn(Optional.of(measurementStation));
        Mockito.when(this.airMeasurementRepositoryMock.insertAirMeasurement(measurementStation)).thenReturn(measurementStation);
        Mockito.when(this.airMeasurementServiceStatusFactory.createAddStatus(measurementStation))
                .thenReturn(new AirMeasurementServiceStatus(AirMeasurementResponseStatus.UPDATE, measurementStation));
        final AirMeasurementServiceStatus airMeasurementServiceStatus = this.airMeasurementService.addAirMeasurement(
                measurementStation);
        assertEquals(AirMeasurementResponseStatus.UPDATE, airMeasurementServiceStatus.getStatus());
        assertNotNull(airMeasurementServiceStatus.getMeasurementStation());
    }

    @Test
    public void airMeasurementService_shouldReturnAllMeasurements_whenMeasurementsExistsInMemory() {
        Mockito.when(this.airMeasurementRepositoryMock.getAllMeasurements()).thenReturn(TestUtils.createCollectionOfMeasurements());
        final Iterable<MeasurementStation> allMeasurements = (this.airMeasurementService.getAllMeasurements());
        assertEquals(2, StreamSupport.stream(allMeasurements.spliterator(), false).count());
    }

    @Test
    public void airMeasurementService_shouldReturnEmptyCollection_whenMeasurementsDoesNotExistsInMemory() {
        Mockito.when(this.airMeasurementRepositoryMock.getAllMeasurements()).thenReturn(List.of());
        final Iterable<MeasurementStation> allMeasurements = (this.airMeasurementService.getAllMeasurements());
        assertFalse(allMeasurements.iterator().hasNext());
    }

    @Test
    public void airMeasurementService_shouldReturnMeasurementStations_whenThereAreMeasurementsExistingWithinGivenCircleInMemory() {
        Mockito.when(this.airMeasurementRepositoryMock.getAllMeasurementStationsNear(Mockito.any(Point.class), Mockito.any(
                Distance.class))).thenReturn(TestUtils.createCollectionOfMeasurements());
        final List<MeasurementStation> allMeasurementsNear = this.airMeasurementService.getAllMeasurementsNear(50D, 50D,
                100D);
        assertEquals(2, allMeasurementsNear.size());
    }

    @Test
    public void airMeasurementService_shouldReturnEmptyCollection_whenThereNoMeasurementsExistingWithinGivenCircleInMemory() {
        Mockito.when(this.airMeasurementRepositoryMock.getAllMeasurementStationsNear(Mockito.any(Point.class), Mockito.any(
                Distance.class))).thenReturn(List.of());
        final List<MeasurementStation> allMeasurementsNear = this.airMeasurementService.getAllMeasurementsNear(50D, 50D,
                100D);
        assertTrue(allMeasurementsNear.isEmpty());
    }
}