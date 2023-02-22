package dev.jozwik.airquality.repository.redis;

import dev.jozwik.airquality.entity.MeasurementStation;
import dev.jozwik.airquality.entity.SettingKey;
import dev.jozwik.airquality.repository.AirMeasurementRepository;
import dev.jozwik.airquality.repository.SettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.search.aggr.Limit;

import java.util.List;
import java.util.Optional;


@Repository
public class RedisAirMeasurementRepository implements AirMeasurementRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisAirMeasurementRepository.class);

    @Autowired
    private RedisMeasurementStationRepository repository;

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public MeasurementStation insertAirMeasurement(final MeasurementStation measurementStation) {
        LOGGER.debug("Trying to insert measurement");
        return this.repository.save(measurementStation);
    }

    @Override
    public Iterable<MeasurementStation> getAllMeasurements() {
        LOGGER.debug("Retrieving all measurements");
        return this.repository.findAll();
    }

    @Override
    public Optional<MeasurementStation> getMeasurementStation(final Long id) {
        LOGGER.debug("Retrieving measurement for id: {} ", id);
        return this.repository.findById(id);
    }

    @Override
    public List<MeasurementStation> getAllMeasurementStationsNear(final Point point, final Distance distance) {
        LOGGER.debug("Retrieving measurements for circle");
        final Pageable limit = PageRequest.of(0, Integer.parseInt(this.settingRepository.getSetting(SettingKey.LIMIT)));
        return this.repository.findByLocationNear(point, distance, limit);
    }

    @Override
    public List<MeasurementStation> getAllMeasurementStationsWithinBox(final Box box) {
        LOGGER.debug("Retrieving measurements for circle");

        return this.repository.findByLocationWithin(box);
    }
}
