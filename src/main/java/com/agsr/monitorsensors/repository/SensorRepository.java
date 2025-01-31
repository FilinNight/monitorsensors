package com.agsr.monitorsensors.repository;

import com.agsr.monitorsensors.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findByName(String name);

    @Query(
            value = "select s from Sensor s " +
                    "where (COALESCE(:name, null) is null or s.name like %:name%) " +
                    "and (COALESCE(:model, null) is null or s.model like %:model%)"
    )
    List<Sensor> findByNameOrModel(@Param("name") String name, @Param("model") String model);
}

