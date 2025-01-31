package com.agsr.monitorsensors.repository;

import com.agsr.monitorsensors.model.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {
    Optional<SensorType> findByValue(String value);
}

