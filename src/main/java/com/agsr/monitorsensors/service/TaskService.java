package com.agsr.monitorsensors.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private final SensorService sensorService;

    public TaskService(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Scheduled(cron = "${scheduled.monitor-sensor-info.cron}")
    void getMonitorSensorInfo() {
        // TODO: In work.
    }

}
