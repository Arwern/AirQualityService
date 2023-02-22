package dev.jozwik.airquality.repository.redis;

import dev.jozwik.airquality.entity.MeasurementStation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RedisMeasurementStationRepository extends CrudRepository<MeasurementStation, Long> {
    List<MeasurementStation> findByLocationNear(Point point, Distance distance, Pageable pageable);
    List<MeasurementStation> findByLocationWithin(Box box);
}
