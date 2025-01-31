package com.agsr.monitorsensors.controller;

import com.agsr.monitorsensors.dto.Response;
import com.agsr.monitorsensors.dto.SensorDto;
import com.agsr.monitorsensors.service.SensorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Main controller", description = "It's got all methods API")
public class MainController {

    private final SensorService sensorService;

    public MainController(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @PreAuthorize("hasAnyRole('ROLE_VIEWER', 'ROLE_ADMINISTRATOR')")
    @GetMapping("/sensors")
    @Operation(summary = "Get all sensors")
    public Response<List<SensorDto>> getSensors(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "model", required = false) String model
    ) {
        try {
            return new Response<>(sensorService.getAllByNameOrModel(name, model));
        } catch (Exception e) {
            return new Response<>(e.getMessage(), 500);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PostMapping("/sensors")
    @Operation(summary = "Create sensor")
    public Response<SensorDto> createSensors(@RequestBody SensorDto sensor) {
        try {
            return new Response<>(sensorService.createSensor(sensor));
        } catch (Exception e) {
            return new Response<>(e.getMessage(), 500);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @PutMapping("/sensors")
    @Operation(summary = "Update sensor")
    public Response<SensorDto> updateSensors(@RequestBody SensorDto sensor) {
        try {
            return new Response<>(sensorService.updateSensor(sensor));
        } catch (Exception e) {
            return new Response<>(e.getMessage(), 500);
        }

    }

    @PreAuthorize("hasAnyRole('ROLE_VIEWER', 'ROLE_ADMINISTRATOR')")
    @GetMapping("/sensors/{id}")
    @Operation(summary = "Get sensor by Id")
    public Response<SensorDto> getSensorById(@PathVariable Long id) {
        try {
            return new Response<>(sensorService.getSensorById(id));
        } catch (Exception e) {
            return new Response<>(e.getMessage(), 500);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/sensors/{id}")
    @Operation(summary = "Delete sensor by Id")
    public Response<Boolean> deleteSensors(@PathVariable Long id) {
        try {
            sensorService.deleteSensor(id);
            return new Response<>(true);
        } catch (Exception e) {
            return new Response<>(e.getMessage(), 500);
        }
    }
}
