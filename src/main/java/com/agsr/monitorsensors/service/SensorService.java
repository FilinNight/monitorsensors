package com.agsr.monitorsensors.service;

import com.agsr.monitorsensors.dto.SensorDto;

import java.util.List;

public interface SensorService {

    List<SensorDto> getAllByNameOrModel(String name, String model);

    SensorDto getSensorById(Long id);

    SensorDto createSensor(SensorDto sensorDto);

    SensorDto updateSensor(SensorDto sensorDto);

    void deleteSensor(Long id);

}
