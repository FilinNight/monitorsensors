package com.agsr.monitorsensors.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensor_unit")
@AllArgsConstructor
public class SensorUnit extends BaseEntity {

    public SensorUnit() {
    }

    @Column(name = "value", nullable = false)
    private String value;
}
