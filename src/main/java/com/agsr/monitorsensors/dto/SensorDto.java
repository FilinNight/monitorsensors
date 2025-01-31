package com.agsr.monitorsensors.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SensorDto {
    private Long id;
    private String name;
    private String model;
    private RangeDto range;
    private String type;
    private String unit;
    private String location;
    private String description;
}
