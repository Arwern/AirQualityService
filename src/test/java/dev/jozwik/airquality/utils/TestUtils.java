package dev.jozwik.airquality.utils;

import dev.jozwik.airquality.entity.Measurement;
import dev.jozwik.airquality.entity.MeasurementStation;

import java.util.List;
import java.util.Map;


public final class TestUtils {

    public static List<MeasurementStation> createCollectionOfMeasurements() {
        final Measurement measurement = new Measurement("pm1", "Pm 1", "10", "1999-01-01 15:20");
        final Measurement measurement2 = new Measurement("pm1", "Pm 1", "14", "1999-01-01 16:00");
        final Measurement measurement3 = new Measurement("pm2", "Pm 1", "100", "1999-01-01 14:00");
        final MeasurementStation measurementStation = new MeasurementStation(1L, "testStation", 50D, 50D, Map.of("pm1", measurement));
        final MeasurementStation measurementStation2 = new MeasurementStation(1L, "testStation", 50D, 50D, Map.of("pm1", measurement2, "pm2.5", measurement3));
        return List.of(measurementStation, measurementStation2);
    }
}
