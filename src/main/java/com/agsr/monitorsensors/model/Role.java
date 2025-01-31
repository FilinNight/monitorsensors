package com.agsr.monitorsensors.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "role")
public class Role extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private RoleType type;
}
