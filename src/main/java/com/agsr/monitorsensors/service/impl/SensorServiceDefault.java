package com.agsr.monitorsensors.service.impl;

import com.agsr.monitorsensors.dto.RangeDto;
import com.agsr.monitorsensors.dto.SensorDto;
import com.agsr.monitorsensors.model.Sensor;
import com.agsr.monitorsensors.model.SensorType;
import com.agsr.monitorsensors.model.SensorUnit;
import com.agsr.monitorsensors.repository.SensorRepository;
import com.agsr.monitorsensors.repository.SensorTypeRepository;
import com.agsr.monitorsensors.repository.SensorUnitRepository;
import com.agsr.monitorsensors.service.SensorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorServiceDefault implements SensorService {

    private final SensorRepository sensorRepository;
    private final SensorTypeRepository sensorTypeRepository;
    private final SensorUnitRepository sensorUnitRepository;

    public SensorServiceDefault(SensorRepository sensorRepository,
                                SensorTypeRepository sensorTypeRepository,
                                SensorUnitRepository sensorUnitRepository) {
        this.sensorRepository = sensorRepository;
        this.sensorTypeRepository = sensorTypeRepository;
        this.sensorUnitRepository = sensorUnitRepository;
    }

    public Sensor findUnsafe(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sensor id: " + id + " is not found"));
    }

    @Override
    public List<SensorDto> getAllByNameOrModel(String name, String model) {
        return sensorRepository.findByNameOrModel(name, model).stream()
                .map(this::toSensorDto)
                .toList();
    }

    @Override
    public SensorDto getSensorById(Long id) {
        return toSensorDto(findUnsafe(id));
    }

    @Override
    @Transactional
    public SensorDto createSensor(SensorDto sensorDto) {
        isValidate(sensorDto);
        Sensor sensor = new Sensor();
        SensorType type = sensorTypeRepository.findByValue(sensorDto.getType())
                .orElseGet(() -> sensorTypeRepository.save(new SensorType(sensorDto.getType())));
        SensorUnit unit = sensorUnitRepository.findByValue(sensorDto.getUnit())
                .orElseGet(() -> sensorUnitRepository.save(new SensorUnit(sensorDto.getUnit())));
        sensor.setName(sensorDto.getName());
        sensor.setModel(sensorDto.getModel());
        sensor.setRange_from(sensorDto.getRange().getFrom());
        sensor.setRange_to(sensorDto.getRange().getTo());
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor.setLocation(sensorDto.getLocation());
        sensor.setDescription(sensorDto.getDescription());
        return toSensorDto(sensorRepository.save(sensor));
    }

    @Override
    public SensorDto updateSensor(SensorDto sensorDto) {
        isValidate(sensorDto);
        Sensor sensor = findUnsafe(sensorDto.getId());
        SensorType type = sensorTypeRepository.findByValue(sensorDto.getType())
                .orElseThrow(() -> new EntityNotFoundException("SensorType value: " + sensorDto.getType() + " is not found"));
        SensorUnit unit = sensorUnitRepository.findByValue(sensorDto.getUnit())
                .orElseThrow(() -> new EntityNotFoundException("SensorUnit value: " + sensorDto.getUnit() + " is not found"));
        sensor.setName(sensorDto.getName());
        sensor.setModel(sensorDto.getModel());
        sensor.setRange_from(sensorDto.getRange().getFrom());
        sensor.setRange_to(sensorDto.getRange().getTo());
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor.setLocation(sensorDto.getLocation());
        sensor.setDescription(sensorDto.getDescription());
        return toSensorDto(sensor);
    }

    @Override
    public void deleteSensor(Long id) {
        sensorRepository.deleteById(id);
    }

    private void isValidate(SensorDto sensorDto) {
        String name = sensorDto.getName();
        String model = sensorDto.getModel();
        String location = sensorDto.getLocation();
        String description = sensorDto.getDescription();
        RangeDto range = sensorDto.getRange();
        if (range != null) {
            if (range.getFrom() == null) {
                throw new ValidationException("Field range.from is NULL");
            }
            if (range.getTo() == null) {
                throw new ValidationException("Field range.to is NULL");
            }
            if (range.getFrom() < 0 || range.getTo() < 0) {
                throw new ValidationException("Field range.from or range.to is negative number");
            }
            if (range.getFrom() > range.getTo()) {
                throw new ValidationException("Field range.from > range.to");
            }
        } else {
            throw new ValidationException("Field range is NULL");
        }
        if (name != null) {
            if (name.length() < 3 || name.length() > 30) {
                throw new ValidationException("Field name is incorrectly sized: " + name.length());
            }
        } else {
            throw new ValidationException("Field name is NULL");
        }
        if (model != null) {
            if (model.length() > 15) {
                throw new ValidationException("Field name is incorrectly sized: " + model.length());
            }
        } else {
            throw new ValidationException("Field model is NULL");
        }
        if (location != null) {
            if (location.length() > 40) {
                throw new ValidationException("Field location is incorrectly sized: " + location.length());
            }
        }
        if (description != null) {
            if (description.length() > 200) {
                throw new ValidationException("Field description is incorrectly sized: " + description.length());
            }
        }
    }

    private SensorDto toSensorDto(Sensor sensor) {
        RangeDto range = RangeDto.builder()
                .from(sensor.getRange_from())
                .to(sensor.getRange_to())
                .build();
        return SensorDto.builder()
                .id(sensor.getId())
                .name(sensor.getName())
                .model(sensor.getModel())
                .range(range)
                .type(sensor.getType().getValue())
                .unit(sensor.getUnit().getValue())
                .location(sensor.getLocation())
                .description(sensor.getDescription())
                .build();
    }
}
