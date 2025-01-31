package com.agsr.monitorsensors.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sensor")
public class Sensor extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "range_from", nullable = false)
    private Integer range_from;

    @Column(name = "range_to", nullable = false)
    private Integer range_to;

    @ManyToOne(optional = false)
    private SensorType type;

    @ManyToOne
    private SensorUnit unit;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;
}