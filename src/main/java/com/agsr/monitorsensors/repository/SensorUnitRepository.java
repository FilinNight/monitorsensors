package com.agsr.monitorsensors.repository;

import com.agsr.monitorsensors.model.SensorUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorUnitRepository extends JpaRepository<SensorUnit, Long> {
    Optional<SensorUnit> findByValue(String value);
}

