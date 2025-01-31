package com.agsr.monitorsensors.model;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
}
